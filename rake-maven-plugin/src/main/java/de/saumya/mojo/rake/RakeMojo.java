package de.saumya.mojo.rake;

import java.io.File;
import java.io.IOException;

import org.apache.maven.plugin.MojoExecutionException;

import de.saumya.mojo.gem.AbstractGemMojo;
import de.saumya.mojo.ruby.gems.GemException;
import de.saumya.mojo.ruby.script.Script;
import de.saumya.mojo.ruby.script.ScriptException;

/**
 * maven wrapper around the rake command.
 * 
 * @goal rake
 * @requiresDependencyResolution test
 */
public class RakeMojo extends AbstractGemMojo {

    /**
     * rakefile to be used for the rake command.
     * 
     * @parameter default-value="${rake.file}"
     */
    private final File              rakefile    = null;

    /**
     * arguments for the rake command.
     * 
     * @parameter default-value="${rake.args}"
     */
    private final String            rakeArgs    = null;

    /**
     * rake version used when there is no pom. defaults to latest version.
     * DEPRECATED: declare a gem dependency with the desired version instead
     * 
     * @parameter default-value="${rake.version}"
     */
    @Deprecated
    private final String            rakeVersion = null;

    /**
     * @parameter default-value="${repositorySystemSession}"
     * @readonly
     */
    private Object repoSession;

    @Override
    public void executeWithGems() throws MojoExecutionException,
            ScriptException, IOException, GemException {
        if (this.project.getBasedir() == null) {
            
            this.gemsInstaller.installGem("rake",
                                          this.rakeVersion,
                                          this.repoSession,
                                          this.localRepository,
                                          getRemoteRepos() );

        }
        final Script script = this.factory.newScriptFromJRubyJar("rake");
        if (this.rakefile != null){
            script.addArg("-f", this.rakefile);
        }
        
        if (this.rakeArgs != null) {
            script.addArgs(this.rakeArgs);
        }
        if (this.args != null) {
            script.addArgs(this.args);
        }

        script.executeIn(launchDirectory());
    }
}
