package main.java.crabGame.view;

import main.java.crabGame.model.Question;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import main.java.crabGame.model.CrabGameWorld;
import main.java.menu.view.MenuScreen;

/**
 * Created by Alina on 2017-07-18.
 */
public class ContinueScreen {
	static JButton answer1;
	static JButton answer2;
	static JButton answer3;
	static Timer gracePeriodTimer;
	static boolean isItGracePeriod;
	static boolean answer1Wrong;
	static boolean answer2Wrong;
	static boolean answer3Wrong;
	static int gracePeriodCounter;
	static Timer questionBufferTimer;
	static boolean isItQuestionBuffer;

	static Question currentQuestion;

	//TODO: look into layered panes for question overlay

	public static void drawContinueScreen(Graphics g) {

		final String continueMessage = "Watch your health bar! Try again!";
		int continueMessageFontSize = 52;
		g.setFont(g.getFont().deriveFont(g.getFont().getStyle(), continueMessageFontSize));
		int messageLength = g.getFontMetrics().stringWidth(continueMessage);
		g.drawString(continueMessage, (MenuScreen.frameWidth - messageLength) / 2, MenuScreen.frameHeight / 2);
	}

	static void drawWrongImage(Graphics g, int answerYPos, String s) {
		g.setColor(Color.GRAY);
		//g.drawImage(ImageManager.wrongImage[0], 50 + g.getFontMetrics().stringWidth(s), answerYPos - 40, this);
	}

	public int getGracePeriodCounter() {
		return gracePeriodCounter;
	}

	/**
	 * Called when first answer is selected, returns to gamePanel if associated answer is correct
	 */
	public static void answerButton1Press() {
		if (currentQuestion.getAnswers().get(0) == currentQuestion.getCorrectAnswer()) {
			gracePeriodCounter = 0;
			gracePeriodTimer.restart();
			isItGracePeriod = true;

			setAnswer1Wrong(false);
			setAnswer2Wrong(false);
			setAnswer3Wrong(false);

			answer1.setEnabled(false);
			answer1.setVisible(false);

			answer2.setEnabled(false);
			answer2.setVisible(false);

			answer3.setEnabled(false);
			answer3.setVisible(false);
		} else {
			setAnswer1Wrong(true);
		}
	}

	/**
	 * Called when second answer is selected, returns to gamePanel if associated answer is correct
	 */
	public static void answerButton2Press() {
		if (currentQuestion.getAnswers().get(1) == currentQuestion.getCorrectAnswer()) {
			gracePeriodCounter = 0;
			gracePeriodTimer.restart();
			isItGracePeriod = true;

			setAnswer1Wrong(false);
			setAnswer2Wrong(false);
			setAnswer3Wrong(false);

			answer1.setEnabled(false);
			answer1.setVisible(false);

			answer2.setEnabled(false);
			answer2.setVisible(false);

			answer3.setEnabled(false);
			answer3.setVisible(false);
		} else {
			setAnswer2Wrong(true);
		}
	}

	/**
	 * Called when third answer is selected, returns to gamePanel if associated answer is correct
	 */
	public static void answerButton3Press() {
		if (currentQuestion.getAnswers().get(2) == currentQuestion.getCorrectAnswer()) {
			gracePeriodCounter = 0;
			gracePeriodTimer.restart();
			isItGracePeriod = true;

			setAnswer1Wrong(false);
			setAnswer2Wrong(false);
			setAnswer3Wrong(false);

			answer1.setEnabled(false);
			answer1.setVisible(false);

			answer2.setEnabled(false);
			answer2.setVisible(false);

			answer3.setEnabled(false);
			answer3.setVisible(false);
		} else {
			setAnswer3Wrong(true);
		}
	}

	/**
	 * closes and re-opens gamePanel
	 *//* maybe helpful but part of the gamePanel opening in a new frame
	public static void replayGame(JFrame frame){
		CrabController.pauseTimers();
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));

		timer.start();

		@SuppressWarnings("unused")
		CrabController c1 = new CrabController();
	}*/
	public static boolean getAnswer1Wrong() {
		return answer1Wrong;
	}

	public static void setAnswer1Wrong(boolean a1W) {
		answer1Wrong = a1W;
	}

	public static boolean getAnswer2Wrong() {
		return answer2Wrong;
	}

	/**
	 * returns to main menu when main menu button is pressed
	 * <p>
	 * frame current window that will be closed on click
	 *//* shouldn't be needed
	public static void returnToMainMenu(JFrame frame){
		CrabController.pauseTimers();
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
	}*/
	public static void setAnswer2Wrong(boolean a2W) {
		answer2Wrong = a2W;
	}

	public static boolean getAnswer3Wrong() {
		return answer3Wrong;
	}

	public static void setAnswer3Wrong(boolean a3W) {
		answer3Wrong = a3W;
	}

	public Timer getQuestionBufferTimer() {
		return questionBufferTimer;
	}

	public boolean getIsItQuestionBuffer() {
		return isItQuestionBuffer;
	}

	public void setIsItQuestionBuffer(boolean isItQuestionBuffer) {
		ContinueScreen.isItQuestionBuffer = isItQuestionBuffer;
	}

	static void makeQuestionButtons() {
		answer1.setContentAreaFilled(true);
		answer1.setBorderPainted(true);
		answer1.setEnabled(true);
		answer1.setFont(answer1.getFont().deriveFont(answer1.getFont().getStyle(), 30));
		answer1.setBackground(Color.RED);
		answer1.setBounds(178, CrabGameWorld.WORLD_HEIGHT - 240, 320, 80);
		answer1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				answerButton1Press();
			}
		});

		answer2.setContentAreaFilled(true);
		answer2.setBorderPainted(true);
		answer2.setEnabled(true);
		answer2.setFont(answer2.getFont().deriveFont(answer2.getFont().getStyle(), 30));
		answer2.setBackground(Color.BLUE);
		answer2.setBounds(518, CrabGameWorld.WORLD_HEIGHT - 240, 320, 80);
		answer2.addActionListener(
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						answerButton2Press();
					}
				}
		);

		answer3.setContentAreaFilled(true);
		answer3.setBorderPainted(true);
		answer3.setEnabled(true);
		answer3.setFont(answer3.getFont().deriveFont(answer3.getFont().getStyle(), 30));
		answer3.setBackground(Color.GREEN);
		answer3.setBounds(858, CrabGameWorld.WORLD_HEIGHT - 240, 320, 80);
		answer3.addActionListener(
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						answerButton3Press();
					}
				}
		);

		answer1.setEnabled(false);
		answer1.setVisible(false);

		answer2.setEnabled(false);
		answer2.setVisible(false);

		answer3.setEnabled(false);
		answer3.setVisible(false);

	}
}
