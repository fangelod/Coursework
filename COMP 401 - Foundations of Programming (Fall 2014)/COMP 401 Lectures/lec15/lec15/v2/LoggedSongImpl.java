package lec15.v2;

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
}
