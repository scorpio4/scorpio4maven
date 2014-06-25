package com.factcore.mojo;

import com.factcore.deploy.SesameDeployer;
import com.factcore.oops.FactException;
import org.openrdf.repository.RepositoryException;

import java.io.IOException;

/**
 * Fact:Core (c) 2014
 * Module: com.factcore.maven
 * User  : lee
 * Date  : 16/06/2014
 * Time  : 5:38 PM
 */


/**
 * Goal that imports RDF
 *
 * @goal RDF
 * @requiresProject true
 * @phase process-sources
 */

public class RDFMojo extends MavenMojo {

    @Override
    public void executeInternal() throws FactException, IOException, RepositoryException {
        getLog().info("Loading RDF: " + getSrcPath().getAbsolutePath());

	    getConnection().begin();
        getConnection().clear();
	    getConnection().commit();

        SesameDeployer sesameDeployer = new SesameDeployer(getFactSpace());
        sesameDeployer.setDeployRDF(true);
        sesameDeployer.setDeployScripts(false);

        sesameDeployer.deploy(getSrcPath());
    }
}
