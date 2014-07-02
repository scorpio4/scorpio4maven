package com.scorpio4.mojo;

import com.scorpio4.assets.Asset;
import com.scorpio4.oops.ConfigException;
import com.scorpio4.oops.FactException;
import com.scorpio4.vendor.sesame.io.SPARQLer;
import com.scorpio4.vocab.COMMON;
import org.openrdf.query.MalformedQueryException;
import org.openrdf.query.QueryEvaluationException;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
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
