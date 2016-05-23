package main;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;

public class InputWindow extends JFrame {
	
	private JTextField text;
	private JButton enter;

	// Sets up window, still need to make visible
	public InputWindow() {
		this.setTitle("Input Window");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		this.add(panel);
		addComponents(panel);

		this.pack();
	}
	
	// Helper method for the constructor
	private void addComponents(JPanel panel) {
		// TODO add "Plain Text:" label to 'panel'
		JLabel plain_text = new JLabel("Plain Text:");
		panel.add(plain_text);
		// TODO set instance var 'text' and add text field to 'panel'
		text = new JTextField(getInput());
		panel.add(text);
		// TODO set instance var 'enter' and add button to 'panel'
		enter = new JButton("enter");
		panel.add(enter);
		// TODO key listener for Enter button
		
		
		JLabel label = new JLabel("plain Text: ");
		text = new JTextField(TEXT_FIELD_SIZE);
		enter = new JButton("Enter");
		
		panel.add(label)
		
	}
	
	public void registerModel(Shifter model) {
		// TODO model listens to 'Enter' button
		
	}
	
	public String getInput() {
		// TODO return the contents of the text box
		String input_text = text.toString();
		
		return input_text;
	}
}
