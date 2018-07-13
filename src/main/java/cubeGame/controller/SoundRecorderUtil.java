package main.java.cubeGame.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
 
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;
 
/**
 * A utility class provides general functions for recording sound.
 * @author www.codejava.net
 *
 */
public class SoundRecorderUtil {
    private static final int BUFFER_SIZE = 4096;
	private static final boolean DEBUG = false;
    private ByteArrayOutputStream recordBytes;
    private TargetDataLine audioLine;
    private AudioFormat format;
 
    private volatile boolean isRunning;
 
    /**
     * Defines a default audio format used to record
     */
    AudioFormat getAudioFormat() {
        float sampleRate = 16000;
        int sampleSizeInBits = 8;
        int channels = 2;
        boolean signed = true;
        boolean bigEndian = true;
        return new AudioFormat(sampleRate, sampleSizeInBits, channels, signed,
                bigEndian);
    }
 
    /**
     * Start recording sound.
     * @throws LineUnavailableException if the system does not support the specified
     * audio format nor open the audio data line.
     */
    public int start() throws LineUnavailableException {
    	
    	if (DEBUG) System.out.println("in SoundRecorderUtil Start");
        format = getAudioFormat();
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
 
        // checks if system supports the data line
        if (!AudioSystem.isLineSupported(info)) {
            throw new LineUnavailableException(
                    "The system does not support the specified format.");
        }
 
        audioLine = AudioSystem.getTargetDataLine(format);
 
        audioLine.open(format);
        audioLine.start();
 
        byte[] buffer = new byte[BUFFER_SIZE];
        int bytesRead = 0;
 
        recordBytes = new ByteArrayOutputStream();
        isRunning = true;
 
        //int count = 0;
        while (isRunning) {
        	//if (DEBUG && ((++count % 10) == 0) ) System.out.println("in SoundRecorderUtil Start");
            bytesRead = audioLine.read(buffer, 0, buffer.length);
            recordBytes.write(buffer, 0, bytesRead);
        }
        return 0;
    }
 
    /**
     * Stop recording sound.
     * @throws IOException if any I/O error occurs.
     */
    public void stop() throws IOException {
        isRunning = false;
         
        if (audioLine != null) {
            audioLine.flush();
            audioLine.close();
        }
    }
 
    /**
     * Save recorded sound data into a .wav file format.
     * @param wavFile The file to be saved.
     * @throws IOException if any I/O error occurs.
     */
    public void save(File wavFile) throws IOException {
    	if (DEBUG) System.out.println("save()ing audio file");
        byte[] audioData = recordBytes.toByteArray();
    	if (DEBUG) System.out.println("audio file length: " + audioData.length);
        ByteArrayInputStream bais = new ByteArrayInputStream(audioData);
        AudioInputStream audioInputStream = new AudioInputStream(bais, format,
                audioData.length / format.getFrameSize());
 
        AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, wavFile);
 
        audioInputStream.close();
        recordBytes.close();
    }
}