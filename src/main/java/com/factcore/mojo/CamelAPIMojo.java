package com.factcore.mojo;

import com.factcore.assets.AssetRegisters;
import com.factcore.deploy.Scorpio4SesameDeployer;
import com.factcore.vendor.camel.component.*;
import com.factcore.vendor.camel.planner.RDFRoutePlanner;
import com.factcore.vendor.sesame.crud.SesameCRUD;
import com.factcore.vocab.COMMON;
import org.apache.camel.CamelContext;
import org.apache.camel.Route;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.SimpleRegistry;

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
 * Multi-Protocol API Server
 *
 * @goal API
 * @requiresProject true
 * @phase process-sources
 */

public class CamelAPIMojo extends BaseFactToolsMojo {
	boolean running = true;

	@Override
	public void executeInternal() throws Exception {
		getLog().info("Camel API: " + getIdentity());

		Scorpio4SesameDeployer scorpio4SesameDeployer = new Scorpio4SesameDeployer(getFactSpace());
		scorpio4SesameDeployer.clean();
		scorpio4SesameDeployer.setDeployRDF(true);
		scorpio4SesameDeployer.setDeployScripts(true);
		scorpio4SesameDeployer.deploy(getSrcPath());

		assetRegister = new AssetRegisters(getConnection());

		SimpleRegistry registry = new SimpleRegistry();
		CamelContext camel = new DefaultCamelContext(registry);

		RDFRoutePlanner routePlanner = new RDFRoutePlanner(camel, getFactSpace());
		routePlanner.setBaseURI(COMMON.CORE+"flo/");
		getLog().info("API ready in: " + stopWatch.toString());

		SesameCRUD crud = new SesameCRUD(getFactSpace(), assetRegister);
		Collection<Map> beans = crud.read("mojo/beans", null);
		for(Map bean: beans) {
			String classname = bean.get("class").toString();
			if (classname.startsWith("bean:")) {
				classname = classname.substring(5);
				getLog().info("Bean: "+classname);
				Class clasz = Class.forName(classname);
				Object bound = clasz.newInstance();
				registry.put((String) bean.get("this"), bound);
			}
		}

		camel.addComponent("self", new SelfComponent(this));
		camel.addComponent("crud", new CRUDComponent(crud));
		camel.addComponent("core", new CoreComponent(getFactSpace(), assetRegister ));
		camel.addComponent("any23", new Any23Component());
		camel.addComponent("sparql", new SesameComponent(repositoryManager));

//		registry.bind("self", this);
		getLog().info("API bound " + beans.size() + " beans in: " + stopWatch.toString());
//		Collection<Map> routeTemplates = crud.read("mojo/beans", null);

//		DefaultDebugger debugger = new DefaultDebugger();
//		debugger.addBreakpoint(new Brea)
//		camel.setDebugger( debugger );

		routePlanner.plan();
		getLog().info("Planned "+camel.getRoutes().size()+" routes in: " + stopWatch.toString());
		for(Route route:camel.getRoutes()) {
			getLog().info("\t\t"+route.getId()+" -> "+route.getEndpoint());
		}

		camel.start();
		getLog().info("Started in: " + stopWatch.toString());
		while(running) {
			Thread.sleep(100);
		}
		camel.stop();
		getLog().info("Stopped after: " + stopWatch.toString());
	}

}
