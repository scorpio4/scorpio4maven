package com.factcore.mojo;

import com.factcore.assets.Asset;
import com.factcore.oops.ConfigException;
import com.factcore.oops.FactException;
import com.factcore.vendor.sesame.crud.SesameCRUD;
import com.factcore.vendor.sesame.io.SPARQLer;
import com.factcore.vendor.sesame.util.RDFPrefixer;
import com.factcore.vocab.COMMON;
import org.openrdf.query.MalformedQueryException;
import org.openrdf.query.QueryEvaluationException;
import org.openrdf.repository.RepositoryException;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

/**
 * Fact:Core (c) 2014
 * Module: com.factcore.maven
 * User  : lee
 * Date  : 16/06/2014
 * Time  : 5:38 PM
 */

/**
 * Goal that runs SPARQL Rules
 *
 * @goal Infer
 * @requiresProject true
 * @phase process-sources
 */
public class InferMoJo extends BaseFactToolsMojo {

    @Override
    public void executeInternal() throws FactException, ConfigException, IOException, RepositoryException, MalformedQueryException, QueryEvaluationException {
        getLog().info("Executing SPARQL Rules: "+getIdentity());

        SPARQLer sparqLer = new SPARQLer(getFactSpace());

        SesameCRUD sesameCRUD = new SesameCRUD(getFactSpace());
        Collection<Map> rules = sesameCRUD.read("mojo/infer", getProject().getProperties());

        for(Map rule:rules) {
            String ruleURI = (String) rule.get("this");
            Asset asset = getAsset(ruleURI, COMMON.MIME_SPARQL);
            String sparql = RDFPrefixer.addSPARQLPrefix(connection, asset.toString());
            int counted = sparqLer.copy(sparql);
            getLog().debug("Inferred " + ruleURI + " -> " + counted + " statements");
        }
    }
}
