import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.20"
    `maven-publish`
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("org.jlleitschuh.gradle.ktlint") version "11.0.0"
}

group = "org.odt2pdf"
version = "1.0.0"

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    implementation("fr.opensagres.xdocreport:org.odftoolkit.odfdom.converter.pdf:1.0.6")

    testImplementation(kotlin("test"))
}

publishing {
    publications {
        create<MavenPublication>("odt2pdf") {
            from(components["java"])
        }
    }

    repositories {
        mavenLocal()
    }
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

tasks {
    named<ShadowJar>("shadowJar") {
        archiveBaseName.set("odt2pdf")
        mergeServiceFiles()
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
