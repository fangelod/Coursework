package lec15.v3;

import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;

public class Main {

	public static void main(String[] args) throws ParseException {
		Song s1 = new SongImpl("Caring Is Creepy");
		Song s2 = new SongImpl("Silent Shout");
		Song s3 = new SongImpl("One Whole Year");
		
		SongLog log = new SongLog();
		
		SimpleDateFormat df = new SimpleDateFormat("M/d/y k:mm");
		
		log.recordInLog(s1, df.parse("2/2/2012 10:34"));
		log.recordInLog(s2, df.parse("2/2/2012 12:14"));
		log.recordInLog(s3, df.parse("2/4/2012 9:14"));
		log.recordInLog(s2, df.parse("2/20/2012 6:14"));
		log.recordInLog(s1, df.parse("2/22/2012 22:34"));
		log.recordInLog(s1, df.parse("2/25/2012 11:12"));
		
		System.out.println(s1.getName() + " last played at: " + df.format(log.lastPlayed(s1)));
		System.out.println(s2.getName() + " last played at: " + df.format(log.lastPlayed(s2)));
		System.out.println(s3.getName() + " last played at: " + df.format(log.lastPlayed(s3)));
		System.out.println();
		
		Date d1 = df.parse("2/1/2012 1:00");
		Song s1_from_log = log.getFirstSongPlayedAfter(d1);
		System.out.println("First song played after " + df.format(d1) + " was " + 
		                   s1_from_log.getName());
		
		System.out.println("Original s1: " + s1.toString());
		System.out.println("s1_from_log: " + s1_from_log.toString());

		System.out.println();

		Date d2 = df.parse("2/5/2012 1:00");
		Song s2_from_log = log.getFirstSongPlayedAfter(d2);
		System.out.println("First song played after " + df.format(d2) + " was " + 
		                   s2_from_log.getName());

		System.out.println("Original s2: " + s2.toString());
		System.out.println("s2_from_log: " + s2_from_log.toString());
		
	}
}
