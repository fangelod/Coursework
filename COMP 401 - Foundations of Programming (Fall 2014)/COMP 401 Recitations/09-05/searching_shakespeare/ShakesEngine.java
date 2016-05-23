// This class builds an index for the input corpus.
package searching_shakespeare;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.HashMap;

public class ShakesEngine {
  
  // Read lines from the given file.
  public static ArrayList<String> readLinesFromFile(String path_to_file) 
      throws IOException {
    try (Scanner scanner = new Scanner(new File(path_to_file))) {
       	ArrayList<String> lines = new ArrayList<String>();
    	while (scanner.hasNextLine()) {
    		lines.add(scanner.nextLine());
    	}
    	return lines;
    }
  }

  // Preprocess each line -- remove punctuations.
  public static ArrayList<String> preprocessLines(ArrayList<String> lines) {
    ArrayList<String> processed_lines = new ArrayList<String>();
    for (String line : lines) {
      // Replace all the punctuation with empty strings.
      processed_lines.add(line.replaceAll("\\p{Punct}", "").toLowerCase());
    }
    return processed_lines;
  }
  
  // Build an index structrure on given lines. The index structure is a map from
  // words to indices of lines that contains the word. This function takes an
  // ArrayList of lines from the input and returns a hashmap. The keys of the
  // hashmap are unique words; the value for each key is an ArrayList whose
  // elements are the line numbers where the key occurs. At the end, the size of
  // each ArrayList should equal to the number of occurences of the key.
  public static HashMap<String, ArrayList<Integer>> buildIndex(ArrayList<String> lines) {
	  HashMap<String, ArrayList<Integer>> index = new HashMap<String, ArrayList<Integer>>();
	  
	  // Index each line.
	  int line_num = 0;
	  for (String line : lines) {
      // Create a scanner for this line;
		  Scanner scanner = new Scanner(line);
		  while (scanner.hasNext()) {
			  String word = scanner.next();
			  if (!index.containsKey(word)) {
				  index.put(word, new ArrayList<Integer>());
			  }
			  index.get(word).add(line_num);
		  }
      // Increment the line number.
      ++line_num;
	  }
	  return index;
  	}

  // The main method.
  public static void main(String[] args) throws IOException {
    // Check the total number of arguments.
    if (args.length != 2) {
      System.out.println("Please provide path_to_file and query word.");
      return;
    }
    // Parse arguments.
    String path_to_file = args[0];
    String query = args[1].toLowerCase(); 

    // Build index structure for the file.

    // Read all the lines in the given file
    ArrayList<String> original_lines = readLinesFromFile(path_to_file);

    // Preprocess each line.
    ArrayList<String> lines = preprocessLines(original_lines);

    // Build the index.
    HashMap<String, ArrayList<Integer>> index = buildIndex(lines);
   
    // Search for the query and print out results.
    // Check if the query is in the index. 
    if (index.containsKey(query)) {
    	
      // **** TODO **** 
      // Get the Arraylist corresponding to the query from the hashmap and name
      // it "retreived_lines"

      // **** TODO ****
      // Print out how many times the query word appeared in the text.

      // **** TODO **** 
      //Print out each line which has the query word. Remember we have the
      // original lines stored in the arraylist "original_lines".

    } 
    else {
      System.out.format("%s appeared 0 times:%n", query);
    }
  }
}
