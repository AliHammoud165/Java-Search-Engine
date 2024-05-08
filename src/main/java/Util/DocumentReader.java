
/**
 * The {@code DocumentReader} interface provides methods for reading documents and splitting them into data units.
 */
package Util;

import java.io.IOException;
import java.util.ArrayList;

public interface DocumentReader {
    /**
     * Reads the content of a document from the specified file path.
     *
     * @param filePath the file path of the document
     * @return the content of the document as a string
     * @throws IOException if an I/O error occurs while reading the document
     */
    String readDocument(String filePath) throws IOException;
    /**
     * Splits the document content into data units.
     *
     * @param doc the document content to be split
     * @param source the source identifier for the document content
     * @return an ArrayList of doc_data objects representing the data units
     */
    default ArrayList<doc_data> Split(String doc, String source) {
        String[] contentLines = doc.split("\\n");
        ArrayList<doc_data> lines = new ArrayList<>();
        for (String line : contentLines) {
            doc_data data = new doc_data();
            data.setData(line);
            data.setSource(source); // Assuming "ali" is the default source
            lines.add(data);
        }
        return lines;
    }
}