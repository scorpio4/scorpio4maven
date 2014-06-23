package com.factcore.mojo;

import com.factcore.assets.AssetRegisters;
import com.factcore.deploy.SesameDeployer;
import com.factcore.vendor.camel.RDFRoutePlanner;
import com.factcore.vendor.camel.component.CRUDComponent;
import com.factcore.vendor.camel.component.CoreComponent;
import com.factcore.vendor.camel.component.SelfComponent;
import com.factcore.vendor.sesame.crud.SesameCRUD;
import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.JndiRegistry;

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

public class CamelAPIMojo extends MavenMojo {
	boolean running = true;

	@Override
	public void executeInternal() throws Exception {
		getLog().info("Camel API: " + getIdentity());

		SesameDeployer sesameDeployer = new SesameDeployer(getFactSpace());
		sesameDeployer.clean();
		sesameDeployer.setDeployRDF(true);
		sesameDeployer.setDeployScripts(true);
		sesameDeployer.deploy(getSrcPath());

		assetRegister = new AssetRegisters(getConnection());

		JndiRegistry jndi = new JndiRegistry();
		CamelContext camel = new DefaultCamelContext(jndi);

		RDFRoutePlanner routePlanner = new RDFRoutePlanner(camel, getFactSpace());
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
				jndi.bind((String) bean.get("this"), bound );
			}
		}

		camel.addComponent("self", new SelfComponent(this));
		camel.addComponent("crud", new CRUDComponent(crud));
		camel.addComponent("core", new CoreComponent(getFactSpace(), assetRegister ));

//		jndi.bind("self", this);
		getLog().info("API bound " + beans.size() + " beans in: " + stopWatch.toString());
//		Collection<Map> routeTemplates = crud.read("mojo/beans", null);

		routePlanner.plan();
		getLog().info("Planned "+camel.getRoutes().size()+" routes in: " + stopWatch.toString());

		camel.start();
		getLog().info("Started in: " + stopWatch.toString());
		while(running) {
			Thread.sleep(100);
		}
		camel.stop();
		getLog().info("Stopped after: " + stopWatch.toString());
	}

}
