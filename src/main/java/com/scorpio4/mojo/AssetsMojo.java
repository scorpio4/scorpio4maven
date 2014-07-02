package com.scorpio4.mojo;

import com.scorpio4.deploy.Scorpio4SesameDeployer;
import com.scorpio4.oops.ConfigException;
import com.scorpio4.oops.FactException;
import org.openrdf.repository.RepositoryException;

import java.io.IOException;

/**
 * Fact:Core (c) 2014
 * Module: com.scorpio4.maven
 * User  : lee
 * Date  : 16/06/2014
 * Time  : 5:38 PM
 */


/**
 * Goal that imports Assets and Scripts
 *
 * @goal Assets
 * @requiresProject true
 * @phase process-sources
 */

public class AssetsMojo extends BaseFactToolsMojo {

    @Override
    public void executeInternal() throws FactException, ConfigException, IOException, RepositoryException {
        getLog().info("Loading Assets: "+getResourcesPath().getAbsolutePath());

        Scorpio4SesameDeployer scorpio4SesameDeployer = new Scorpio4SesameDeployer(getFactSpace());
        scorpio4SesameDeployer.setDeployRDF(false);
        scorpio4SesameDeployer.setDeployScripts(true);

        scorpio4SesameDeployer.deploy(getResourcesPath());
    }
}
