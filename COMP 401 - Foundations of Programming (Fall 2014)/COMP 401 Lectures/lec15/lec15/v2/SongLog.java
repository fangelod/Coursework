package lec15.v2;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;

public class SongLog {

	private List<LoggedSong> logged_song_list;
	
	public SongLog() {
		logged_song_list = new ArrayList<LoggedSong>();
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
			LoggedSong  next_song = logged_song_list.get(i);
			if (next_song.equals(s)) {
				return next_song.getDate();
			}
		}
		return null;
	}
}
