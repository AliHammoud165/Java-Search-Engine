package Util;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

/**
 * The {@code WordDocumentReader} class reads text content from Word documents.
 */
public class WordDocumentReader implements DocumentReader {

    /**
     * Reads the text content of a Word document from the specified file path.
     *
     * @param filePath the file path of the Word document to read
     * @return the text content of the Word document
     * @throws IOException if an I/O error occurs while reading the document
     */
    @Override
    public String readDocument(String filePath) throws IOException {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(filePath);
            XWPFDocument document = new XWPFDocument(fis);
            List<XWPFParagraph> paragraphs = document.getParagraphs();
            StringBuilder sb = new StringBuilder();
            for (XWPFParagraph para : paragraphs) {
                sb.append(para.getText()).append("\n");
            }
            return sb.toString();
        } finally {
            if (fis != null) {
                fis.close();
            }
        }
    }
}
