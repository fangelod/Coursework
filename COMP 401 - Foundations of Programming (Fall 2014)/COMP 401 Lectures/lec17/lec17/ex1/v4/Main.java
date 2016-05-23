package lec17.ex1.v4;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.BorderLayout;
import java.awt.Color;

public class Main {

	public static void main(String[] args) {
		JFrame main_frame = new JFrame();
		main_frame.setTitle("Color Picker");
		main_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel main_panel = new JPanel();
		main_frame.setContentPane(main_panel);
		
		main_panel.setLayout(new BorderLayout());

		ColorChooser color_chooser = new ColorChooser();		
		
		color_chooser.addChangeListener(new ColorWatcher());
		
		main_panel.add(color_chooser, BorderLayout.CENTER);
		
		main_frame.pack();
		main_frame.setVisible(true);
	}
}

class ColorWatcher implements ChangeListener {

	@Override
	public void stateChanged(ChangeEvent e) {
		ColorChooser chooser = (ColorChooser) e.getSource();
		
		System.out.println("You changed the color to: " + chooser.getColor().toString());
	}
	
}