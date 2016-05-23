package lec15.v1;

public class SongImpl implements Song {

	private String name;
	
	public SongImpl(String song_name) {
		name = song_name;
	}
	
	public String getName() {
		return name;
	}
	
	public boolean equals(Song other_song) {
		return name.equals(other_song.getName());
	}
}
