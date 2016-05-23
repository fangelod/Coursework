package lec15.v1;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;

public class SongLog {

	private List<Song> song_list;
	private List<Date> date_list;
	
	public SongLog() {
		song_list = new ArrayList<Song>();
		date_list = new ArrayList<Date>();
	}
	
	public void recordInLog(Song s, Date d) {
		// This assumes that Date d is always "later" than date associated with
		// last song recorded. A real implementation would check this.
		
		song_list.add(s);
		date_list.add(d);
	}
	
	public void recordInLog(Song s) {
		recordInLog(s, new Date());
	}
	
	public Date lastPlayed(Song s) {
		Date last_played = null;
		
		for (int i=song_list.size()-1; i>=0; i--) {
			if (song_list.get(i).equals(s)) {
				last_played = date_list.get(i);
				break;
			}
		}
		return last_played;
	}
}
