package a8;


import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

import comp401.sushi.*;

public class SushiWorkstationWidget extends JPanel {

	/* Do not change the following line. */
	private List<WorkstationListener> listeners;

	public SushiWorkstationWidget() {
		/* Do not change the following line*/
		listeners = new ArrayList<WorkstationListener>();
		
		/* Replace the following line with your own code. */
		add(new JLabel("Placeholder to show something"));
	}


	/* Do not change the following three methods:
	 * addWorkstationListener
	 * removeWorkstationListener
	 * publicPlateToListeners
	 */
	
	public void addWorkstationListener(WorkstationListener l) {
		listeners.add(l);
	}

	public void removeWorkstationListener(WorkstationListener l) {
		listeners.remove(l);
	}
	
	private void publishPlateToListeners(Plate p) {
		for (WorkstationListener l : listeners) {
			l.handleMadePlate(p);
		}
	}
}
