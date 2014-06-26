package com.factcore.mojo;

import com.factcore.oops.ConfigException;
import com.factcore.oops.FactException;
import com.factcore.vendor.sesame.crud.SesameCRUD;

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
 * Goal that expands Templates
 *
 * @goal Generate
 * @requiresProject true
 * @phase process-sources
 */
public class GenerateMoJo extends BaseFactToolsMojo {

    @Override
    public void executeInternal() throws FactException, ConfigException, IOException {
        getLog().info("Generating: "+getIdentity());

        SesameCRUD sesameCRUD = new SesameCRUD(getFactSpace());
        Collection<Map> rules = sesameCRUD.read("mojo/templates", getProject().getProperties());
        for(Map rule:rules) {
            getLog().info("Template: "+rule);
        }
    }
}
