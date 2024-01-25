package act15;

import java.awt.Color;

/**
 * Elevens game constants are saved here to be used throughout the program
 */
public class Constants {
  /**
   * Deck initialization values.  The RANKS and POINT_VALUES match up element
   * for element and the Deck constructors fills a deck with cards of each
   * rank and suit.  Note that in Elevens, the face cards (J, Q, K) have no
   * value during game play
   */
  public static final String[] RANKS = {"ace","2","3","4","5","6","7","8","9","10","jack","queen","king"};
  public static final String[] SUITS = {"clubs","spades","diamonds","hearts"};
  public static final int[] POINT_VALUES = {1,2,3,4,5,6,7,8,9,10,0,0,0};
  
  /**
   * The number of cards in a player's hand
   */
  public static final int BOARD_SIZE = 9;
  
  /**
   * Game status constants
   */
  public static final int YOU_WIN = 1;
  public static final int YOU_LOSE = 2;
  public static final int IN_PLAY = 0;
  
  
  /**
   * Color and line width constants go here
   */
  public static final Color SELECTED_COLOR = Color.YELLOW;
  public static final Color WIN_COLOR = Color.BLUE;
  public static final Color LOSE_COLOR = Color.BLACK;
  public static final int BORDER_WIDTH = 2;
}
