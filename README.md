# Odt2Pdf

This library converts Open Office/Libre Office ODT documents to PDF documents.

It wraps `fr.opensagres.xdocreport:org.odftoolkit.odfdom.converter.pdf:1.0.6`, so that the
PDF converter can be used in projects that use the latest versions of `org.odftoolkit:odfdom-java`.

To achieve this, the library is packaged as a fat jar, and it shadows and relocates `org.odftoolkit`. The reason for
this is that `fr.opensagres.xdocreport:org.odftoolkit.odfdom.converter.pdf:1.0.6` uses an old version of  `odfdom-java`
(`org.odftoolkit:odfdom-java:0.8.7`).

## Usage

### Library
First you need to build and publish the library to your local Maven repository (Linux):

     ./gradlew clean build publishToMavenLocal

Ensure that you have added your local Maven repository, and add a dependency to the library in the build file 
(in this example `build.gradle.kts`):

    repositories {
        mavenCentral()
        mavenLocal()
    }

    dependencies {
        implementation("org.odt2pdf", "odt2pdf", "1.0", classifier = "all")
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
