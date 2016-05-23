/*
 * Copyright (c) 2014 Xufeng Han. All rights Reserved.  
 *
 * This code is a modified version of David Flanagan's original PlaySoundStream
 * code. It is provided AS-IS, WITHOUT ANY WARRANTY either expressed or implied.
 * You may study, use, and modify it for any non-commercial purpose, including
 * teaching and use in open-source projects.
 * You may distribute it non-commercially as long as you retain this notice.
 * The original code can be downloaded from:
 * http://www.java2s.com/Code/Java/Development-Class/PlayssoundsstreamingfromaURL.htm
 */

/*
 * Copyright (c) 2004 David Flanagan.  All rights reserved.
 * This code is from the book Java Examples in a Nutshell, 3nd Edition.
 * It is provided AS-IS, WITHOUT ANY WARRANTY either expressed or implied.
 * You may study, use, and modify it for any non-commercial purpose,
 * including teaching and use in open-source projects.
 * You may distribute it non-commercially as long as you retain this notice.
 * For a commercial use license, or to purchase the book, 
 * please visit http://www.davidflanagan.com/javaexamples3.
 */
package wave;
import java.net.URL;
import java.io.*;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SampledAudioStreamPlayer implements Runnable {
  
  public SampledAudioStreamPlayer(URL url, Control ctrl) {
    this.url = url;
    this.ctrl = ctrl;
    interrupted = false;
  }

  public void run() {
    try {
      streamSampledAudio();
      if (!interrupted) {
        ctrl.resetPrompt();
        ctrl.setIsPlaying(false);
      }
    } catch (Exception e) {
      System.out.println(e.getStackTrace());
    }
  }

  private void streamSampledAudio() throws 
    IOException, UnsupportedAudioFileException, LineUnavailableException {
    AudioInputStream ain = null; // We read audio data from here
    SourceDataLine line = null; // And write it here.

    try {
      // Get an audio input stream from the URL
      InputStream inStream = url.openStream();
      BufferedInputStream bufferedStream = new BufferedInputStream(inStream);
      ain = AudioSystem.getAudioInputStream(bufferedStream);

      // Get information about the format of the stream
      AudioFormat format = ain.getFormat();
      DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);

      // If the format is not supported directly (i.e. if it is not PCM
      // encoded, then try to transcode it to PCM.
      if (!AudioSystem.isLineSupported(info)) {
        // This is the PCM format we want to transcode to.
        // The parameters here are audio format details that you
        // shouldn't need to understand for casual use.
        AudioFormat pcm = new AudioFormat(
            format.getSampleRate(), 16, format.getChannels(), true, false);

        // Get a wrapper stream around the input stream that does the
        // transcoding for us.
        ain = AudioSystem.getAudioInputStream(pcm, ain);

        // Update the format and info variables for the transcoded data
        format = ain.getFormat();
        info = new DataLine.Info(SourceDataLine.class, format);
      }

      // Open the line through which we'll play the streaming audio.
      line = (SourceDataLine) AudioSystem.getLine(info);
      line.open(format);

      // Allocate a buffer for reading from the input stream and writing
      // to the line. Make it large enough to hold 4k audio frames.
      // Note that the SourceDataLine also has its own internal buffer.
      int framesize = format.getFrameSize();
      byte[] buffer = new byte[4 * 1024 * framesize]; // the buffer
      int numbytes = 0; // how many bytes

      // We haven't started the line yet.
      boolean started = false;

      for (;;) { // We'll exit the loop when we reach the end of stream
        // First, read some bytes from the input stream.
        int bytesread = ain.read(buffer, numbytes, buffer.length - numbytes);
        // If there were no more bytes to read, we're done.
        if (bytesread == -1)
          break;
        if (!ctrl.isPlaying()) {
          interrupted = true;
          break;
        }
        numbytes += bytesread;

        // Now that we've got some audio data, to write to the line,
        // start the line, so it will play that data as we write it.
        if (!started) {
          line.start();
          started = true;
        }

        // We must write bytes to the line in an integer multiple of
        // the framesize. So figure out how many bytes we'll write.
        int bytestowrite = (numbytes / framesize) * framesize;

        // Now write the bytes. The line will buffer them and play
        // them. This call will block until all bytes are written.
        line.write(buffer, 0, bytestowrite);

        // If we didn't have an integer multiple of the frame size,
        // then copy the remaining bytes to the start of the buffer.
        int remaining = numbytes - bytestowrite;
        if (remaining > 0)
          System.arraycopy(buffer, bytestowrite, buffer, 0, remaining);
        numbytes = remaining;
      }

      // Now block until all buffered sound finishes playing.
      line.drain();
    } finally { // Always relinquish the resources we use
      if (line != null)
        { line.close(); }
      if (ain != null)
        { ain.close(); }
    }
  }

  private URL url;
  private Control ctrl;
  private boolean interrupted;
}
