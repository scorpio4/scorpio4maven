package com.scorpio4.mojo;

import com.scorpio4.assets.Asset;
import com.scorpio4.assets.AssetRegister;
import com.scorpio4.assets.AssetRegisters;
import com.scorpio4.fact.FactSpace;
import com.scorpio4.util.map.MapUtil;
import com.scorpio4.vendor.sesame.RepositoryManager;
import com.scorpio4.vendor.sesame.store.NativeRDFSRepository;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.config.RepositoryConfigException;
import org.openrdf.repository.http.HTTPRepository;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;

/**
 * Scorpio4 (c) 2014
 * Module: com.scorpio4.maven
 * @author lee
 * Date  : 16/06/2014
 * Time  : 5:36 PM
 */
public abstract class ScorpioMojo extends AbstractMojo {
	public File srcPath = null, targetPath = null, homePath = null, resourcesPath=null;
	public String identity = null;
	String PROPS_PREFIX = "scorpio4";

	/**
	 * @parameter default-value="${project}"
	 * @required
	 * @readonly
	 */
	public MavenProject project;

	public Repository repository = null;
	public AssetRegister assetRegister;
	public RepositoryManager repositoryManager;
	protected FactSpace factSpace;

	public ScorpioMojo() {
    }

    public void initialize() throws MojoFailureException, RepositoryException, MalformedURLException, RepositoryConfigException {
        Hashtable properties = getProject().getProperties();
        this.identity = MapUtil.getString(properties, PROPS_PREFIX + ".id", null);
        if (this.identity==null) throw new MojoFailureException("Missing <scorpio4.id>");

        this.srcPath = MapUtil.getFile(properties, PROPS_PREFIX +".src.path", getProject().getBasedir() );
        this.resourcesPath = MapUtil.getFile(properties, PROPS_PREFIX +".resources.path", srcPath );
        this.targetPath = MapUtil.getFile(properties, PROPS_PREFIX +".target.path", getProject().getBasedir() );
        this.homePath = MapUtil.getFile(properties, PROPS_PREFIX +".home", getProject().getBasedir() );

        initializeRepository();
    }

    protected void initializeRepository() throws RepositoryException, MalformedURLException, RepositoryConfigException {
	    this.repositoryManager = new RepositoryManager(getHomePath());
	    this.repository = repositoryManager.getRepository(getIdentity());
	    getLog().info("Repository: "+getIdentity()+" @ "+ getHomePath());
	    this.factSpace = new FactSpace(getIdentity(),getRepository());
	    this.assetRegister = new AssetRegisters(factSpace.getConnection());
    }

    public void finished() throws RepositoryException {
        if (factSpace!=null) factSpace.close();
        if (repository!=null && repository.isInitialized()) repository.shutDown();
        getLog().info("Finished MoJo: "+getClass().getSimpleName());
    }

	public MavenProject getProject() {
		return project;
	}

	public File getSrcPath() {
        return srcPath;
    }

    public File getResourcesPath() {
        return resourcesPath;
    }

    public File getTargetPath() {
        return targetPath;
    }

    public File getHomePath() {
        return homePath;
    }

    public String getAppName() {
        return getProject().getName();
    }

    public Asset getAsset(String uri, String mimeType) throws IOException {
        return assetRegister.getAsset(uri,mimeType);
    }

    public String getIdentity() {
        return this.identity;
    }

    public Repository getRepository() {
        return repository;
    }

    public RepositoryConnection getConnection() throws RepositoryException {
        return getFactSpace().getConnection();
    }

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        try {
            initialize();
            executeInternal();
        } catch (Exception e) {
            getLog().error(e.getMessage());
            throw new MojoExecutionException("Fact Error: "+e.getMessage(), e);
        } finally {
            try {
                finished();
            } catch (RepositoryException e) {
                throw new MojoExecutionException("Repository Shutdown Error", e);
            }
        }
    }

	public AssetRegister getAssetRegister() {
		return assetRegister;
	}

	public RepositoryManager getRepositoryManager() {
		return repositoryManager;
	}

	public abstract void executeInternal() throws Exception;

    public Repository newLocalRepository() throws RepositoryException {
        File dataDir = new File(homePath, "sandbox");
        dataDir.mkdirs();
        NativeRDFSRepository nativeRDFSRepository = new NativeRDFSRepository(dataDir);
        nativeRDFSRepository.initialize();
        return nativeRDFSRepository;
    }

    public Repository newSandboxRepository(Map properties) throws RepositoryException {
        String host = MapUtil.getString(properties, PROPS_PREFIX +".upload.host", null);
        String name = MapUtil.getString(properties, PROPS_PREFIX + ".upload.name", null);
        File dataDir = new File(homePath, "sandbox");
        dataDir.mkdirs();

        if (host==null||host.equals("")) {
            getLog().info("Using Native Repository: "+ dataDir.getAbsolutePath());
            return new NativeRDFSRepository(dataDir);
        }

        HTTPRepository repository = new HTTPRepository(host, name);
        repository.setDataDir(dataDir);
        repository.initialize();
        getLog().info("Connected to "+ host+name );
        return repository;
   }

	public Map<String,String> getConfig() {
		Map map = new HashMap<String,String>();
		Properties properties = getProject().getProperties();
		for(Object property: properties.keySet()) {
			String key = property.toString();
			Object value = properties.get(property);
			boolean isLocal = key.startsWith("scorpio4_")||key.startsWith("scorpio4:");
			if (value!=null&&isLocal) {
				key = key.substring(9).replace(".","_");
				map.put(key, value.toString());
			}
		}
		return map;
	}

	public FactSpace getFactSpace() {
		return factSpace;
	}
}
