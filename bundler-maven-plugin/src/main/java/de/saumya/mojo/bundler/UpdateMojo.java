package de.saumya.mojo.bundler;

import java.io.IOException;

import org.apache.maven.plugin.MojoExecutionException;

import de.saumya.mojo.gem.AbstractGemMojo;
import de.saumya.mojo.ruby.gems.GemException;
import de.saumya.mojo.ruby.script.Script;
import de.saumya.mojo.ruby.script.ScriptException;

/**
 * maven wrapper around the bundler update command.
 * 
 * @goal update
 */
public class UpdateMojo extends AbstractGemMojo {

    /**
     * arguments for the bundler command.
     * 
     * @parameter default-value="${bundler.args}"
     */
    private String            bundlerArgs;
    
    /**
     * bundler version used when there is no pom. defaults to latest version.
     * DEPRECATED: declare a gem dependency with the desired version instead
     * 
     * @parameter default-value="${bundler.version}"
     */
    @Deprecated
    private String            bundlerVersion;

    /**
     * @parameter default-value="${repositorySystemSession}"
     * @readonly
     */
    private Object repoSession;

    
    @Override
    public void executeWithGems() throws MojoExecutionException,
            ScriptException, IOException, GemException {
        final Script script = this.factory.newScriptFromSearchPath("bundle");
        script.addArg("update");
        if (this.project.getBasedir() == null) {

            this.gemsInstaller.installGem("bundler",
                                          this.bundlerVersion,
                                          this.repoSession,
                                          this.localRepository,
                                          getRemoteRepos());

        }
        if (this.bundlerArgs != null) {
            script.addArgs(this.bundlerArgs);
        }
        if (this.args != null) {
            script.addArgs(this.args);
        }

        script.executeIn(launchDirectory());
    }
}
