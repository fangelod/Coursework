package lec17.ex2.v5;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class PlaylistView extends JPanel implements Observer {

	private Playlist plist;
	private JPanel list_panel;
	
	public PlaylistView(Playlist plist) {
		this.plist = plist;
		plist.addObserver(this);
		
		setLayout(new BorderLayout());

		list_panel = new JPanel();
		list_panel.setLayout(new GridLayout(0,1));
		buildListPanel();
		add(new JScrollPane(list_panel), BorderLayout.CENTER);
		
		AddSongWidget add_song_widget = new AddSongWidget(plist);
		add(add_song_widget, BorderLayout.SOUTH);
	}

	private void buildListPanel() {
		for (Song s : plist.getSongs()) {
			JLabel song_label = new JLabel(s.toString());
			list_panel.add(song_label);
		}				
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
		list_panel.removeAll();
		buildListPanel();
		list_panel.revalidate();
	}
}
