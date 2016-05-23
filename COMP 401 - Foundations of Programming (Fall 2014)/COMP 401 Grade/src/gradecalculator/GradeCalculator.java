package gradecalculator;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GradeCalculator extends JPanel implements ActionListener {
	
	private JTextField a1;
	private JTextField a2;
	private JTextField a3;
	private JTextField a4;
	private JTextField a5;
	private JTextField a6;
	private JTextField a7;
	private JTextField a8;
	private JTextField recitation;
	private JTextField midterm1;
	private JTextField midterm2;
	private JTextField grade;
	JTextField[] text_fields = new JTextField[] {a1, a2, a3, a4,
			a5, a6, a7, a8, recitation, midterm1, midterm2, grade};
	
	public GradeCalculator(){
		addAssignmentPanel();
		addOtherPanel();
	}

	private void addAssignmentPanel(){
		JPanel a_panel = new JPanel();
		a_panel.setLayout(new BoxLayout(a_panel, BoxLayout.PAGE_AXIS));
		
		for (int i=1;i<=8;i++){
			JPanel a = new JPanel();
			a.add(new JLabel ("Assignment "+i+": "), BorderLayout.WEST);
			text_fields[i-1] = new JTextField();
			text_fields[i-1].setPreferredSize(new Dimension(50,25));
			a.add(text_fields[i-1], BorderLayout.EAST);
			a_panel.add(a);
		}
		
		add(a_panel, BorderLayout.WEST);

	}
	
	private void addOtherPanel(){
		JPanel b_panel = new JPanel();
		b_panel.setLayout(new BoxLayout(b_panel, BoxLayout.PAGE_AXIS));
		
		String[] labels = new String[] {"Recitation (out of 12)", 
				"Midterm 1", "Midterm 2", "Desired Grade"};
		
		for (int i=8;i<text_fields.length;i++){
			JPanel b = new JPanel();
			b.add(new JLabel(labels[i-8]),  BorderLayout.WEST); 
			text_fields[i] = new JTextField();
			text_fields[i].setPreferredSize(new Dimension(50,25));
			b.add(text_fields[i], BorderLayout.CENTER);
			b_panel.add(b);
		}
		
		JButton submit = (new JButton("What do I need?"));
		submit.addActionListener(this);
		b_panel.add(submit);
	
		add(b_panel, BorderLayout.EAST);
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		double assignment_points=0;

		for (int i=0;i<8; i++){
			Double n = Double.parseDouble(text_fields[i].getText());
			assignment_points += n;
		}		
		double assignment_grade=getMappedGrade(assignment_points / 76*100);
		
		//i=8

		Double r = Double.parseDouble(text_fields[8].getText());
		double recitation_grade = getMappedGrade(r/12*100);

		
		Double m1 = Double.parseDouble(text_fields[9].getText());
		Double m2 = Double.parseDouble(text_fields[10].getText());
		
		double grade_so_far = assignment_grade*.4 + recitation_grade*.1+
				m1*.15 + m2*.15; 
		
		String goal = text_fields[11].getText();
		double needed_grade = 0;
		switch (goal){
		case "A":
			needed_grade = 3.85;  //
			break;
		case "A-":
			needed_grade = 3.5;
			break;
		case "B+":
			needed_grade = 3.15;
			break;
		case "B":
			needed_grade = 2.85;
			break;
		case "B-":
			needed_grade = 2.5;
			break;
		case "C+":
			needed_grade = 2.15;
			break;
		case "C":
			needed_grade = 1.85;
			break;
		case "C-":
			needed_grade = 1.5;
			break;
		case "D+":
			needed_grade = 1.15;
			break;
		case "D":
			needed_grade = .85;
			break;
		case "D-":
			needed_grade = .5;
			break;
		}
		
		double you_need = (needed_grade - grade_so_far)/.2;
		String message = String.valueOf(you_need).substring(0,4);
		
		JOptionPane.showMessageDialog(this, 
				"You need a "+ message + " on the final.");
}	
	
	private double getMappedGrade(double score){
		if (score >= 95){
			return 4;
		} else if (score >=90){
			return (score-90)/10+3.5;
		} else if (score>69){
			return  (3.5-(90-score)/10);
		} else if (score<41){
			return 0;
		} else {
			 return (score-40)/30*1.5;
		}
	}

}
