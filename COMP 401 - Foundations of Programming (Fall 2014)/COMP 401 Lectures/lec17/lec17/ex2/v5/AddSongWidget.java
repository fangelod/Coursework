package lec17.ex2.v5;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;

public class AddSongWidget extends JPanel implements ActionListener {
	private Playlist plist;
	private JTextField song_field;
	private JTextField artist_field;
	private JSlider rating_slider;
	private JButton add_song_button;
	
	public AddSongWidget(Playlist plist) {
		this.plist = plist;
		
		song_field = new JTextField(10);
		add(new JLabel("Song: "));		
		add(song_field);
		
		artist_field = new JTextField(10);
		add(new JLabel("Artist: "));
		add(artist_field);
		
		rating_slider = new JSlider(0, 5, 0);
		rating_slider.setPaintTicks(true);
		rating_slider.setSnapToTicks(true);
		rating_slider.setPaintLabels(true);
		rating_slider.setMajorTickSpacing(1);
		add(new JLabel("Rating: "));
		add(rating_slider);
		
		add_song_button = new JButton("+");
		add_song_button.setActionCommand("add");
		add(add_song_button);

		add_song_button.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String song_name = song_field.getText();
		String artist_name = artist_field.getText();
		int rating = rating_slider.getValue();
		
		Song song_to_add = new Song(song_name, artist_name, rating);
		plist.addSong(song_to_add);
		
		song_field.setText("");
		artist_field.setText("");
		rating_slider.setValue(0);
	}
}