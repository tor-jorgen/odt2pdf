import org.odt2pdf.PDFConverter
import java.util.logging.LogManager

fun main(args: Array<String>) {
    if (args.isEmpty() || args[0] == "-h" || args[0] == "--help") {
        println(
            """Usage: java -jar [path]odt2pdf-<version>-all.jar <input file path> [output file path]
            |Convert Open Office/Libre Office ODT documents to PDF.
            |
            |Arguments:
            |  input file path:  Path to ODF file to be converted
            |  output file path: Path to converted PDF file. Optional. Default is input file path, replacing the extension with pdf, or adding .pdf if no extension exists 
            """.trimMargin()
        )
        return
    }

    // Clear logging from OdfXMLFactory
    LogManager.getLogManager().reset()

    val out = PDFConverter().fromOdf(args[0], if (args.size > 1) args[1] else null)
    println("Converted '${args[0]}' to '$out'")
}
