package lec17.ex2.v3;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class PlaylistView extends JPanel {

	private Playlist plist;
	private JPanel list_panel;
	
	public PlaylistView(Playlist plist) {
		this.plist = plist;
		
		setLayout(new BorderLayout());

		list_panel = new JPanel();
		list_panel.setLayout(new GridLayout(0,1));
		for (Song s : plist.getSongs()) {
			JLabel song_label = new JLabel(s.toString());
			list_panel.add(song_label);
		}		
		add(list_panel, BorderLayout.CENTER);
		
		AddSongWidget add_song_widget = new AddSongWidget(plist);
		add(add_song_widget, BorderLayout.SOUTH);
	}
}
