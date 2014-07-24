package com.scorpio4.mojo;

import com.scorpio4.deploy.Scorpio4SesameDeployer;
import com.scorpio4.runtime.Engine;

import java.util.Map;

/**
 * Scorpio4 (c) 2014
 * Module: com.scorpio4.maven
 * @author lee
 * Date  : 16/06/2014
 * Time  : 5:38 PM
 */

/**
 * Maven Engine
 *
 * @goal Start Engine
 * @requiresProject true
 * @phase process-sources
 */

public class EngineMojo extends ScorpioMojo {

	@Override
	public void executeInternal() throws Exception {
		getLog().info("Booting Scorpio4 @ " + getIdentity());

		Scorpio4SesameDeployer scorpio4SesameDeployer = new Scorpio4SesameDeployer(getIdentity(), getConnection());
		scorpio4SesameDeployer.clean();
		scorpio4SesameDeployer.setDeployRDF(true);
		scorpio4SesameDeployer.setDeployScripts(true);
		scorpio4SesameDeployer.deploy(getSrcPath());

		Map<String,String> config = getConfig();
		getLog().debug("Configuration: "+config);
		Engine engine = new Engine(getIdentity(), getRepositoryManager(), config);

		engine.start();
		getLog().info("Started: " + getIdentity());
		while(engine.isRunning()) {
			Thread.sleep(1000);
		}
		getLog().info("Stopping: " + getIdentity());
		engine.stop();
	}


}
