package lec17.ex2.v6;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Playlist extends java.util.Observable {

	private List<Song> songs;
	
	public Playlist() {
		songs = new ArrayList<Song>();
	}
	
	public int getSize() {
		return songs.size();
	}
	
	public Song[] getSongs() {
		return songs.toArray(new Song[songs.size()]);
	}

	public void addSong(Song s) {
		songs.add(s);
		setChanged();
		notifyObservers();
	}
	
	public void removeSong(Song s) {
		songs.remove(s);
		setChanged();
		notifyObservers();
	}

	public Song getSong(int index) {
		return songs.get(index);
	}

	public void shuffle() {
		Collections.shuffle(songs);
		setChanged();
		notifyObservers();
	}
}
