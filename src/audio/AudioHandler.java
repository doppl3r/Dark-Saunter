package audio;
import java.io.*;
import javax.sound.sampled.*;

public enum AudioHandler {
    THUNK("sounds/thunk.wav"),
    THEME("sounds/music.wav");

   public static enum Volume {
      MUTE, LOW, MEDIUM, HIGH
   }
   public static Volume volume = Volume.LOW;
   public Clip clip;
   AudioHandler(String soundFileName) {
      try {
    	  // Set up an audio input stream piped from the sound file.
          AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(
         		 this.getClass().getClassLoader().getResource(soundFileName));
          //AudioFormat format = audioInputStream.getFormat();
          DataLine.Info info = new DataLine.Info(Clip.class, audioInputStream.getFormat());
          // Get a clip resource.
          //clip = AudioSystem.getClip();
          clip = (Clip) AudioSystem.getLine(info);
          // Open audio clip and load samples from the audio input stream.
          clip.open(audioInputStream); }
      catch (UnsupportedAudioFileException e) { e.printStackTrace(); }
      catch (IOException e) { e.printStackTrace(); }
      catch (LineUnavailableException e) { e.printStackTrace(); }
   }
   public void play() {
	  if (volume != Volume.MUTE) {
	      //clip.setFramePosition(0); // rewind to the beginning
		  stop();
          clip.start(); // Start playing
	  }
   }
   public void stop(){
	   clip.stop();
	   clip.setFramePosition(0);
   }
   static void init() { values(); }  // calls the constructor for all the elements
}