package lec17.ex1.v3;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ColorChooser extends JPanel implements ChangeListener {

	Color color;
	JSlider red_slider;
	JSlider green_slider;
	JSlider blue_slider;
	JLabel color_label;
	List<ChangeListener> change_listeners;
	
	public ColorChooser(Color init_color) {
		color = init_color;
		
		setLayout(new BorderLayout());
		
		JPanel slider_panel = new JPanel();
		slider_panel.setLayout(new GridLayout(3,1));
		
		red_slider = new JSlider(0, 255, init_color.getRed());
		green_slider = new JSlider(0, 255, init_color.getGreen());
		blue_slider = new JSlider(0, 255, init_color.getBlue());

		red_slider.setPreferredSize(new Dimension(200, 20));
		green_slider.setPreferredSize(new Dimension(200, 20));
		blue_slider.setPreferredSize(new Dimension(200, 20));
		
		slider_panel.add(red_slider);
		slider_panel.add(green_slider);
		slider_panel.add(blue_slider);
		
		add(slider_panel, BorderLayout.SOUTH);
		
		color_label = new JLabel(init_color.toString());
		color_label.setBackground(this.color);
		color_label.setOpaque(true);
		color_label.setPreferredSize(new Dimension(200, 40));
		color_label.setHorizontalAlignment(SwingConstants.CENTER);

		add(color_label, BorderLayout.CENTER);
		
		red_slider.addChangeListener(this);
		green_slider.addChangeListener(this);
		blue_slider.addChangeListener(this);
		
		change_listeners = new ArrayList<ChangeListener>();
	}
	
	public ColorChooser() {
		this(Color.GREEN);
	}
	
	public Color getColor() {
		return color;
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		color = new Color(red_slider.getValue(),
				          green_slider.getValue(),
				          blue_slider.getValue());
		color_label.setText(color.toString());
		color_label.setBackground(color);

		if (!((JSlider) e.getSource()).getValueIsAdjusting()) {
			ChangeEvent ev = new ChangeEvent(this);
			for (ChangeListener l : change_listeners) {
				l.stateChanged(ev);
			}
		}
	}
	
	public void addChangeListener(ChangeListener l) {
		change_listeners.add(l);
	}
	
	public void removeChangeListener(ChangeListener l) {
		change_listeners.remove(l);
	}
}
