package lec15.v3;

import java.util.Date;

public interface LoggedSong extends Song {
	Date getDate();
	Song getWrappedSong();
}
