package main.java.cubeGame.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.swing.SwingWorker;
import javax.swing.Timer;

import main.java.cubeGame.model.CubeWorld;
import main.java.cubeGame.view.CubeGameScreen;
import main.java.menu.controller.MGController;

/**
 * 
 * @author Cathrine and Collin
 *Controls the Cube game. Detects screen size and creates a crabWorld and an animation.
 */
public class CubeController extends MGController{
	private static final int RECORD_TIME = 10000; // in milliseconds
	public static final Integer RECORDING_FILE_ERROR = new Integer(-1);
	private static final boolean DEBUG = false;
	final int timerTick = 17;
	static int curRecordingNum = 0;
	private File wavFile;
	CubeWorld world;
	CubeGameScreen view;
	public final SoundRecorderUtil recorder = new SoundRecorderUtil();
	private SwingWorker<Integer, Integer> recordingThread;
	CubeListener listener;
	Timer timer; //used outside this class
	Timer recordingTimer; //stops recording if it goes too long
	
	public boolean recording = false;
	
	public CubeController(){
		world = new CubeWorld();
		view = new CubeGameScreen(this);
		listener = new CubeListener(this);
		timer = new Timer (timerTick, listener);
		view.addMouseListener(listener);
		view.addMouseMotionListener(listener);
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void dispose() {
		world.reset();
		hideRecorder();
		view.setVisible(false);
		timer.stop();
		view.reset();
	}

	public CubeWorld getWorld() {
		return world;
	}
	
	@Override
	public CubeGameScreen getView() {
		return view;
	}
	
	public Timer getTimer() {
		return timer;
	}
	
	public void attemptRecording() {
		view.showRecordingButtons();
	}
	
	public void record2(){

		if (DEBUG) System.out.println("calling record2(): " + "Recording" + curRecordingNum + ".wav");
		
	    class RecordingWorker extends SwingWorker<Integer, Integer>
		{
            volatile boolean CONTINUE = true;
            
			@Override
			protected Integer doInBackground() throws Exception
			{
                try {
                    
                    ActionListener taskPerformer = new ActionListener() {
                        @Override
			public void actionPerformed(ActionEvent evt) {
                    		if (DEBUG) System.out.println("record2 timer stopping recording");
                        	CONTINUE = false; //don't want to call cancel?
                    		stopRecorder(); //will call cancel on worker thread
                        }
                    };
                    recordingTimer = new Timer(RECORD_TIME, taskPerformer);
                    //recordingTimer.setRepeats(false);
                    recordingTimer.start();
                    
                    System.out.println("Start recording...");
                    @SuppressWarnings("unused")
					int temp = recorder.start();

                	if (DEBUG ) System.out.println("doInBackground() ending");
                    //while (!isCancelled() && CONTINUE){
                    	//seems like a bad idea
                    //}
                } catch (LineUnavailableException ex) {
                    ex.printStackTrace();
                    return RECORDING_FILE_ERROR;
                }
				return new Integer(0);              
			}

			@Override
			protected void done()
			{
				System.out.println("DONE recording");
            	if (DEBUG ) System.out.println("doInBackground() about to stop recording");
            	if (DEBUG ) System.out.println("doInBackground() CONTINUE: " + CONTINUE);
            	if (DEBUG ) System.out.println("doInBackground() isCancelled(): " + isCancelled());
                stopRecorder(); //stops recordingTimer too
			}
		}

		curRecordingNum ++;
		wavFile =  new File("Recording" + curRecordingNum + ".wav");
		if (DEBUG) System.out.println("record2() new wav file: " + "Recording" + curRecordingNum + ".wav");
		
        recordingThread = new RecordingWorker();
        recordingThread.execute();
         
        if (DEBUG) System.out.println("end of record2()");
	    
	}
	
//	public void record() {		
//		curRecordingNum ++;
//		wavFile =  new File("Recording" + curRecordingNum + ".wav");
//         
//		if (DEBUG) System.out.println("calling record(): " + "Recording" + curRecordingNum + ".wav");
//		
//        // create a separate thread for recording
//        Thread recordThread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    System.out.println("Start recording...");
//                    recorder.start();
//                } catch (LineUnavailableException ex) {
//                    ex.printStackTrace();
//                    System.exit(-1);
//                }              
//            }
//        });
//         
//        recordThread.start();
//         
//        try {
//            Thread.sleep(RECORD_TIME);
//        } catch (InterruptedException ex) {
//            ex.printStackTrace();
//        }
//         
//        stopRecorder();
//         
//        System.out.println("DONE");
//	}
	
	public void stopRecorder(){
    	if (DEBUG ) System.out.println("stopRecorder()");
		try {
            recordingTimer.stop();
            recorder.stop();
            recorder.save(wavFile);
            boolean result = recordingThread.cancel(true);
        	if (DEBUG ) System.out.println("stopRecorder() able to cancel recordingTimer: " + result);
            view.showingEnd = true;
    		view.hideButtons();
            System.out.println("STOPPED");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
	}

	public void hideRecorder() {
		view.hideRecordingButtons();
	}
}
