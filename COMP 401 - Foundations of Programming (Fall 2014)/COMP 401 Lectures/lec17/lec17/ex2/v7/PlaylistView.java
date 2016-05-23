package lec17.ex2.v7;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class PlaylistView extends JPanel implements Observer, ActionListener {

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
		
		JButton shuffle_button = new JButton("Shuffle");
		shuffle_button.addActionListener(this);
		JPanel button_panel = new JPanel();
		button_panel.add(shuffle_button);
		add(button_panel, BorderLayout.NORTH);
	}

	private void buildListPanel() {
		for (int i=0; i<plist.getSize(); i++) {
			SongListingWidget song_listing = new SongListingWidget(plist, i);
			list_panel.add(song_listing);
		}				
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
		list_panel.removeAll();
		buildListPanel();
		list_panel.revalidate();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		plist.shuffle();
	}
}
