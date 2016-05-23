package lec11.ex9.v2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main2 {

	public static void main(String [] args) {
		
		Scanner console_scanner = new Scanner(System.in);
		
		while (true) {
			System.out.println("Enter playlist file to read: ");
			try {
				String input_filename = console_scanner.next();
				if (input_filename.equals("exit")) {
					break;
				}
				Playlist p = Playlist.readFromFile(input_filename);
				p.print();
				System.out.println("");
				
			} catch (FileNotFoundException e) {
				System.out.println("No such playlist, try again.");
			} catch (PlaylistFormatException e) {
				System.out.println("Playlist malformed, try again.");
			}
		}
		System.out.println("Done!");
	}
}
