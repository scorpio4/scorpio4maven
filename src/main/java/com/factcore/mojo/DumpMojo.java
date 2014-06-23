package com.factcore.mojo;

import com.factcore.assets.Asset;
import com.factcore.oops.ConfigException;
import com.factcore.oops.FactException;
import com.factcore.vendor.sesame.io.SPARQLer;
import com.factcore.vocab.COMMON;
import org.openrdf.query.MalformedQueryException;
import org.openrdf.query.QueryEvaluationException;
import org.openrdf.repository.RepositoryException;
import org.openrdf.rio.RDFHandlerException;

import java.io.File;
import java.io.IOException;

/**
 * Fact:Core (c) 2014
 * Module: com.factcore.mojo
 * User  : lee
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

public class DumpMojo extends MavenMojo {

    @Override
    public void executeInternal() throws FactException, ConfigException, IOException, RepositoryException, QueryEvaluationException, MalformedQueryException, RDFHandlerException {
        File file = new File(getTempPath(), getAppName()+".dump.n3");

        SPARQLer sparqLer = new SPARQLer(getFactSpace());
        Asset asset = getAsset("mojo/dump", COMMON.MIME_SPARQL);
        sparqLer.copy(asset.toString(), file);
        getLog().info("Dumped to "+file.getAbsolutePath());
    }
}
