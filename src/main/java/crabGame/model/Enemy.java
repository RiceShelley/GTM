package main.java.crabGame.model;

/**
 * Fish (or other animals) that obstruct the crabbies
 */
public class Enemy extends Mover {
	public static final int ENEMY_WIDTH = 125;
	public static final int ENEMY_HEIGHT = 66;
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
