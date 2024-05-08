package Util;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;

/**
 * The {@code PDFDocumentReader} class reads text content from PDF documents.
 */
public class PDFDocumentReader implements DocumentReader {

    /**
     * Reads the text content of a PDF document from the specified file path.
     *
     * @param filePath the file path of the PDF document to read
     * @return the text content of the PDF document
     * @throws IOException if an I/O error occurs while reading the document
     */
    @Override
    public String readDocument(String filePath) throws IOException {
        File file = new File(filePath);
        try (PDDocument document = Loader.loadPDF(file)) {
            PDFTextStripper stripper = new PDFTextStripper();
            return stripper.getText(document);
        }
    }
}
