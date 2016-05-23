package shakesviewer_todo;

import java.io.IOException;

public class ShakesViewer {
  public static void main(String[] args) throws IOException, IndexOutOfBoundsException{

    // Load Hamlet
    System.out.println("> Load Hamlet as a book.");
    MultiLineBook hamlet = new MultiLineBook("William Shakespeare", "The Tragedie of Hamlet");
    hamlet.readTextFromFileOrDie("10-24/shakesviewer_todo/hamlet.txt");

    // Create a decorator viewer.
    System.out.println("> Create a default viewer and print the visible text.");
    ScrollableBookViewer hamletViewer = new ScrollableBookViewer(hamlet);
    // Print the author and the title of the book via the viewer.
    System.out.format("Author: %s, Title: %s%n", hamletViewer.getAuthor(), 
                      hamletViewer.getTitle());
    // Print the text in the default view (line 1-10).
    System.out.format("Viewer content [line %d - line %d]:%n%s%n", 
                      hamletViewer.getBeginning(), hamletViewer.getEnd(), 
                      hamletViewer.getTextInViewer());

    // Jump to the famous line, and display 5 lines of text.
    System.out.println("> Jump to the famous line. Change range to 5 lines.");
    hamletViewer.setViewerRange(2146, 5);
    System.out.format("Viewer content [line %d - line %d]:%n%s%n", 
                      hamletViewer.getBeginning(), hamletViewer.getEnd(), 
                      hamletViewer.getTextInViewer());
    
    // Scroll up by 2 lines and display visible text.
    System.out.println("> Scroll up by 2 lines.");
    hamletViewer.scrollUp(2);
    System.out.format("Viewer content [line %d - line %d]:%n%s%n", 
                      hamletViewer.getBeginning(), hamletViewer.getEnd(), 
                      hamletViewer.getTextInViewer());
    
    // Add this in myself. Trying to test code.
    System.out.println("> Test if amount would go beyond the first line");
    hamletViewer.setViewerRange(3, 5);
    hamletViewer.scrollUp(5);
    System.out.format("Viewer content [line%d - line%d]:%n%s%n",
    					hamletViewer.getBeginning(), hamletViewer.getEnd(),
    					hamletViewer.getTextInViewer());

    // Return the original book and print its author and title.
    System.out.println("> Return the original book.");
    Book original = hamletViewer.getOriginalBook();
    System.out.format("[Original Book] Author: %s, Title: %s%n", 
                      original.getAuthor(), original.getTitle());
  }
}
