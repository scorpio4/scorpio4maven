package com.factcore.mojo;

import com.factcore.assets.AssetRegisters;
import com.factcore.deploy.SesameDeployer;
import com.factcore.oops.FactException;
import org.apache.commons.io.FileUtils;
import org.openrdf.repository.RepositoryException;

import java.io.File;
import java.io.IOException;

/**
 * Fact:Core (c) 2014
 * Module: com.factcore.maven
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
            SesameDeployer sesameDeployer = new SesameDeployer(getFactSpace());
            sesameDeployer.clean();

            File path = getResourcesPath();
            path.mkdirs();
            getLog().info("Initialized: " + getAppName() + " @ " + getIdentity());
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
    }
}
