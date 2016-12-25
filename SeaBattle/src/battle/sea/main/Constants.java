package battle.sea.main;

import java.awt.Dimension;

public class Constants {
	public static final int SCALE = 30;
	/* 
	 * Grid step
	 * */
	public static final int START_STEP = 0;
	public static final int WIDTH_STEP = 10;
	public static final int HEIGHT_STEP = 10;
	
	/*
	 * Indents
	 * */
	public static final int DELIM = 100;
	public static final int LEFT = 30;
	public static final int RIGHT = 30;
	public static final int TOP = 20;
	public static final int BOTTOM = 100;
	
	public static final String[] num_arr = {"0","1","2","3","4","5","6","7","8","9"};
	public static final String[] char_arr = {"0","1","2","3","4","5","6","7","8","9"};
	//public static final String[] char_arr = {"A","B","C","D","E","F","G","H","I","J"};
	
	/*
	 * WIDTH_1 : WIDTH_2 - first field
	 * WIDTH_3 : WIDTH_4 - second field
	 * HEIGHT_1: HEIGHT_2 - elevation
	 * */
	public static final int WIDTH = WIDTH_STEP*SCALE;
	public static final int WIDTH_1 = LEFT;
	public static final int WIDTH_2 = WIDTH_1+WIDTH;
	public static final int WIDTH_3 = WIDTH_2+DELIM;
	public static final int WIDTH_4 = WIDTH_3+WIDTH;
	public static final int WIDTH_5 = WIDTH_4+RIGHT;
	
	public static final int HEIGHT = HEIGHT_STEP*SCALE;
	public static final int HEIGHT_1 = TOP;
	public static final int HEIGHT_2 = HEIGHT_1+HEIGHT;
	public static final int HEIGHT_3 = HEIGHT_2+BOTTOM;
	
	public static final Dimension DIM = new Dimension(WIDTH_5, HEIGHT_3);
}
