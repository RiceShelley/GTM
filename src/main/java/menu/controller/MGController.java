package main.java.menu.controller;

public abstract class MGController {
	protected MGView view;
    /**
     * called once per tick; repaints the JFrame and advance the model
     */
    public abstract void update();

    /**
     * safely kills the JFrame when the user switches game.
     */
    public abstract void dispose();

	public abstract MGView getView();
    
    
}
