package lec11.ex9.v1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class Playlist {

	private Song[] playlist_songs;

	private Playlist(Song[] songs) {
		playlist_songs = songs;
	}

	public static Playlist readFromFile(String filename)
			throws PlaylistFormatException {
		ArrayList<Song> songs = new ArrayList<Song>();

		Scanner scanner = null;

		try {
			scanner = new Scanner(new File(filename));
		} catch (FileNotFoundException e) {
			System.out.println("File " + filename + " doesn't exist");
			System.exit(-1);
		}
		
		try {
			while (scanner.hasNext()) {
				String song_name = scanner.nextLine();
				String artist_name = scanner.nextLine();
				double length = scanner.nextDouble();
				int rating = scanner.nextInt();
				
				// Need this next call to skip rest of line
				scanner.nextLine();

				Song next_song = new Song(song_name, artist_name, length,
						rating);
				songs.add(next_song);
				
			}
		} catch (NoSuchElementException e) {
			throw new PlaylistFormatException("Error in playlist after "
					+ songs.size() + " songs");
		} finally {
			scanner.close();
		}

		return new Playlist(songs.toArray(new Song[0]));
	}

	public void print() {
		for (Song s : playlist_songs) {
			System.out.println(s.toString());
		}
	}
}
