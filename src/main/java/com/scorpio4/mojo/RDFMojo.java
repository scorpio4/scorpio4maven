package com.scorpio4.mojo;

import com.scorpio4.deploy.Scorpio4SesameDeployer;
import com.scorpio4.oops.FactException;
import org.openrdf.repository.RepositoryException;

import java.io.IOException;

/**
 * Scorpio4 (c) 2014
 * Module: com.scorpio4.maven
 * @author lee
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

public class RDFMojo extends ScorpioMojo {

    @Override
    public void executeInternal() throws FactException, IOException, RepositoryException {
        getLog().info("Loading RDF: " + getSrcPath().getAbsolutePath());

	    getConnection().begin();
        getConnection().clear();
	    getConnection().commit();

        Scorpio4SesameDeployer scorpio4SesameDeployer = new Scorpio4SesameDeployer(getIdentity(), getConnection());
        scorpio4SesameDeployer.setDeployRDF(true);
        scorpio4SesameDeployer.setDeployScripts(false);

        scorpio4SesameDeployer.deploy(getSrcPath());
    }
}
