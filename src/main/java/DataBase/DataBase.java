/**
 * The {@code DataBase} class provides functionalities for interacting with a MongoDB database.
 */
package DataBase;

import NLP.NLP;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
public class DataBase {
    private String host;
    private int port;

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }
    /**
     * Constructs a new DataBase object with the specified host and port.
     *
     * @param port the port number of the MongoDB server
     * @param host the host address of the MongoDB server
     */
   public DataBase(int port, String host) {
        this.port = port;
        this.host = host;
    }
    /**
     * Retrieves search history data from the database.
     *
     * @return an array of history_DB objects representing the search history data
     */
    public history_DB[] getDATA() {
        MongoClient mongoClient = MongoClients.create("mongodb://" + host + ":" + port);
        MongoDatabase database = mongoClient.getDatabase("History");
        MongoCollection<Document> collection = database.getCollection("history");

        List<history_DB> historyList = new ArrayList<>();

        MongoCursor<Document> cursor = collection.find().iterator();
        while (cursor.hasNext()) {
            Document doc = cursor.next();
            String search = doc.getString("Search");
            String results = doc.getString("Results");

            history_DB history = new history_DB();
            history.setSearch(search);
            history.setResults(results);
            historyList.add(history);
        }

        cursor.close();
        mongoClient.close();

        return historyList.toArray(new history_DB[0]);
    }
    /**
     * Searches for the most similar document to the given query in the database.
     *
     * @param whatYouWant the query to search for
     * @param DATA the array of history_DB objects representing the search history data
     * @return the similarity score between the query and the most similar document
     */
    public double simentecSearch(String whatYouWant, history_DB DATA[]) {
        String mostSimilarDocument = null;
        String mostSimilarResults = null;
        double maxSimilarity = Double.MIN_VALUE;

        ArrayList<String> searchList = new ArrayList<>();
        ArrayList<String> resultList = new ArrayList<>();

        for (history_DB document : DATA) {
            searchList.add(document.getSearch());
            resultList.add(document.getResults());
        }

        NLP nlp = new NLP();

        String[] result = nlp.search(whatYouWant, searchList);

        String mostSimilarDocumentAndScore = null;
        double similarity = 0.0;

        if (result.length >= 2) {
            mostSimilarDocumentAndScore = result[0];
            similarity = Double.parseDouble(result[1]);

            if (mostSimilarDocumentAndScore != null && mostSimilarDocumentAndScore.startsWith("Most similar document: ")) {
                mostSimilarDocument = mostSimilarDocumentAndScore.substring("Most similar document: ".length());
            }
        }

        int index = searchList.indexOf(mostSimilarDocument);

        if (index != -1 && index < resultList.size()) {
            mostSimilarResults = resultList.get(index);
        }

        if (mostSimilarDocument != null) {
            System.out.println("Most similar document: " + mostSimilarDocument);
            System.out.println("Results: " + mostSimilarResults);
            System.out.println("Similarity Score: " + similarity);
        } else {
            System.out.println("No documents found in DB.");
        }

        return similarity;
    }

    public void addEntryToDB(String searchValue, String resultValue) {
        try (MongoClient mongoClient = MongoClients.create("mongodb://" + host + ":" + port)) {
            MongoDatabase database = mongoClient.getDatabase("History");
            MongoCollection<Document> collection = database.getCollection("history");

            Document document = new Document();
            document.append("Search", searchValue);
            document.append("Results", resultValue);

            collection.insertOne(document);

            System.out.println("New entry added to the database.");
        }
    }

    public void updateDocument(String searchField, String searchValue, String updateField, String updateValue) {
    try (MongoClient mongoClient = MongoClients.create("mongodb://" + host + ":" + port)) {
        MongoDatabase database = mongoClient.getDatabase("History");
        MongoCollection<Document> collection = database.getCollection("history");

        UpdateResult result = collection.updateMany(Filters.eq(searchField, searchValue),
                Updates.set(updateField, updateValue));

        System.out.println("Documents matched: " + result.getMatchedCount());
        System.out.println("Documents modified: " + result.getModifiedCount());

    }
}

public void deleteDocument(String field, String value) {
    try (MongoClient mongoClient = MongoClients.create("mongodb://" + host + ":" + port)) {
        MongoDatabase database = mongoClient.getDatabase("History");
        MongoCollection<Document> collection = database.getCollection("history");

        DeleteResult result = collection.deleteMany(Filters.eq(field, value));

        System.out.println("Documents deleted: " + result.getDeletedCount());
    }
}

    public void clearAllDocuments() {
        try (MongoClient mongoClient = MongoClients.create("mongodb://" + host + ":" + port)) {
            MongoDatabase database = mongoClient.getDatabase("History");
            MongoCollection<Document> collection = database.getCollection("history");

            DeleteResult result = collection.deleteMany(new Document());

            System.out.println("All documents deleted: " + result.getDeletedCount());
        }
    }
}