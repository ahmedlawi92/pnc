package org.jboss.pnc.integration.deployments;

import org.jboss.arquillian.container.test.api.Testable;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.GenericArchive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.container.LibraryContainer;
import org.jboss.shrinkwrap.api.importer.ZipImporter;
import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.PomEquippedResolveStage;

import java.io.File;
import java.util.stream.Stream;

public class Deployments {

    public static EnterpriseArchive baseEar() {
        EnterpriseArchive ear = ShrinkWrap.create(EnterpriseArchive.class);
        PomEquippedResolveStage mavenResolver = Maven.resolver().loadPomFromFile(new File("pom.xml"));

        addEar(ear, mavenResolver);

        setTestableWar(ear);

        return ear;
    }

    public static EnterpriseArchive baseEarWithTestDependencies() {
        EnterpriseArchive ear = ShrinkWrap.create(EnterpriseArchive.class);
        PomEquippedResolveStage mavenResolver = Maven.resolver().loadPomFromFile(new File("pom.xml"));

        addEar(ear, mavenResolver);
        addAssertJ(ear, mavenResolver);
        addRestAssured(ear, mavenResolver);

        setTestableWar(ear);

        return ear;
    }

    private static void setTestableWar(EnterpriseArchive ear) {
        WebArchive restWar = ear.getAsType(WebArchive.class, "/pnc-rest.war");
        ear.addAsModule(Testable.archiveToTest(restWar));
    }

    private static void addAssertJ(LibraryContainer<?> webArchive, PomEquippedResolveStage mavenResolver) {
        File[] assertJ = mavenResolver.resolve("org.assertj:assertj-core:1.7.0").withTransitivity().asFile();
        Stream.of(assertJ).forEach(lib -> webArchive.addAsLibrary(lib));
    }

    private static void addRestAssured(LibraryContainer<?> webArchive, PomEquippedResolveStage mavenResolver) {
        File[] assertJ = mavenResolver.resolve("com.jayway.restassured:rest-assured:2.4.0").withTransitivity().asFile();
        Stream.of(assertJ).forEach(lib -> webArchive.addAsLibrary(lib));
    }

    private static void addEar(Archive<?> webArchive, PomEquippedResolveStage mavenResolver) {
        File[] manuallyAddedLibs = mavenResolver.resolve("org.jboss.pnc:ear-package:ear:1.0-SNAPSHOT").withoutTransitivity().asFile();
        Stream.of(manuallyAddedLibs).forEach(lib -> webArchive.merge(ShrinkWrap.create(ZipImporter.class).
                importFrom(lib).as(GenericArchive.class)));
    }
}
