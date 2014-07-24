package com.scorpio4.mojo;

import com.scorpio4.assets.AssetRegisters;
import com.scorpio4.deploy.Scorpio4SesameDeployer;
import com.scorpio4.oops.FactException;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;

import java.io.File;
import java.io.IOException;

/**
 * Scorpio4 (c) 2014
 * Module: com.scorpio4.maven
 * @author lee
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

public class InitializeMojo extends ScorpioMojo {

    @Override
    public void executeInternal() throws FactException, IOException, RepositoryException {
	    assert getRepository()!=null;
	    RepositoryConnection connection = getRepository().getConnection();
	    getLog().info("Clear Repository: " +getIdentity());
		connection.begin();
	    connection.clear();
	    connection.commit();

        try {
            this.assetRegister = new AssetRegisters(getConnection());
            Scorpio4SesameDeployer scorpio4SesameDeployer = new Scorpio4SesameDeployer(getIdentity(),getConnection());
            scorpio4SesameDeployer.clean();

            File path = getResourcesPath();
            path.mkdirs();
            getLog().info("Initialized: " + getAppName() + " @ " + getIdentity());
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
    }
}
