package org.odt2pdf

import org.odftoolkit.odfdom.converter.pdf.PdfConverter
import org.odftoolkit.odfdom.converter.pdf.PdfOptions
import org.odftoolkit.odfdom.doc.OdfTextDocument
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File

/** A class used to convert an ODF document to a PDF document */
class PDFConverter {
    private val converter = PdfConverter.getInstance()
    private val regex = Regex("""\..*$""")

    /**
     * Convert an ODF document to PDF
     *
     * @param inputFilePath path to file containing the ODF document
     * @param outputFilePath the path to write the PDF to. If null,
     *     inputFilePath will be used, replacing the extension with "pdf", or
     *     adding ".pdf" if no extension exists
     * @return path of output file
     */
    fun fromOdf(
        inputFilePath: String,
        outputFilePath: String? = null,
    ): String {
        val document = OdfTextDocument.loadDocument(File(inputFilePath))
        val out = outputFilePath ?: pdfSuffix(inputFilePath)
        fromOdf(document, out)

        return out
    }

    /**
     * Convert an ODF document to PDF
     *
     * @param document a byte array containing the ODF document
     * @param outputFilePath the path to write the PDF to
     */
    fun fromOdf(
        document: ByteArray,
        outputFilePath: String,
    ) {
        val textDocument = OdfTextDocument.loadDocument(ByteArrayInputStream(document))
        fromOdf(textDocument, outputFilePath)
    }

    /**
     * Convert an ODF document to PDF
     *
     * @param document the ODF document
     * @param outputFilePath the path to write the PDF to
     */
    fun fromOdf(
        document: OdfTextDocument,
        outputFilePath: String,
    ) {
        val options = PdfOptions.create()
        File(outputFilePath).outputStream().use {
            converter.convert(document, it, options)
        }
    }

    /**
     * Convert an ODF document to PDF
     *
     * @param document a byte array containing the ODF document
     * @return the PDF as a ByteArrayOutputStream
     */
    fun fromOdf(document: ByteArray): ByteArrayOutputStream {
        val options = PdfOptions.create()
        val out = ByteArrayOutputStream()
        val textDocument = OdfTextDocument.loadDocument(ByteArrayInputStream(document))
        converter.convert(textDocument, out, options)
        return out
    }

    private fun pdfSuffix(inputFilePath: String): String =
        if (inputFilePath.contains(regex)) {
            inputFilePath.replace(regex, ".pdf")
        } else {
            "$inputFilePath.pdf"
        }
}
