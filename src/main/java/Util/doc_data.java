package Util;

import java.util.ArrayList;

/**
 * The {@code doc_data} class represents data extracted from documents.
 */
public class doc_data {
    private String data;
    private String Source;

    /**
     * Retrieves the data stored in this {@code doc_data} object.
     *
     * @return the data stored in this object
     */
    public String getData() {
        return data;
    }

    /**
     * Retrieves the source of the data stored in this {@code doc_data} object.
     *
     * @return the source of the data stored in this object
     */
    public String getSource() {
        return Source;
    }

    /**
     * Sets the data for this {@code doc_data} object.
     *
     * @param data the data to set
     */
    public void setData(String data) {
        this.data = data;
    }

    /**
     * Sets the source of the data for this {@code doc_data} object.
     *
     * @param source the source of the data to set
     */
    public void setSource(String source) {
        Source = source;
    }

    /**
     * Merges two lists of {@code doc_data} objects into one.
     *
     * @param list1 the first list of {@code doc_data} objects
     * @param list2 the second list of {@code doc_data} objects
     * @return the merged list of {@code doc_data} objects
     */
    public static ArrayList<doc_data> merge(ArrayList<doc_data> list1, ArrayList<doc_data> list2) {
        ArrayList<doc_data> mergedList = new ArrayList<>(list1);
        mergedList.addAll(list2);
        return mergedList;
    }
}
