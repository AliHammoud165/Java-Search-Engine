package Util;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * The {@code JSONDocumentReader} class reads JSON documents.
 */
public class JSONDocumentReader implements DocumentReader {

    /**
     * Reads the content of a JSON document from the specified file path.
     *
     * @param filePath the file path of the JSON document to read
     * @return the content of the JSON document as a string
     * @throws IOException if an I/O error occurs while reading the document
     */
    @Override
    public String readDocument(String filePath) throws IOException {
        File jsonFile = new File(filePath);
        return new String(Files.readAllBytes(jsonFile.toPath()));
    }
}
