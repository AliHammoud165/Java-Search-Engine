package NLP;

import Util.doc_data;

import java.util.ArrayList;

/**
 * The {@code Search} class provides functionalities for searching among a list of documents.
 */
public class Search {
    ArrayList<doc_data> documents;
    String whatYouWant;

    /**
     * Constructs a new Search object with the provided list of documents and search query.
     *
     * @param documents the list of documents to search within
     * @param whatYouWant the search query
     */
    public Search(ArrayList<doc_data> documents, String whatYouWant) {
        this.documents = documents;
        this.whatYouWant = whatYouWant;
    }
    /**
     * Performs a similarity-based search among the documents using the provided search query.
     *
     * @return the content of the most similar document found
     */
    public String simentecSearch() {
        NLP nlp = new NLP();
        String Save="";
        String mostSimilarDocumentData = null;
        String mostSimilarDocumentSource = null;
        double maxSimilarity = Double.MIN_VALUE;

        for (doc_data doc : documents) {
            ArrayList<String> w = new ArrayList<>();
            w.add(doc.getData());

            String[] result = nlp.search(whatYouWant, w);
            double similarity = Double.parseDouble(result[1]);

            if (similarity > maxSimilarity) {
                maxSimilarity = similarity;
                mostSimilarDocumentData = doc.getData();
                mostSimilarDocumentSource = doc.getSource();
            }
        }

        if (mostSimilarDocumentData != null) {
            System.out.println("Most similar document data: " + mostSimilarDocumentData);
            System.out.println("Source: " + mostSimilarDocumentSource);
            Save=mostSimilarDocumentData;
        } else {
            System.out.println("No documents found.");
        }

        return Save;
    }
}
