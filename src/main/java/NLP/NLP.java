
/**
 * The {@code NLP} class provides functionalities for natural language processing tasks such as computing document similarity and searching.
 */
package NLP;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

import java.util.*;

/**
 * Constructs a new NLP object and initializes the Stanford CoreNLP pipeline.
 */
public class NLP {
    private String content;
    private StanfordCoreNLP pipeline;

    public NLP( ) {

        this.pipeline = Pipeline.getPipeline(); // Using the pipeline from Pipeline class
    }

    /**
     * Computes the similarity between the content of the NLP object and a given query.
     *
     * @param query the query for which similarity is computed
     * @return the similarity score between the content and the query
     */

    public double similarity(String query) {
        Annotation docAnnotation = new Annotation(content);
        Annotation queryAnnotation = new Annotation(query);

        pipeline.annotate(docAnnotation);
        pipeline.annotate(queryAnnotation);

        List<String> docTokens = extractTokens(docAnnotation);
        List<String> queryTokens = extractTokens(queryAnnotation);

        Map<String, Double> docTfIdfVector = computeTfIdfVector(docTokens);
        Map<String, Double> queryTfIdfVector = computeTfIdfVector(queryTokens);

        return computeCosineSimilarity(docTfIdfVector, queryTfIdfVector);
    }

    /**
     * Computes the TF-IDF vector for the given list of tokens.
     *
     * @param tokens the list of tokens for which TF-IDF vector is computed
     * @return a map representing the TF-IDF vector of the tokens
     */
    private Map<String, Double> computeTfIdfVector(List<String> tokens) {
        Map<String, Double> tfIdfVector = new HashMap<>();
        Map<String, Integer> termFrequency = new HashMap<>();
        int totalTerms = tokens.size();

        // Compute term frequency (TF)
        for (String token : tokens) {
            termFrequency.put(token, termFrequency.getOrDefault(token, 0) + 1);
        }

        // Compute inverse document frequency (IDF)
        Set<String> uniqueTokens = new HashSet<>(tokens);
        for (String token : uniqueTokens) {
            double tf = (double) termFrequency.get(token) / totalTerms;
            double idf = Math.log((double) tokens.size() / (1 + Collections.frequency(tokens, token)));
            tfIdfVector.put(token, tf * idf);
        }

        return tfIdfVector;
    }
    /**
     * Extracts tokens from the provided annotation.
     *
     * @param annotation the annotation from which tokens are extracted
     * @return a list of tokens extracted from the annotation
     */
    private List<String> extractTokens(Annotation annotation) {
        List<String> tokens = new ArrayList<>();
        for (CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class)) {
            for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
                String word = token.get(CoreAnnotations.TextAnnotation.class);
                tokens.add(word);
            }
        }
        return tokens;
    }

    private double computeCosineSimilarity(Map<String, Double> vector1, Map<String, Double> vector2) {
        Set<String> intersection = new HashSet<>(vector1.keySet());
        intersection.retainAll(vector2.keySet());

        double dotProduct = 0.0;
        double norm1 = 0.0;
        double norm2 = 0.0;

        for (String term : intersection) {
            dotProduct += vector1.get(term) * vector2.get(term);
        }

        for (double value : vector1.values()) {
            norm1 += Math.pow(value, 2);
        }

        for (double value : vector2.values()) {
            norm2 += Math.pow(value, 2);
        }

        double similarity = dotProduct / (Math.sqrt(norm1) * Math.sqrt(norm2));
        return similarity;
    }


    /**
     * Retrieves the content of the NLP object.
     *
     * @return the content of the NLP object
     */
    public String getContent() {
        return content;
    }

    /**
     * Searches for the most similar document to the given query among the provided list of documents.
     *
     * @param query the query to search for
     * @param documents the list of documents to search within
     * @return an array containing the most similar document and its similarity score
     */

    public String[] search(String query, ArrayList<String> documents) {
        ArrayList<String> queryTokens = new ArrayList<>(Arrays.asList(query.split(" ")));
        Map<String, Double> queryVector = computeTfIdfVector(queryTokens);
        String[] result = new String[2]; // Result array to hold the message and similarity score
        double maxSimilarity = Double.MIN_VALUE; // Initialize max similarity to minimum value
        String mostSimilarDocument = null;

        // Iterate through documents to find the most similar one
        for (String document : documents) {
            ArrayList<String> documentTokens = new ArrayList<>(Arrays.asList(document.split(" ")));
            Map<String, Double> documentVector = computeTfIdfVector(documentTokens);
            double similarity = computeCosineSimilarity(queryVector, documentVector);

            // Check if the current document is more similar than the previous one
            if (similarity > maxSimilarity) {
                maxSimilarity = similarity;
                mostSimilarDocument = document;
            }
        }

        // Populate the result array with the most similar document and its similarity score
        if (mostSimilarDocument != null) {
            result[0] = "Most similar document: " + mostSimilarDocument;
            result[1] = String.valueOf(maxSimilarity);
        } else {
            result[0] = "No documents found.";
            result[1] = "0"; // Or any other default value indicating no similarity
        }

        return result;
    }


}

