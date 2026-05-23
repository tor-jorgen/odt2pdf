# Odt2Pdf

This library converts Open Office/Libre Office ODT documents to PDF documents.

It wraps `fr.opensagres.xdocreport:org.odftoolkit.odfdom.converter.pdf:1.0.6`, so that the
PDF converter can be used in projects that use the latest versions of `org.odftoolkit:odfdom-java`.

To achieve this, the library is packaged as a fat jar, and it shadows and relocates `org.odftoolkit`. The reason for
this is that `fr.opensagres.xdocreport:org.odftoolkit.odfdom.converter.pdf:1.0.6` uses an old version of  `odfdom-java`
(`org.odftoolkit:odfdom-java:0.8.7`).

## Usage

You can either:

* Download this library from GitHub in your build script
* or, download and build the library locally, and then include it in your build script

### Download from GitHub

You can download from GitHub in two different ways:

* Using GitHub Packages
* Using GitHub Release

#### Using GitHub Packages

When using a GitHub package, you can download the package from the GitHub Packages registry (a Maven repository), but
you will need to authenticate against the registry.

Add the following to your `build.gradle.kts` file:

```kotlin
repositories {
    maven { url = uri("https://maven.pkg.github.com/tor-jorgen/odt2pdf") }
}

val odt2pdfVersion = "1.1.0"
implementation("org.odt2pdf:odt2pdf:$odt2pdfVersion")
```

#### Using GitHub Release

When using a GitHub release, you can download the package without autenticating, but you have to use the
`de.undercouch.gradle.tasks.download.Download` plugin. You will not benefit from caching, but that is not a big issue.

Add the following to your `build.gradle.kts` file:

```kotlin   
val odt2pdfVersion = "1.1.0"

dependencies {
    implementation(files(layout.buildDirectory.file("libs/odt2pdf-$odt2pdfVersion-all.jar")))
}

// Download library from GitHub
tasks.register<de.undercouch.gradle.tasks.download.Download>("downloadOdt2pdf") {
    src("https://github.com/tor-jorgen/odt2pdf/releases/download/v1.1.0/odt2pdf-$odt2pdfVersion-all.jar")
    dest(layout.buildDirectory.file("libs/odt2pdf-$odt2pdfVersion-all.jar"))
}

tasks.named("compileKotlin") {
    dependsOn("downloadOdt2pdf")
}

```

### Download and build locally

Requires Java 25.

First you need to build and publish the library to your local Maven repository (Linux):

     ./gradlew clean build publishToMavenLocal

Ensure that you have added your local Maven repository, and add a dependency to the library in the build file
(in this example `build.gradle.kts`):

    repositories {
        mavenCentral()
        mavenLocal()
    }

    dependencies {
        implementation("org.odt2pdf", "odt2pdf", "1.1.0", classifier = "all")
    }

Add the following to your code (in this example Kotlin):

    import org.odt2pdf.PDFConverter
   
    val converter = PDFConverter()
    ...
    converter.fromOdf(<arguments>)

#### Logging

`OdfXMLFactory` uses `java.util.logging`. You can add the following code to disable logging from `OdfXMLFactory`
(Kotlin):

    import java.util.logging.LogManager

    LogManager.getLogManager().reset()

### Standalone

Run from the command line:

    java -jar [path]odt2pdf-<version>-all.jar [-h|--help]

Run without arguments, or with `-h` or `--help` to get help.

## License

See [xdocreport](https://github.com/opensagres/xdocreport)
