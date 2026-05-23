import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

val kotlinVersion: String by project
val javaLanguageVersion: String by project

plugins {
    kotlin("jvm")
    `maven-publish`
    id("com.gradleup.shadow") version "9.4.1"
    id("org.jlleitschuh.gradle.ktlint") version "14.2.0"
    id("dev.detekt") version ("2.0.0-alpha.3")
    id("org.sonarqube") version "7.3.0.8198"
}

group = "org.odt2pdf"
version = "1.1.0"

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    val pdfConverterVersion = "1.0.6"
    implementation("fr.opensagres.xdocreport:org.odftoolkit.odfdom.converter.pdf:$pdfConverterVersion")

    testImplementation(kotlin("test"))
}

publishing {
    publications {
        create<MavenPublication>("odt2pdf") {
            artifact(tasks.named("shadowJar"))
        }
    }

    repositories {
        mavenLocal()

        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/tor-jorgen/odt2pdf")
            credentials {
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of(javaLanguageVersion))
    }
}

tasks.register("printVersion") {
    group = "help"
    description = "Prints the project version."

    doLast {
        println(project.version)
    }
}

tasks {
    named<ShadowJar>("shadowJar") {
        archiveBaseName.set("odt2pdf")
        mergeServiceFiles()

        // Only pull in runtime dependencies
        configurations = listOf(project.configurations.runtimeClasspath.get())

        manifest {
            attributes(mapOf("Main-Class" to "MainKt"))
        }
        // fr.opensagres.xdocreport uses an old version of odftoolkit (org.odftoolkit:odfdom-java:0.8.7),
        // so rename odftoolkit to avoid conflicts when this library is included in other projects
        relocate("org.odftoolkit", "shadow.odftoolkit")
    }
}

tasks {
    build {
        dependsOn(shadowJar)
    }
}

sonar {
    properties {
        property("sonar.organization", "tor-jorgen")
        property("sonar.projectKey", "tor-jorgen_odt2pdf")
        property("sonar.projectName", "ODT to PDF")
        property("sonar.host.url", "https://sonarcloud.io")
    }
}
