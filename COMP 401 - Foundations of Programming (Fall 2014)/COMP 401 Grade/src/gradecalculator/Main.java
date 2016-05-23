package gradecalculator;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		GradeCalculator calculator = new GradeCalculator();
		
		JFrame main_frame = new JFrame();
		main_frame.setTitle("Grade Calculator");
		main_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		main_frame.setContentPane(calculator);

		main_frame.pack();
		main_frame.setVisible(true);
	}
}
