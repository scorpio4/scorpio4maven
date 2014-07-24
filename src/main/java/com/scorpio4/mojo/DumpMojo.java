package com.scorpio4.mojo;

import com.scorpio4.assets.Asset;
import com.scorpio4.oops.ConfigException;
import com.scorpio4.oops.FactException;
import com.scorpio4.vendor.sesame.io.SPARQLRules;
import com.scorpio4.vocab.COMMONS;
import org.openrdf.query.MalformedQueryException;
import org.openrdf.query.QueryEvaluationException;
import org.openrdf.repository.RepositoryException;
import org.openrdf.rio.RDFHandlerException;

import java.io.File;
import java.io.IOException;

/**
 * Scorpio4 (c) 2014
 * Module: com.scorpio4.mojo
 * @author lee
 * Date  : 18/06/2014
 * Time  : 12:58 AM
 */


/**
 * Goal that dumps facts
 *
 * @goal Dump
 * @requiresProject true
 * @phase process-sources
 */

public class DumpMojo extends ScorpioMojo {

    @Override
    public void executeInternal() throws FactException, ConfigException, IOException, RepositoryException, QueryEvaluationException, MalformedQueryException, RDFHandlerException {
        File file = new File(getHomePath(), getAppName()+".dump.n3");

        SPARQLRules SPARQLRules = new SPARQLRules(getConnection(),getIdentity());
        Asset asset = getAsset("mojo/dump", COMMONS.MIME_SPARQL);
        SPARQLRules.apply(asset.toString(), file);
        getLog().info("Dumped to "+file.getAbsolutePath());
    }
}
