package wave;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.NoSuchElementException;
import java.net.URL;

/////////////////////////////// Track class ///////////////////////////////
class Track {
  public Track(String title, String url) {
    this.title = title;
    this.url = url;
  }
  public String getTitle() { return title; }
  public String getURL() { return url; }
  private String title;
  private String url;
}

/////////////////////////////// Control class ///////////////////////////////
// Control is a class that handles playback control.
class Control implements Iterator<Track>{

  // Constructor. Simply clone the input track array.
  public Control(Track[] tracks) {
    this.tracks = tracks.clone();
    playing = false;
    current = -1;
    random = new Random(System.currentTimeMillis());
    mode = Mode.LINEAR;
  }
  
  // Mode enum
  public enum Mode {
    LINEAR, REPEAT, RANDOM
  }

  /////////////////////// Iterator interface methods /////////////////////
  public boolean hasNext() {
    if (tracks.length > 0) {
      switch (mode) {
        case LINEAR:
          return current + 1 < tracks.length;
        case REPEAT:
          if (current == -1) {
            ++current;
          }
          return true;
        case RANDOM:
          if (current == -1) {
            ++current;
          }
          return true;
        default:
          throw new RuntimeException("Invalid mode.");
      }
    } else {
      return false;
    }
  }

  public Track next() {
    if (hasNext()) {
      switch (mode) {
        case LINEAR:
          return tracks[++current];
        case REPEAT:
          return tracks[current];
        case RANDOM:
          // Randomly pick an index different from current.
          int next = random.nextInt(tracks.length-1);
          if (next >= current) {
            ++next;
          }
          current = next;
          return tracks[current];
        default:
          throw new RuntimeException("Invalid mode.");
      }
    } else {
      throw new NoSuchElementException("Error: cannot find next sound track.");
    }
  }

  // Get the element returned by the last next().
  public Track getCurrentTrack() {
    if (current != -1) {
      return tracks[current];
    } else {
      throw new RuntimeException("Current soundtrack is not available yet.");
    }
  }
  /////////////////// End of Iterator interface methods //////////////////

  // Print all track titles to a string
  public String allTrackTitlesToString() {
    StringBuilder s = new StringBuilder();
    for(int i = 0; i < tracks.length; ++i) {
      // Generate a formatted string (track # and track title) and append
      // it to the output.
      s.append(String.format("%3s. %s%n", i+1, tracks[i].getTitle()));
    }
    return s.toString();
  }

  // Stop the current playback
  public void setIsPlaying(boolean flag) {
    playing = flag;
  }

  // Getter
  public boolean isPlaying() {
    return playing;
  }

  // Mode setter
  public void setMode(Mode mode) {
    this.mode = mode;
  }

  public void resetPrompt() {
    System.out.format("%n>");
  }


  private Track[] tracks;
  private boolean playing;
  private int current;
  private Mode mode;
  private Random random;
}

/////////////////////////////// Player class ///////////////////////////////
class SoundStreamPlayer {
  public SoundStreamPlayer(Track[] tracks) {
    // Create a control
    control = new Control(tracks);
    // Create a command reader which reads user commands from standard input.
    cmdReader = new BufferedReader(new InputStreamReader(System.in));
  }
  
  public void powerOn() {
    // An infinite loop waiting for commands and parsing them.
    System.out.println("[Wave]: You have loaded the following soundtracks:");
    System.out.print(control.allTrackTitlesToString());
    for(;;) {
      if (control.isPlaying()) {
        System.out.format("Playing \"%s\">", control.getCurrentTrack().getTitle());
      } else {
        System.out.print(">");
      }
      try {
        // Wait for 100 millisecond if no command has been entered yet.
        while(!cmdReader.ready()){
          Thread.sleep(100);
        }

        // Read the command.
        String cmd = cmdReader.readLine().trim().toLowerCase();
        
        // Execute the command.
        if (cmd.equals("quit")) {
          // QUIT: Break out of the infinite loop and quit.
          quitHandler();
          break;

        } else if (cmd.equals("list")) {
          // LIST: List the sound tracks.
          System.out.print(control.allTrackTitlesToString());

        } else if (cmd.equals("play")) {
          // PLAY: Play the next track if not already playing.
          if (!control.isPlaying()) {
            if (control.hasNext()) {
              playHandler();
            } else {
              quitHandler();
              break; // End playback and break out of the infinite loop.
            }
          }
        
        } else if (cmd.equals("stop")) {
          // STOP: Stop playing the current track
          control.setIsPlaying(false);
        
        } else if (cmd.equals("linear")) {
          control.setMode(Control.Mode.LINEAR);
          System.out.println("[Wave]: Set playback mode to LINEAR.");

        } else if (cmd.equals("repeat")) {
          control.setMode(Control.Mode.REPEAT);
          System.out.println("[Wave]: Set playback mode to REPEAT.");

        } else if (cmd.equals("random")) {
          control.setMode(Control.Mode.RANDOM);
          System.out.println("[Wave]: Set playback mode to RANDOM.");

        } else if (cmd.equals("help")) {
          // HELP: Display available commands.
          helpHandler();

        } else {
          System.out.format("[Wave]: Unknow command \"%s\"%n", cmd);
        }

      } catch (Exception e) {
        System.out.println(e.getStackTrace());
      }        
    }
  }

  public static void main(String[] args) {
    ArrayList<Track> testTracks = new ArrayList<Track>();
    testTracks.add(new Track("A Memory Away", 
                             "http://solarapex.me/misc/tracks/AMemoryAway.wav"));
    testTracks.add(new Track("Deserve To Be Loved", 
                             "http://solarapex.me/misc/tracks/DeserveToBeLoved.wav"));
    testTracks.add(new Track("Bloody Tears",
                             "http://solarapex.me/misc/tracks/BloodyTears.wav"));

    SoundStreamPlayer player = new SoundStreamPlayer(testTracks.toArray(new Track[0]));
    player.powerOn();
  }

  /////////////////////////  Command handlers /////////////////////////
  private void quitHandler() { 
    control.setIsPlaying(false);
    System.out.println("[Wave]: Shutting down. Bye.");
  }

  private void playHandler() throws Exception {
    URL url = new URL(control.next().getURL());
    control.setIsPlaying(true);
    (new Thread(new SampledAudioStreamPlayer(url, control))).start();
  }

  private void helpHandler() {
    System.out.println("Available Commands:");
    System.out.format("%10s: Play the next track according to playback mode.%n", "play");
    System.out.format("%10s: Stop the current playing track.%n", "stop");
    System.out.format("%10s: List all available tracks.%n", "list");
    System.out.format("%10s: Set playback mode to LINEAR.%n", "linear");
    System.out.format("%10s: Set playback mode to REPEAT.%n", "repeat");
    System.out.format("%10s: Set playback mode to RANDOM.%n", "random");
    System.out.format("%10s: Quit Wave.%n", "quit");
    System.out.format("%10s: Print this help message.%n", "help");
  }
  
  private Control control;
  // Some more private fields
  private BufferedReader cmdReader;
}
