package com.factcore.mojo;

import com.factcore.assets.Asset;
import com.factcore.oops.ConfigException;
import com.factcore.oops.FactException;
import com.factcore.vendor.sesame.io.SPARQLer;
import com.factcore.vocab.COMMON;
import org.openrdf.query.MalformedQueryException;
import org.openrdf.query.QueryEvaluationException;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
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
 * Goal that imports Assets and Scripts
 *
 * @goal Upload
 * @requiresProject true
 * @phase process-sources
 */

public class UploadMojo extends BaseFactToolsMojo {

    @Override
    public void executeInternal() throws FactException, ConfigException, IOException, RepositoryException, QueryEvaluationException, MalformedQueryException {
        getLog().info("Synchronizing Sandbox: "+getIdentity());

        Repository sandboxRepository = newSandboxRepository(getProject().getProperties());
        RepositoryConnection sandboxConnection = sandboxRepository.getConnection();

        SPARQLer sparqLer = new SPARQLer(getFactSpace());
        sparqLer.clean(sandboxConnection);

        Asset asset = getAsset("mojo/upload", COMMON.MIME_SPARQL);
        int copied = sparqLer.copy(sandboxConnection, asset.toString());
        getLog().info("Copied "+copied+" statements to Sandbox");
        sandboxConnection.close();
    }
}
