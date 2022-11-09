package org.odt2pdf

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.odftoolkit.odfdom.doc.OdfTextDocument
import java.io.ByteArrayOutputStream
import java.io.File
import java.nio.file.Files
import java.nio.file.Path

internal class PDFConverterTest {

    @Test
    fun `fromOdf converts file and replaces dot odt`() {
        val pdfConverter = PDFConverter()
        pdfConverter.fromOdf("src/test/resources/test.odt")

        assertTrue(Files.exists(Path.of("src/test/resources/test.pdf")))
    }

    @Test
    fun `fromOdf converts file and appends dot pdf`() {
        val pdfConverter = PDFConverter()
        pdfConverter.fromOdf("src/test/resources/test")

        assertTrue(Files.exists(Path.of("src/test/resources/test.pdf")))
    }

    @Test
    fun `fromOdf converts file and uses provided name`() {
        val pdfConverter = PDFConverter()
        pdfConverter.fromOdf("src/test/resources/test.odt", "src/test/resources/test.pdf")

        assertTrue(Files.exists(Path.of("src/test/resources/test.pdf")))
    }

    @Test
    fun `fromOdf converts OdfTextDocument`() {
        val pdfConverter = PDFConverter()
        OdfTextDocument.loadDocument(File("src/test/resources/test.odt")).use { document ->
            pdfConverter.fromOdf(document, "src/test/resources/test.pdf")
        }

        assertTrue(Files.exists(Path.of("src/test/resources/test.pdf")))
    }

    @Test
    fun `fromOdf converts ByteArray`() {
        val pdfConverter = PDFConverter()
        OdfTextDocument.loadDocument(File("src/test/resources/test.odt")).use { document ->
            ByteArrayOutputStream().use { bytes ->
                document.save(bytes)
                pdfConverter.fromOdf(bytes.toByteArray(), "src/test/resources/test.pdf")
            }
        }

        assertTrue(Files.exists(Path.of("src/test/resources/test.pdf")))
    }

    @AfterEach
    fun cleanup() {
        Files.deleteIfExists(Path.of("src/test/resources/test.pdf"))
    }
}
