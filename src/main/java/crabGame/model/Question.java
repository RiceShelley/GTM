package main.java.crabGame.model;

import java.util.ArrayList;
import java.util.Collections;

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
	 * @param p   prompt to display at top of frame
	 * @param correctAnswer  the correct answer (if the corresponding button is selected, the game will continue)
	 * @param wrongAnswerOne an incorrect answer (selecting the corresponding button will not distanceSoFar the game)
	 * @param wrongAnswerTwo an incorrect answer (selecting the corresponding button will not distanceSoFar the game)
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
		questions.add(new Question("Which of these animals live in an estuary?", "Crab", "Bear", "Human"));
		questions.add(new Question("Which of these animals live in an estuary?", "Fish", "Giraffe", "Rhino"));
		questions.add(new Question("Which of these animals live in an estuary?", "Turtle", "Elephant", "Tiger"));
		questions.add(new Question("Is an estuary made of...?", "Salt and Fresh Water", "Salt Water", "Fresh Water"));
		questions.add(new Question("These structures are the best at keeping the estuary from being washed away.", "Gabions", "Sea Walls", "Bulkheads"));
		questions.add(new Question("How do crabs find their way to the estuary?", "Smelling the saltiness", "Internal compass", "Asking for directions"));
		questions.add(new Question("Where do crabs give birth to their young?", "Salt Water", "Fresh Water", "In the Estuary"));
		questions.add(new Question("In what state are the NERR's located?", "Delaware", "Oklahoma", "Canada"));
		questions.add(new Question("In what state are the NERR's located?", "Florida", "Nevada", "Kansas"));
		questions.add(new Question("In what state are the NERR's located?", "Texas", "Europe", "Idaho"));
		questions.add(new Question("Which of these can change the saltiness of an estuary?", "Storms", "Tuesdays", "Sunshine"));
		questions.add(new Question("Which of these can change the saltiness of an estuary?", "Drought", "Wind", "Winter"));
		questions.add(new Question("Which of these can change the saltiness of an estuary?", "Construction", "Summer", "Clouds"));
		questions.add(new Question("Gabions are made of what natural resource.", "Oyster Shells", "Concrete", "Wood"));
		questions.add(new Question("Which of these is a type of estuary?", "Bar-Built Estuary", "Salt and Vinegar Estuary", "Geometric Estuary"));
		questions.add(new Question("Which of these is a type of estuary?", "Coastal Plains Estuary", "Coniferous Estuary", "Bubble Estuary"));
		questions.add(new Question("Which of these is a type of estuary?", "Fjord Estuary", "Refrigerator Estuary", "iEstuary"));
		questions.add(new Question("Which of these is a type of estuary?", "Tectonic Estuary", "Gemstone Estuary", "Big and Tall Estuary"));
		questions.add(new Question("What are the sediment deposits at the end of rivers called?", "Deltas", "Gammas", "Omegas"));
		questions.add(new Question("Fish use these to breathe underwater.", "Gills", "Wings", "Horns"));
		questions.add(new Question("Crabs are this type of animal.", "Crustaceans", "Mammals", "Dinosaurs"));
		questions.add(new Question("Turtles are this type of animal", "Reptiles", "Insects", "Rodents"));
		questions.add(new Question("Which of these is the name of a fish?", "Sea Bass", "Tony", "Pomegranate"));
		questions.add(new Question("Which of these is the name of a fish?", "Pickerel", "Grizzly", "Square"));
		questions.add(new Question("Which of these is the name of a fish?", "Trout", "Emerald", "Snapchat"));
		Collections.shuffle(questions);
	}

	/**
	 * shuffles the answer so they will appear in a random order when the prompt appears
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
