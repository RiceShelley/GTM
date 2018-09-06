package main.java.crabGame.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

import main.java.cubeGame.controller.SheetManager;
import main.java.menu.view.ImageManager;

/**
 * This class creates the questions and answers that appear
 */
public class Question {
	public static ArrayList<Question> questions = new ArrayList<Question>();
	private String prompt;
	private String correctAnswer;
	private ArrayList<String> answers;
	public static int qaState = 0;

	/**
	 * Creates instance of Question
	 *
	 * @param p              prompt to display at top of frame
	 * @param correctAnswer  the correct answer (if the corresponding button is
	 *                       selected, the game will continue)
	 * @param wrongAnswerOne an incorrect answer (selecting the corresponding button
	 *                       will not distanceSoFar the game)
	 * @param wrongAnswerTwo an incorrect answer (selecting the corresponding button
	 *                       will not distanceSoFar the game)
	 */
	public Question(String p, String correctAnswer, String wrongAnswerOne, String wrongAnswerTwo) {
		this.prompt = p;
		this.correctAnswer = correctAnswer;

		this.answers = new ArrayList<String>();

		this.answers.add(wrongAnswerOne);
		this.answers.add(wrongAnswerTwo);
		this.answers.add(correctAnswer);

		shuffle();
	}

	public static void load() {
		InputStream in = Question.class.getResourceAsStream("/main/resources/questions.txt");
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));

		String line = "";
		while (true) {
			try {
				line = reader.readLine();
				if (line == null)
					break;
				line = line.replaceAll("\"", "");
				String q[] = line.split(", ");
				questions.add(new Question(q[0], q[1], q[2], q[3]));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		Collections.shuffle(questions);
	}

	/**
	 * shuffles the answer so they will appear in a random order when the prompt
	 * appears
	 */
	public void shuffle() {
		Collections.shuffle(this.answers);
	}

	/**
	 * determines if answer selected is correct
	 *
	 * @param ans answer to test
	 * @return boolean
	 */
	public boolean isRightAnswer(String ans) {
		return (ans.equals(correctAnswer));
	}

	public String getPrompt() {
		return prompt;
	}

	public void setPrompt(String prompt) {
		this.prompt = prompt;
	}

	public String getCorrectAnswer() {
		return correctAnswer;
	}

	public void setCorrectAnswer(String correctAnswer) {
		this.correctAnswer = correctAnswer;
	}

	public ArrayList<String> getAnswers() {
		return answers;
	}

	public void setAnswers(ArrayList<String> answers) {
		this.answers = answers;
	}

}
