package projectsearchengine;

import DataBase.DataBase;
import DataBase.history_DB;
import NLP.Search;
import Util.doc_data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * The {@code ProjectSearchEngine} class represents the main entry point for the search engine project.
 */

public class ProjectSearchEngine {

    /**
     * The main method that initializes and manages the search engine functionality.
     *
     * @param args command line arguments (not used)
     * @throws IOException if an I/O error occurs while interacting with files or databases
     */

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        String x="y";
while (x.equals("y")) {
    System.out.println("Hello, how can I help you? To search, enter 'S'. To delete, enter 'd':");
    String choice = scanner.nextLine(); // Read user's choice

    if (choice.equalsIgnoreCase("S")) {
        System.out.println("Enter your search query:");
        String query = scanner.nextLine(); // Read search query from user

        DataBase D1 = new DataBase(27017, "localhost");
        history_DB DATABASE[];
        DATABASE = D1.getDATA();
        double similarity = D1.simentecSearch(query, DATABASE);

        if (similarity < 0.6) {
     ArrayList<doc_data>mergedList=new ArrayList<>();
     Merg M=new Merg();

     mergedList=M.getALL_DATA();

            Search search = new Search(mergedList, query);
            String result = search.simentecSearch();
            D1.addEntryToDB(query, result);
        }
    } else if (choice.equalsIgnoreCase("d")) {
        DataBase D1 = new DataBase(27017, "localhost");

        System.out.println("how you want to delete Clear_all (c) one by one (o)");
        String Delete=scanner.nextLine();
        if(Delete.equals("o")){
        System.out.println("What you want to delete");
        String D=scanner.nextLine();
        D1.deleteDocument("Search", D);
        }

        if(Delete.equals("c")){
            D1.clearAllDocuments();
        }
    } else {
        System.out.println("Invalid choice. Please enter 'S' to search or 'd' to delete.");
    }

    System.out.println("Can I help you with any thing else (y/n)");
    x=scanner.nextLine();
}
        scanner.close();
    }
}
