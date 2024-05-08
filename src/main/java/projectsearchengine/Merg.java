package projectsearchengine;

import Util.*;

import java.io.IOException;
import java.util.ArrayList;

/**
 * The {@code Merg} class represents a merger for combining data from different types of documents.
 */
public class Merg {

    private ArrayList<doc_data> ALL_DATA;

    /**
     * Constructs a new Merg object and initializes it by reading data from various types of documents.
     *
     * @throws IOException if an I/O error occurs while reading the documents
     */
    public Merg() throws IOException {
        ALL_DATA = new ArrayList<>();

        // Reading data from PDF documents
        PDFDocumentReader pdfReader = new PDFDocumentReader();
        String pdfContent = pdfReader.readDocument("pdf.pdf");
        ArrayList<doc_data> pdfData = pdfReader.Split(pdfContent, "pdf.pdf");

        // Reading data from Word documents
        WordDocumentReader wordReader = new WordDocumentReader();
        String wordContent1 = wordReader.readDocument("word1.docx");
        String wordContent2 = wordReader.readDocument("word2.docx");
        ArrayList<doc_data> wordData1 = wordReader.Split(wordContent1, "word1.docx");
        ArrayList<doc_data> wordData2 = wordReader.Split(wordContent2, "word2.docx");

        // Reading data from JSON documents
        JSONDocumentReader jsonReader = new JSONDocumentReader();
        String jsonContent = jsonReader.readDocument("OOP.json.txt");
        ArrayList<doc_data> jsonData = jsonReader.Split(jsonContent, "OOP.json");

        // Reading data from XML documents
        XMLDocumentReader xmlReader = new XMLDocumentReader();
        String xmlContent = xmlReader.readDocument("OOP.xml.txt");
        ArrayList<doc_data> xmlData = xmlReader.Split(xmlContent, "OOP.xml");

        // Combining data from all document types
        ALL_DATA.addAll(pdfData);
        ALL_DATA.addAll(wordData1);
        ALL_DATA.addAll(wordData2);
        ALL_DATA.addAll(jsonData);
        ALL_DATA.addAll(xmlData);
    }

    /**
     * Retrieves the merged data from different types of documents.
     *
     * @return the merged data as an ArrayList of doc_data objects
     */
    public ArrayList<doc_data> getALL_DATA() {
        return ALL_DATA;
    }

    /**
     * Sets the merged data to the specified ArrayList of doc_data objects.
     *
     * @param ALL_DATA the merged data to set
     */
    public void setALL_DATA(ArrayList<doc_data> ALL_DATA) {
        this.ALL_DATA = ALL_DATA;
    }
}
