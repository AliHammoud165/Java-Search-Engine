package DataBase;

/**
 * The {@code history_DB} class represents data stored in the database related to search history.
 */
public class history_DB {

    String Search;
    String Results;

    /**
     * Sets the search query in this {@code history_DB} object.
     *
     * @param search the search query to set
     */
    public void setSearch(String search) {
        Search = search;
    }

    /**
     * Sets the search results in this {@code history_DB} object.
     *
     * @param results the search results to set
     */
    public void setResults(String results) {
        Results = results;
    }

    /**
     * Retrieves the search results stored in this {@code history_DB} object.
     *
     * @return the search results
     */
    public String getResults() {
        return Results;
    }

    /**
     * Retrieves the search query stored in this {@code history_DB} object.
     *
     * @return the search query
     */
    public String getSearch() {
        return Search;
    }
}
