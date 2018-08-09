package main.java.crabGame.model;

import main.java.menu.view.MenuScreen;

/**
 * Fish (or other animals) that obstruct the crabbies
 */
public class Enemy extends Mover {
	public static final int ENEMY_WIDTH = MenuScreen.frameWidth / 13;
	public static final int ENEMY_HEIGHT = MenuScreen.frameHeight / 9;
	private int frame = 0;
	public final double ENEMY_VELOCITY = super.movementVelocity;

	/**
	 * Create instance of an Enemy
	 */
	public Enemy(int x, int y) {
		super(x, y, ENEMY_WIDTH, ENEMY_HEIGHT);
		movementVelocity = ENEMY_VELOCITY;
	}

	@Override
	void update(long deltaTime) {
		super.update(deltaTime);

		randomlyMoveEnemy();

	}

	void randomlyMoveEnemy(){
		double chanceToMove = .97;
		if (Math.random() > chanceToMove){
			moveUp();
			moveLeft();
		}
	}
	
	public int getFrame(){
		frame = (frame+1)%5;
		return frame;
	}
}
