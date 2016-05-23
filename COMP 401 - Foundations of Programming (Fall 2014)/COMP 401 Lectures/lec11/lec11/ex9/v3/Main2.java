package lec11.ex9.v3;

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
				String input_filename = console_scanner.nextLine().trim();
				if (input_filename.equals("exit")) {
					break;
				}
				
				if (input_filename.equals("null")) {
					input_filename = null;
				}

				Playlist p = Playlist.readFromFile(input_filename);
				p.print();
				System.out.println("");

			} catch (FileNotFoundException e) {
				System.out.println("No such playlist, try again.");
			} catch (PlaylistFormatException e) {
				System.out.println("Playlist malformed, try again.");
			} catch (IllegalArgumentException e) {
				System.out.println("Filename can not be empty, try again.");
			}
		}
		System.out.println("Done!");
	}
}
