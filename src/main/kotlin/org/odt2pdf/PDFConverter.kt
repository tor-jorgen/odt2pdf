package org.odt2pdf

import org.odftoolkit.odfdom.converter.pdf.PdfConverter
import org.odftoolkit.odfdom.converter.pdf.PdfOptions
import org.odftoolkit.odfdom.doc.OdfTextDocument
import java.io.ByteArrayInputStream
import java.io.File

/** A class used to convert a ODF document to a PDF document */
class PDFConverter {
    private val converter = PdfConverter.getInstance()
    private val regex = Regex("""\..*$""")

    /**
     * Convert ODF document to PDF
     *
     * @param inputFilePath path to file containing the ODF document
     * @param outputFilePath path to file that will contain the PDF. If null,
     *     inputFilePath will be used, replacing the extension with pdf, or
     *     adding .pdf if no extension exists
     * @return path of output file
     */
    fun fromOdf(inputFilePath: String, outputFilePath: String? = null): String {
        val document = OdfTextDocument.loadDocument(File(inputFilePath))
        val out = outputFilePath ?: pdfSuffix(inputFilePath)
        fromOdf(document, out)

        return out
    }

    /**
     * Convert ODF document to PDF
     *
     * @param document a byte array containing the ODF document
     * @param outputFilePath path to file that will contain the PDF
     */
    fun fromOdf(document: ByteArray, outputFilePath: String) {
        val textDocument = OdfTextDocument.loadDocument(ByteArrayInputStream(document))
        fromOdf(textDocument, outputFilePath)
    }

    /**
     * Convert ODF document to PDF
     *
     * @param document the ODF document
     * @param outputFilePath path to file that will contain the PDF
     */
    fun fromOdf(document: OdfTextDocument, outputFilePath: String) {
        val options = PdfOptions.create()
        File(outputFilePath).outputStream().use {
            converter.convert(document, it, options)
        }
    }

    private fun pdfSuffix(inputFilePath: String): String {
        return if (inputFilePath.contains(regex)) {
            inputFilePath.replace(regex, ".pdf")
        } else {
            "$inputFilePath.pdf"
        }
    }
}
