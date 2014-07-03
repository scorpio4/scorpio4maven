package com.scorpio4.mojo;

import com.scorpio4.assets.AssetRegisters;
import com.scorpio4.deploy.Scorpio4SesameDeployer;
import com.scorpio4.oops.FactException;
import org.apache.commons.io.FileUtils;
import org.openrdf.repository.RepositoryException;

import java.io.File;
import java.io.IOException;

/**
 * Scorpio4 (c) 2014
 * Module: com.scorpio4.maven
 * User  : lee
 * Date  : 17/06/2014
 * Time  : 11:49 PM
 */


/**
 * Goal that Initialize
 *
 * @goal Initialize
 * @requiresProject true
 * @phase process-sources
 */

public class InitializeMojo extends BaseFactToolsMojo {

    protected void initializeRepository() throws RepositoryException {
    }

    @Override
    public void executeInternal() throws FactException, IOException {

        getLog().info("Deleting: " + getTempPath().getAbsolutePath());
        FileUtils.deleteDirectory(getTempPath());

        try {
            repository = newLocalRepository();
            connection = repository.getConnection();
            this.assetRegister = new AssetRegisters(connection);
            Scorpio4SesameDeployer scorpio4SesameDeployer = new Scorpio4SesameDeployer(getFactSpace());
            scorpio4SesameDeployer.clean();

            File path = getResourcesPath();
            path.mkdirs();
            getLog().info("Initialized: " + getAppName() + " @ " + getIdentity());
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
    }
}
