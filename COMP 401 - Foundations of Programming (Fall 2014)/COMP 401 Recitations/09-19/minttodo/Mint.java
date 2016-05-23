package minttodo;

import java.io.IOException;
import java.util.ArrayList;

// Commandline interface
//   Usage: Mint <file1> [file2] ...
public class Mint {
  public static void main(String[] args) {
    // Check the number of arguments
    if (args.length == 0) {
      System.out.println("Usage: Mint <file1> [file2] ...");
      return;
    }
    
    // Create a MintManager for each file;
    ArrayList<MintManager> managers= new ArrayList<MintManager>();
    for (int i = 0; i < args.length; ++i) {
      try {
        MintManager m = new MintManager(args[i]);
        managers.add(m);
      } catch (IOException e) {
        System.err.println("Error: cannot find or read file: " + args[i]);
      }
    }

    // Mint
    int totalErrors = 0;
    for (MintManager m : managers) {
      m.check();
      totalErrors += m.getNumErrors();
      System.out.println(m.getErrors());
    }
    System.out.println(totalErrors + " errors.");
  }
}
