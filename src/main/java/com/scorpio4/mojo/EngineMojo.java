package com.scorpio4.mojo;

import com.scorpio4.deploy.Scorpio4SesameDeployer;
import com.scorpio4.runtime.Engine;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Scorpio4 (c) 2014
 * Module: com.scorpio4.maven
 * User  : lee
 * Date  : 16/06/2014
 * Time  : 5:38 PM
 */

/**
 * Semantic Engine
 *
 * @goal run
 * @requiresProject true
 * @phase process-sources
 */

public class EngineMojo extends BaseFactToolsMojo {
	boolean running = true;

	@Override
	public void executeInternal() throws Exception {
		getLog().info("Camel API: " + getIdentity());

		Scorpio4SesameDeployer scorpio4SesameDeployer = new Scorpio4SesameDeployer(getFactSpace());
		scorpio4SesameDeployer.clean();
		scorpio4SesameDeployer.setDeployRDF(true);
		scorpio4SesameDeployer.setDeployScripts(true);
		scorpio4SesameDeployer.deploy(getSrcPath());

		Engine engine = new Engine(getIdentity(), repositoryManager, getConfig());
		engine.start();
		engine.run();
		engine.stop();
	}

	public Map<String,String> getConfig() {
		Map map = new HashMap<String,String>();
		Properties properties = getProject().getProperties();
		for(Object property: properties.keySet()) {
			String key = property.toString();
			key = key.replace(".","_");
			Object value = properties.get(property);
			boolean isLocal = key.startsWith("scorpio4_")||key.startsWith("scorpio4:");
			if (value!=null&&isLocal) {
				map.put(key, value.toString());
			}
		}
		return map;

	}

}
