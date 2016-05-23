package shakesviewer_todo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MultiLineBook implements Book {

  public MultiLineBook(String author, String title) {
    this.author = author;
    this.title = title;
    this.lines = new ArrayList<String>();
  }

  @Override
  public String getAuthor() { return author; }
 
  @Override
  public String getTitle() { return title; }
  
  @Override
  public int getNumberOfLines() { return lines.size(); }

  @Override
  public String getLine(int lineNum) throws IndexOutOfBoundsException{
    // Line numbers start from 1 and we correct for that.
    return lines.get(lineNum-1);
  }

  public void readTextFromFileOrDie(String fileName) throws IOException {
    try(Scanner scanner = new Scanner(new File(fileName))) {
      while (scanner.hasNextLine()) {
        lines.add(scanner.nextLine());
      }
    }
  }

  private String author;
  private String title;
  private List<String> lines;
  
}
