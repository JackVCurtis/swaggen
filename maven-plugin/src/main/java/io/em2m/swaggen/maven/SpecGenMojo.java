package io.em2m.swaggen.maven;

import io.em2m.swaggen.SpecBuilder;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.util.Set;

/**
 * Generate swagger files for your specifications
 */
@Mojo(name = "generate", defaultPhase = LifecyclePhase.GENERATE_SOURCES)
public class SpecGenMojo extends AbstractMojo {

    @Parameter(name = "verbose", required = false, defaultValue = "false")
    private boolean verbose;

    /**
     * Location of the output directory.
     */
    @Parameter(name = "outputDir", defaultValue = "${project.build.directory}/classes/${project.groupId}/${project.artifactId}")
    private File outputDir;

    /**
     * The directory which contains scala/java source files
     */
    @Parameter(name = "sourceDir", property = "project.build.sourceDirectory")
    protected File sourceDir;

    /**
     * The list of profiles to process
     */
    @Parameter(name = "profiles", property = "swaggen.profiles")
    protected Set<String> profiles;

    /**
     * The list of services to process
     */
    @Parameter(name = "services", property = "swaggen.services")
    protected Set<String> services;

    @Parameter(property = "project.version", required = true)
    private String version;

    public void execute() throws MojoExecutionException {
        File specDir = new File(sourceDir.getParentFile(), "spec");
        new SpecBuilder(specDir, outputDir, version, services, profiles).build();
    }

}
