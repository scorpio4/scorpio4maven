package com.scorpio4.mojo;

import com.scorpio4.assets.Asset;
import com.scorpio4.oops.ConfigException;
import com.scorpio4.oops.FactException;
import com.scorpio4.vendor.sesame.crud.SesameCRUD;
import com.scorpio4.vendor.sesame.io.SPARQLer;
import com.scorpio4.vendor.sesame.util.RDFPrefixer;
import com.scorpio4.vocab.COMMON;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.openrdf.query.MalformedQueryException;
import org.openrdf.query.QueryEvaluationException;
import org.openrdf.repository.RepositoryException;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

/**
 * Fact:Core (c) 2014
 * Module: com.scorpio4.maven
 * User  : lee
 * Date  : 16/06/2014
 * Time  : 5:38 PM
 */


/**
 * Goal that checks facts
 *
 * @goal Check
 * @requiresProject true
 * @phase process-sources
 */

public class CheckFactsMojo extends BaseFactToolsMojo {

    @Override
    public void executeInternal() throws FactException, ConfigException, IOException, RepositoryException, QueryEvaluationException, MalformedQueryException, MojoExecutionException, MojoFailureException {
        getLog().info("Executing Fact Checks: "+getIdentity());

        SPARQLer sparqLer = new SPARQLer(getFactSpace());

        SesameCRUD sesameCRUD = new SesameCRUD(getFactSpace());
        Collection<Map> rules = sesameCRUD.read("mojo/factchecks", getProject().getProperties());

        for(Map rule:rules) {
            String ruleURI = (String) rule.get("this");
            Asset asset = getAsset(ruleURI, COMMON.MIME_SPARQL);
            if (asset!=null) {
                String sparql = RDFPrefixer.addSPARQLPrefix(connection, asset.toString());
                getLog().info("FactCheck: "+ruleURI+" -> "+sparql);
                sparqLer.copy(sparql);
            } else {
                getLog().warn("Missing FactCheck for: " + ruleURI);
            }
        }

        Collection<Map> fails = sesameCRUD.read("mojo/failedchecks", getProject().getProperties());
        getLog().warn("Failed: " + fails.size() + " statements");

        Collection<Map> fatals = sesameCRUD.read("mojo/fatalchecks", getProject().getProperties());
        if (!fatals.isEmpty()) {
	        for(Map fatal: fatals) {
		        getLog().error("Check Failed: "+fatal);
	        }
            throw new MojoFailureException("FATAL: " + fatals.size()+ " statements");
        }
    }
}
