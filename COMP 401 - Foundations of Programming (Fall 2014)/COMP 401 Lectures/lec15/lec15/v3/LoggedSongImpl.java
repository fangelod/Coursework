package lec15.v3;

import java.util.Date;

public class LoggedSongImpl implements LoggedSong {

	private Song song;
	private Date date;
	
	public LoggedSongImpl(Song s, Date d) {
		song = s;
		date = d;
	}

	public Date getDate() {
		return date;
	}

	public boolean equals(Song s) {
		return song.equals(s);
	}
	
	public String getName() {
		return song.getName();
	}
	
	public Song getWrappedSong() {
		return song;
	}
}
