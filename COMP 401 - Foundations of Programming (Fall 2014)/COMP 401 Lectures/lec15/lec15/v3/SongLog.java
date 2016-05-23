package lec15.v3;

import java.util.ArrayList;
import java.util.Date;

public class SongLog {

	private ArrayList<LoggedSongImpl> logged_song_list;
	
	public SongLog() {
		logged_song_list = new ArrayList<LoggedSongImpl>();
	}
	
	public void recordInLog(Song s, Date d) {
		// This assumes that Date d is always "later" than date associated with
		// last song recorded. A real implementation would check this.
		
		logged_song_list.add(new LoggedSongImpl(s,d));
	}
	
	public void recordInLog(Song s) {
		recordInLog(s, new Date());
	}
	
	public Date lastPlayed(Song s) {
		for (int i=logged_song_list.size()-1; i>=0; i--) {
			LoggedSongImpl  next_song = logged_song_list.get(i);
			if (next_song.equals(s)) {
				return next_song.getDate();
			}
		}
		return null;
	}
	
	public Song getFirstSongPlayedAfter(Date d) {

//		for (LoggedSong s : logged_song_list) {
//			if (s.getDate().compareTo(d) > 0) {
//				return s;
//			}
//		}

		for (LoggedSong s : logged_song_list) {
			if (s.getDate().compareTo(d) > 0) {
				return s.getWrappedSong();
			}
		}
		return null;
	}
}
