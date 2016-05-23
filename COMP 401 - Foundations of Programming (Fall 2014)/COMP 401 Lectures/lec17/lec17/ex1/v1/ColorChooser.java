package lec17.ex1.v1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;

public class ColorChooser extends JPanel {

	private Color color;
	private JSlider red_slider;
	private JSlider green_slider;
	private JSlider blue_slider;
	private JLabel color_label;
	
	public ColorChooser(Color init_color) {
		color = init_color;
		
		setLayout(new BorderLayout());
		
		JPanel slider_panel = new JPanel();
		slider_panel.setLayout(new GridLayout(3,1));
		
		red_slider = new JSlider(0, 255, init_color.getRed());
		green_slider = new JSlider(0, 255, init_color.getGreen());
		blue_slider = new JSlider(0, 255, init_color.getBlue());

		Dimension slider_dim = new Dimension(200,20);
		red_slider.setPreferredSize(slider_dim);
		green_slider.setPreferredSize(slider_dim);
		blue_slider.setPreferredSize(slider_dim);
		
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
	}
	
	public ColorChooser() {
		this(Color.GREEN);
	}
	
	public Color getColor() {
		return color;
	}
}
