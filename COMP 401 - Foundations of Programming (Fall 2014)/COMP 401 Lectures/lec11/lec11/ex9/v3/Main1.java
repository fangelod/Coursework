package lec11.ex9.v3;

import java.io.FileNotFoundException;

public class Main1 {

	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Must specify playlist file as argument to program.");
			System.exit(-1);
		}
		
		String playlist_file = args[0];
		
		try {
			Playlist p = Playlist.readFromFile(playlist_file);
			
			p.print();
		} catch (PlaylistFormatException e) {
			System.out.println(e.getMessage());
			System.exit(-1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
