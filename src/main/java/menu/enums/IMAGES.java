package main.java.menu.enums;

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
	CUBE_BG("/main/resources/images/Game3 BG.png"),
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
	CUBE_END("/main/resources/images/Blackbird_Eric_Crossan_Web.png"),
	TRAIL("/main/resources/images/trail.png"),
	BOAT_TUT_1("/main/resources/images/tut1.png"),
	BOAT_TUT_2("/main/resources/images/tut2.png"),
	BOAT_TUT_3("/main/resources/images/tut3.png"),
	BOAT_TUT_4("/main/resources/images/tut4.png"),
	BOAT_TUT_5("/main/resources/images/tut7.png"),
	CUBE_TUT_1("/main/resources/images/tut6.png"),
	UP_ARROW("/main/resources/images/tut5.png"),
	X("/main/resources/images/erosion.png"),
	BLUE_CRAB_INVINCIBLE("/main/resources/images/BlueCrabInvincible.png"),
	HEART("/main/resources/images/fullHeart.png"),
	ROLL_BUTTON("/main/resources/images/rollButton.png"),
	STOP_BUTTON("/main/resources/images/Stop.png"),
	START_BUTTON("/main/resources/images/Record.png"),
	GREEN_BAR("/main/resources/images/Game1Fill.png"),
	RED_BAR("/main/resources/images/Game1Empty.png"),
	DIE_SIL("/main/resources/images/dieSilhouette.png"),
	MENU_BOAT1("/main/resources/images/tut8.png"),
	MENU_BOAT2("/main/resources/images/tut9.png"),
	MENU_BOAT3("/main/resources/images/tut10.png"),
	MENU_BOAT4("/main/resources/images/tut11.png"),
	MENU_CRAB1("/main/resources/images/tutcrab1.png"),
	MENU_CRAB2("/main/resources/images/tutcrab2.png"),
	MENU_CRAB3("/main/resources/images/tutcrab3.png"),
	MENU_CRAB4("/main/resources/images/tutcrab4.png"),
	MENU_CUBE1("/main/resources/images/tutcube1.png"),
	MENU_CUBE2("/main/resources/images/tutcube2.png"),
	MENU_CUBE3("/main/resources/images/tutcube3.png"),
	MENU_CUBE4("/main/resources/images/tutcube4.png"),
	QUESTION_MARK("/main/resources/images/qmark.png"),
	// The following are the new ending faces for the dice game
	DICE_ALLIGATOR("/main/resources/images/qmark.png"),
	DICE_CATFISH("/main/resources/images/catfishCard.png"),
	DICE_CLAM("/main/resources/images/clamCard.png"),
	DICE_CORAL("/main/resources/images/coralCard.png"),
	DICE_CORDGRASS("/main/resources/images/cordgrassCard.png"),
	DICE_DOLPHIN("/main/resources/images/dolphinCard.png"),
	DICE_DUCK("/main/resources/images/duckCard.png"),
	DICE_BOAT("/main/resources/images/fishingBoatCard.png"),
	DICE_FLOWER("/main/resources/images/flowerCard.png"),
	DICE_HERON("/main/resources/images/heronCard.png"),
	DICE_HIKERS("/main/resources/images/hikersCard.png"),
	DICE_OTTER("/main/resources/images/otterCard.png"),
	DICE_RABBIT("/main/resources/images/rabbitCard.png"),
	DICE_RACCOON("/main/resources/images/raccoonCard.png"),
	DICE_ROCK("/main/resources/images/rockCard.png"),
	DICE_SEAWEED("/main/resources/images/seaweedCard.png"),
	DICE_SNAPPER("/main/resources/images/snapperCard.png"),
	DICE_WILDCARD("/main/resources/images/qmark.png"),
	DICE_MANGROVE("/main/resources/images/mangroveCard.png");

	

	public final String path;

	IMAGES(String path) {
		this.path = path;
	}
	
		
}