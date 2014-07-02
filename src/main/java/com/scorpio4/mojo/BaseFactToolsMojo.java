package com.scorpio4.mojo;

import org.apache.maven.project.MavenProject;

/**
 * Fact:Core (c) 2014
 * Module: com.scorpio4.maven
 * User  : lee
 * Date  : 16/06/2014
 * Time  : 5:36 PM
 */
public abstract class BaseFactToolsMojo extends ScorpioMojo {

    /**
     * @parameter default-value="${project}"
     * @required
     * @readonly
     */
    public MavenProject project;

	public BaseFactToolsMojo() {
    }

    public MavenProject getProject() {
        return project;
    }


    public abstract void executeInternal() throws Exception;
}
