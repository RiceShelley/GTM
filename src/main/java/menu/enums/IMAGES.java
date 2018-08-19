package main.java.menu.enums;

import java.util.Arrays;

public enum IMAGES {
	CRAB_BUTTON("/main/resources/images/CrabButton.png"),
	BOAT_BUTTON("/main/resources/images/BoatButton.png"),
	CUBE_BUTTON("/main/resources/images/StoryCubesButton.png"),
	WAVE_IN("/main/resources/images/trans_in.png"),
	WAVE_OUT("/main/resources/images/Trans_out.png"),  //changed filename, had a capitol T    //FIX THIS PATH
	CUBE_ROLL("/main/resources/images/dice.png"),
	CUBE_FACE("/main/resources/images/genericdie.png"),
	CRAB_BG("/main/resources/images/Game1BG.png"),
	BOATS_BG("/main/resources/images/Game2BGfront.png"),
	CUBE_TABLE("/main/resources/images/woodBackground.jpg"),
	CUBE_BACKGROUND("/main/resources/images/estuaryBackground.jpg"),
	WATER("/main/resources/images/Game2BGrear.png"),
	BOAT_N("/main/resources/images/boatEmpty.png"),
	BOAT_O("/main/resources/images/boatOyster.png"),
	BOAT_C("/main/resources/images/boatCordgrass.png"),
	BOAT_R("/main/resources/images/boatRock.png"),
	OYSTER("/main/resources/images/oyster_bag.png"),
	CORDGRASS("/main/resources/images/cordgrass.png"),
	ROCK("/main/resources/images/rocks.png"),
	BLANK("/main/resources/images/blank.png"),
	BLUE_CRAB("/main/resources/images/bluecrab.png"),  //changed filename to have capitols, was lowercase   //FIX THIS PATH
	BASS("/main/resources/images/Game1_Enemy.png"),
	DOCK("/main/resources/images/dock.png"),
	QUEST_ACTION("/main/resources/images/call_to_action.png"),
	WAKE_LEFT("/main/resources/images/wakeLeft.png"),
	WAKE_RIGHT("/main/resources/images/wake.png"),
	ERO1("/main/resources/images/Game2Erosion1.png"),
	ERO2("/main/resources/images/Game2Erosion2.png"),
	ERO3("/main/resources/images/Game2Erosion3.png"),
	BOAT_END("/main/resources/images/boatEnd.png"),
	CUBE_END("/main/resources/images/cubeEndingScreen.png"),
	TRAIL("/main/resources/images/trail.png"),
	BOAT_TUT_1("/main/resources/images/tut1.png"),
	BOAT_TUT_2("/main/resources/images/tut2.png"),
	BOAT_TUT_3("/main/resources/images/tut3.png"),
	BOAT_TUT_4("/main/resources/images/tut4.png"),
	BOAT_TUT_5("/main/resources/images/tut7.png"),
	CUBE_TUT_1("/main/resources/images/tut6.png"),
	CUBE_TUTORIAL("/main/resources/images/cubeTutorial.png"),
	UP_ARROW("/main/resources/images/tut5.png"),
	X("/main/resources/images/erosion.png"),
	BLUE_CRAB_INVINCIBLE("/main/resources/images/BlueCrabInvincible.png"),
	HEART("/main/resources/images/fullHeart.png"),
	SUBMIT_BUTTON("/main/resources/images/submitButton.png"),
	ROLL_BUTTON("/main/resources/images/rollButton.png"),
	STOP_BUTTON("/main/resources/images/Stop.png"),
	START_BUTTON("/main/resources/images/Record.png"),
	GREEN_BAR("/main/resources/images/Game1Fill.png"),
	RED_BAR("/main/resources/images/Game1Empty.png"),
	DIE_SIL("/main/resources/images/dieSilhouette.png"),
	BOAT_TUTORIAL("/main/resources/images/boatTutorial.png"),
	MENU_BOAT2("/main/resources/images/tut9.png"),
	MENU_BOAT3("/main/resources/images/tut10.png"),
	MENU_BOAT4("/main/resources/images/tut11.png"),
	CRAB_TUTORIAL("/main/resources/images/crabTutorial.png"),
	MENU_CUBE1("/main/resources/images/tutcube1.png"),
	MENU_CUBE2("/main/resources/images/tutcube2.png"),
	MENU_CUBE3("/main/resources/images/tutcube3.png"),
	MENU_CUBE4("/main/resources/images/tutcube4.png"),
	TURTLE("/main/resources/images/turtle.png"),
	QUESTION_MARK("/main/resources/images/QuestionMark.png"),
	TUT_BG("/main/resources/images/tutorialBackground.png"),
	// The following are the new ending faces for the dice game
	DICE_BOAT("/main/resources/images/boatDie.png"),
	DICE_HORSESHOECRAB("/main/resources/images/horseshoeCrabDie.png"),
	DICE_CATFISH("/main/resources/images/catfishDie.png"),
	DICE_CLAM("/main/resources/images/clamDie.png"),
	DICE_CORDGRASS("/main/resources/images/cordgrassDie.png"),
	DICE_DOLPHIN("/main/resources/images/dolphinDie.png"),
	DICE_DUCK("/main/resources/images/duckDie.png"),
	DICE_HIKERS("/main/resources/images/hikersDie.png"),
	DICE_OTTER("/main/resources/images/otterDie.png"),
	DICE_RABBIT("/main/resources/images/rabbitDie.png"),
	DICE_ROCKS("/main/resources/images/rocksDie.png"),
	DICE_SEAWEED("/main/resources/images/seaweedDie.png"),
	DICE_WILDCARD("/main/resources/images/wildCardDie.png"),
	DICE_GABION("/main/resources/images/gabionDie.png"),
	DICE_SEAWALL("/main/resources/images/seawallDie.png"),
	DICE_FISHING("/main/resources/images/fishingDie.png"),
	DICE_OYSTER("/main/resources/images/oysterDie.png"),
	DICE_TURTLE("/main/resources/images/turtleDie.png"),
	DICE_PLASTIC("/main/resources/images/plasticDie.png");

	public final String path;

	IMAGES(String path) {
		this.path = path;
	}
	
	public static IMAGES getImage(String path) throws Exception {
		return Arrays.stream(values()).filter(image -> image.path.equals(path)).findAny().get();
	}
		
}