package com.scorpio4.mojo;

import com.scorpio4.oops.ConfigException;
import com.scorpio4.oops.FactException;
import com.scorpio4.vendor.sesame.crud.SesameCRUD;
import org.openrdf.repository.RepositoryException;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

/**
 * Scorpio4 (c) 2014
 * Module: com.scorpio4.maven
 * @author lee
 * Date  : 16/06/2014
 * Time  : 5:38 PM
 */
/**
 * Goal that expands Templates
 *
 * @goal Generate
 * @requiresProject true
 * @phase process-sources
 */
public class GenerateMoJo extends ScorpioMojo {

    @Override
    public void executeInternal() throws FactException, ConfigException, IOException, RepositoryException {
        getLog().info("Generating: "+getIdentity());

        SesameCRUD sesameCRUD = new SesameCRUD(getFactSpace());
        Collection<Map> rules = sesameCRUD.read("mojo/templates", getProject().getProperties());
        for(Map rule:rules) {
            getLog().info("Template: "+rule);
        }
    }
}
