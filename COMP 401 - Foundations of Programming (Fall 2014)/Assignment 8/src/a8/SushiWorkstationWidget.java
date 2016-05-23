package a8;


import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;

import comp401.sushi.*;
import comp401.sushi.Plate.Color;

public class SushiWorkstationWidget extends JPanel implements ActionListener {

	/* Do not change the following line. */
	private List<WorkstationListener> listeners;
	
	private JPanel main_panel;
	private JPanel top_panel;
	private JPanel bottom_panel;
	private JPanel meat_panel;
	
	private JComboBox plate_color_dropdown;
	private JComboBox sashimi_dropdown;
	private JComboBox nigiri_dropdown;
	
	private JSlider price_slider;
	private JSlider avocado_slider;
	private JSlider crab_slider;
	private JSlider eel_slider;
	private JSlider rice_slider;
	private JSlider salmon_slider;
	private JSlider seaweed_slider;
	private JSlider shrimp_slider;
	private JSlider tuna_slider;
	
	private JButton roll_button;
	private JButton sashimi_button;
	private JButton nigiri_button;
	
	private Plate created_plate;
	private String color_chosen;
	private String stype_chosen;
	private String ntype_chosen;

	public SushiWorkstationWidget() {
		/* Do not change the following line*/
		listeners = new ArrayList<WorkstationListener>();
		
		/* Replace the following line with your own code. */
		main_panel = new JPanel(new GridLayout(2,1));
		top_panel = new JPanel(new GridLayout(10,2));
		bottom_panel = new JPanel(new GridLayout(2,1));
		meat_panel = new JPanel(new GridLayout(2,3));
		
		makeTopPanel();
		makeBottomPanel();
		main_panel.setLayout(new BorderLayout());
		
		main_panel.add(top_panel, BorderLayout.NORTH);
		main_panel.add(bottom_panel, BorderLayout.CENTER);
		
		setLayout(new BorderLayout());
		add(main_panel, BorderLayout.CENTER);
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
	
	protected void publishPlateToListeners(Plate p) {
		for (WorkstationListener l : listeners) {
			l.handleMadePlate(p);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == roll_button) {
			makeRoll();
		} else if (e.getSource() == sashimi_button) {
			makeSashimi();
		} else if (e.getSource() == nigiri_button) {
			makeNigiri();
		}
		
		if (created_plate != null) {
			publishPlateToListeners(created_plate);
		}
		created_plate = null;
	}
	
	public void makeTopPanel() {
		top_panel.add(new JLabel("Plate Color"));
		
		String[] plate_color_array = {"Blue", "Gold", "Green", "Red"};
		plate_color_dropdown = new JComboBox(plate_color_array);
		top_panel.add(plate_color_dropdown);
		
		top_panel.add(new JLabel("Gold Price"));
		
		price_slider = new JSlider(500,1500);
		Hashtable price_label_table = new Hashtable();
		price_label_table.put(new Integer(500), new JLabel("$5.00"));
		price_label_table.put(new Integer(1500), new JLabel("$15.00"));
		price_slider.setLabelTable(price_label_table);
		price_slider.setMinorTickSpacing(1);
		price_slider.setMajorTickSpacing(166);
		price_slider.setPaintLabels(true);
		price_slider.setPaintTicks(true);
		top_panel.add(price_slider);
		
		top_panel.add(new JLabel("Avocado"));
		avocado_slider = new JSlider(0,100);
		top_panel.add(avocado_slider);
		
		top_panel.add(new JLabel("Crab"));
		crab_slider = new JSlider(0,100);
		top_panel.add(crab_slider);
		
		top_panel.add(new JLabel("Eel"));
		eel_slider = new JSlider(0,100);
		top_panel.add(eel_slider);
		
		top_panel.add(new JLabel("Rice"));
		rice_slider = new JSlider(0,100);
		top_panel.add(rice_slider);
		
		top_panel.add(new JLabel("Salmon"));
		salmon_slider = new JSlider(0,100);
		top_panel.add(salmon_slider);
		
		top_panel.add(new JLabel("Seaweed"));
		seaweed_slider = new JSlider(0,100);
		top_panel.add(seaweed_slider);
		
		top_panel.add(new JLabel("Shrimp"));
		shrimp_slider = new JSlider(0,100);
		top_panel.add(shrimp_slider);
		
		top_panel.add(new JLabel("Tuna"));
		tuna_slider = new JSlider(0,100);
		top_panel.add(tuna_slider);
	}
	
	public void makeBottomPanel() {
		roll_button = new JButton("Make Roll");
		roll_button.setActionCommand("Make Roll");
		roll_button.addActionListener(this);
		bottom_panel.add(roll_button);
		
		meat_panel.add(new JLabel("Sashimi Type"));
		
		String[] type_array = {"Crab", "Eel", "Salmon", "Shrimp", "Tuna"};
		sashimi_dropdown = new JComboBox(type_array);
		meat_panel.add(sashimi_dropdown);
		
		sashimi_button = new JButton("Make Sashimi");
		sashimi_button.setActionCommand("Make Sashimi");
		sashimi_button.addActionListener(this);
		meat_panel.add(sashimi_button);
		
		meat_panel.add(new JLabel("Nigiri Type"));
		
		nigiri_dropdown = new JComboBox(type_array);
		meat_panel.add(nigiri_dropdown);
		
		nigiri_button = new JButton("Make Nigiri");
		nigiri_button.setActionCommand("Make Nigiri");
		nigiri_button.addActionListener(this);
		meat_panel.add(nigiri_button);
		
		bottom_panel.add(meat_panel);
	}
	
	public Color checkColor() {
		color_chosen = plate_color_dropdown.getSelectedItem().toString();
		Color c = null;
		
		if (color_chosen.equals("Blue")) {
			c = Plate.Color.BLUE;
		} else if (color_chosen.equals("Gold")) {
			c = Plate.Color.GOLD;
		} else if (color_chosen.equals("Green")) {
			c = Plate.Color.GREEN;
		} else if (color_chosen.equals("Red")) {
			c = Plate.Color.RED;
		}
		return c;
	}
	
	public void makeRoll() {
		ArrayList<Ingredient> ing_roll = new ArrayList<Ingredient>();
		
		if (avocado_slider.getValue() > 0) {
			Avocado avo_oz = new Avocado(avocado_slider.getValue() * .01);
			ing_roll.add(avo_oz);
		}
		
		if (crab_slider.getValue() > 0) {
			Crab crab_oz = new Crab(crab_slider.getValue() * .01);
			ing_roll.add(crab_oz);
		}
		
		if (eel_slider.getValue() > 0) {
			Eel eel_oz = new Eel(eel_slider.getValue() * .01);
			ing_roll.add(eel_oz);
		}
		
		if (rice_slider.getValue() > 0) {
			Rice rice_oz = new Rice(rice_slider.getValue() * .01);
			ing_roll.add(rice_oz);
		}
		
		if (salmon_slider.getValue() > 0) {
			Salmon sal_oz = new Salmon(salmon_slider.getValue() * .01);
			ing_roll.add(sal_oz);
		}
		
		if (seaweed_slider.getValue() > 0) {
			Seaweed sea_oz = new Seaweed(seaweed_slider.getValue() * .01);
			ing_roll.add(sea_oz);
		}
		
		if (shrimp_slider.getValue() > 0) {
			Shrimp shr_oz = new Shrimp(shrimp_slider.getValue() * .01);
			ing_roll.add(shr_oz);
		}
		
		if (tuna_slider.getValue() > 0) {
			Tuna tuna_oz = new Tuna(tuna_slider.getValue() * .01);
			ing_roll.add(tuna_oz);
		}
		
		Ingredient[] roll_array = new Ingredient[ing_roll.size()];
		roll_array = ing_roll.toArray(roll_array);
		Roll r = new Roll(roll_array);
		
		if (checkColor() == Plate.Color.BLUE) {
			if (r.getCost() < 4.0) {
				try {
					created_plate = new BluePlate(r);
				} catch (PlatePriceException e) {
					//Shouldn't Happen
					e.printStackTrace();
				}
			}
		} else if (checkColor() == Plate.Color.GOLD) {
			if (r.getCost() < (price_slider.getValue() * .01)) {
				try {
					created_plate = new GoldPlate(r, (price_slider.getValue() 
							* .01));
				} catch (PlatePriceException e) {
					//Shouldn't Happen
					e.printStackTrace();
				}
			}
		} else if (checkColor() == Plate.Color.GREEN) {
			if (r.getCost() < 2.0) {
				try {
					created_plate = new GreenPlate(r);
				} catch (PlatePriceException e) {
					//Shouldn't Happen
					e.printStackTrace();
				}
			}
		} else if (checkColor() == Plate.Color.RED) {
			if (r.getCost() < 1.0) {
				try {
					created_plate = new RedPlate(r);
				} catch (PlatePriceException e) {
					//Shouldn't Happen
					e.printStackTrace();
				}
			}
		}
	}
	
	public void makeSashimi() {
		checkColor();
		if (color_chosen.equals("Blue")) {
			try {
				created_plate = new BluePlate(new Sashimi(whatSMeat()));
			} catch (PlatePriceException e1) {
				//Shouldn't Happen
				e1.printStackTrace();
			}
		} else if (color_chosen.equals("Gold")) {
			try {
				created_plate = new GoldPlate(new Sashimi(whatSMeat()), 
						(price_slider.getValue() * .01));
			} catch (PlatePriceException e1) {
				//Shouldn't Happen
				e1.printStackTrace();
			}
		} else if (color_chosen.equals("Green")) {
			try {
				created_plate = new GreenPlate(new Sashimi(whatSMeat()));
			} catch (PlatePriceException e1) {
				//Shouldn't Happen
				e1.printStackTrace();
			}
		} else if (color_chosen.equals("Red")) {
			try {
				created_plate = new RedPlate(new Sashimi(whatSMeat()));
			} catch (PlatePriceException e1) {
				//Shouldn't Happen
				e1.printStackTrace();
			}
		}
	}
	
	public void makeNigiri() {
		checkColor();
		if (color_chosen.equals("Blue")) {
			try {
				created_plate = new BluePlate(new Nigiri(whatNMeat()));
			} catch (PlatePriceException e1) {
				//Shouldn't Happen
				e1.printStackTrace();
			}
		} else if (color_chosen.equals("Gold")) {
			try {
				created_plate = new GoldPlate(new Nigiri(whatNMeat()), 
						(price_slider.getValue() * .01));
			} catch (PlatePriceException e1) {
				//Shouldn't Happen
				e1.printStackTrace();
			}
		} else if (color_chosen.equals("Green")) {
			try {
				created_plate = new GreenPlate(new Nigiri(whatNMeat()));
			} catch (PlatePriceException e1) {
				//Shouldn't Happen
				e1.printStackTrace();
			}
		} else if (color_chosen.equals("Red")) {
			try {
				created_plate = new RedPlate(new Nigiri(whatNMeat()));
			} catch (PlatePriceException e1) {
				//Shouldn't Happen
				e1.printStackTrace();
			}
		}
	}
	
	public Sashimi.SashimiType whatSMeat() {
		stype_chosen = sashimi_dropdown.getSelectedItem().toString();
		Sashimi.SashimiType type = null;
		
		if (stype_chosen.equals("Tuna")) {
			type = Sashimi.SashimiType.TUNA;
		} else if (stype_chosen.equals("Salmon")) {
			type = Sashimi.SashimiType.SALMON;
		} else if (stype_chosen.equals("Eel")) {
			type = Sashimi.SashimiType.EEL;
		} else if (stype_chosen.equals("Crab")) {
			type = Sashimi.SashimiType.CRAB;
		} else if (stype_chosen.equals("Shrimp")) {
			type = Sashimi.SashimiType.SHRIMP;
		}
		
		return type;
	}
	
	public Nigiri.NigiriType whatNMeat() {
		ntype_chosen = nigiri_dropdown.getSelectedItem().toString();
		Nigiri.NigiriType type = null;
		
		if (ntype_chosen.equals("Tuna")) {
			type = Nigiri.NigiriType.TUNA;
		} else if (ntype_chosen.equals("Salmon")) {
			type = Nigiri.NigiriType.SALMON;
		} else if (ntype_chosen.equals("Eel")) {
			type = Nigiri.NigiriType.EEL;
		} else if (ntype_chosen.equals("Crab")) {
			type = Nigiri.NigiriType.CRAB;
		} else if (ntype_chosen.equals("Shrimp")) {
			type = Nigiri.NigiriType.SHRIMP;
		}
		
		return type;
	}
}
