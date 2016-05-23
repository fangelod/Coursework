package main;

/** Main class for the program. */
public class Main {
	public static void main(String[] args) {
		// Set up model and view
		InputWindow inputView = new InputWindow();
		Shifter model = new Shifter(inputView);
		
		inputView.setVisible(true);
	}
}
