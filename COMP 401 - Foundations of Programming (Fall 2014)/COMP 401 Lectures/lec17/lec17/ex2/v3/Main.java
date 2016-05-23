package lec17.ex2.v3;

import javax.swing.JFrame;

public class Main {
	public static void main(String[] args) {
		JFrame main_frame = new JFrame();
		main_frame.setTitle("Playlist View Example");
		main_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Playlist plist = new Playlist();
		plist.addSong(new Song("Saint Simon", "The Shins", 4));
		plist.addSong(new Song("No Sunlight", "Death Cab For Cutie", 3));
		plist.addSong(new Song("Wake Up", "Arcade Fire", 4));
		plist.addSong(new Song("Brand New Love", "Superchunk", 5));
		plist.addSong(new Song("32 Flavors", "Ani Di Franco", 2));

		PlaylistView plist_view = new PlaylistView(plist);
		main_frame.setContentPane(plist_view);

		main_frame.pack();
		main_frame.setVisible(true);
	}

}
