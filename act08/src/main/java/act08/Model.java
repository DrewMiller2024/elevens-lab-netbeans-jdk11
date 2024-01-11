package act08;

import com.mrjaffesclass.apcs.messenger.*;

/**
 * The model represents the data used by the Elevens game.
 */
public class Model implements MessageHandler {

  // Messaging system for the MVC
  private final Messenger mvcMessaging;

  // Game state variables
  private int gameStatus;
  private Deck deck;
  private Card[] board;
  private boolean[] cardSelected;
  private boolean validSelection;
  private boolean gameWon;
  private int gamesWon;
  private int gamesPlayed;

  /**
   * Model constructor: Create the data representation of the program
   * @param messages Messaging class instantiated by the Controller for 
   *   local messages between Model, View, and controller
   */
  public Model(Messenger messages) {
    // This is for the messaging
    mvcMessaging = messages;    
  }
  
  /**
   * Initialize the model here and subscribe to any required messages
   */
  public void init() {
    // Create a new deck, new board array, and new cardSelected array to keep
    // track of the selected cards
    deck = new Deck(Constants.RANKS, Constants.SUITS, Constants.POINT_VALUES);
    board = new Card[Constants.BOARD_SIZE];
    cardSelected = new boolean[Constants.BOARD_SIZE];

    // Initialize the game counters
    gamesWon = 0;
    gamesPlayed = 0;    
    newGame();
  }
  
  /**
   * Initialize the model to start a new game
   */
  private void newGame() {
    deck.shuffle();
    for (int i = 0; i < Constants.BOARD_SIZE; i++) {
        board[i] = deck.deal();
        cardSelected[i] = false;
    }
    validSelection = false;
    //checks if the player is initially dealt a deck that has no matches
    if (isGameOver()) {
        gamesPlayed++;
        gameStatus = Constants.YOU_LOSE;
    } else {
        gameStatus = Constants.IN_PLAY;
    }
    gameWon = false;
  }

  /**
   * Check to see if the selected cards represent a legal move
   * @return True if move is legal
   */
  private boolean isLegalMoveSelected() {
      boolean legalMove = false;
      int count = 0;
      int totalPointValue = 0;
      boolean j = false;
      boolean q = false;
      boolean k = false;
      for (int i = 0; i < Constants.BOARD_SIZE; i++) {
          if (cardSelected[i]) {
            count++;
            int value = board[i].getPointValue();
            totalPointValue += value;
            if (value == 0) {
                switch (board[i].getRank()) {
                    case "jack":
                        j = true;
                        break;
                    case "queen":
                        q = true;
                        break;
                    case "king" :
                        k = true;
                        break;
                    default :
                        break;
                }
            }
          }
      }
      if (count == 2) {
          if (totalPointValue == 11) {
              legalMove = true;
          }
      } else if (count == 3) {
          if (j && q && k) {
              legalMove = true;
          }
      }
      return legalMove;
  }
  
  /**
   * Checks the board to see if there are legal plays available (but not
   * necessarily selected)
   * @return True if there are legal plays available
   */
  private boolean legalMovesAvailable() {
    // To be implemented in Activity 08
    boolean j = false;
    boolean q = false;
    boolean k = false;
    for (int i = 0; i < Constants.BOARD_SIZE; i++) {
        switch (board[i].getRank()) {
                    case "jack":
                        j = true;
                        break;
                    case "queen":
                        q = true;
                        break;
                    case "king" :
                        k = true;
                        break;
                    default :
                        break;
                }
    }
    
  }
  
  /**
   * Check to see if the game is over
   * @return True if the game is over
   */
  private boolean isGameOver() {
    // To be implemented in Activity 08
  }
  
  /**
   * The model must implement messageHandler so it can process
   * messages sent from the View.  messagePayload can be any object, but
   * it must be cast into the expected class.
   * @param messageName
   * @param messagePayload 
   */
  @Override
  public void messageHandler(String messageName, Object messagePayload) {
    switch (messageName) {
            
      default: {
        
      }
    }
  }
  
  /**
   * *********** FOR TESTING ONLY -- DO NOT CHANGE OR REMOVE ***********
   */
  public Object get(String prop) {
    switch (prop) {
      case "gameStatus": return gameStatus;
      case "deck":  return deck;
      case "board":  return board;
      case "cardSelected":  return cardSelected;
      case "validSelection": return validSelection;
      case "gameWon": return gameWon;
      case "gamesWon": return gamesWon;
      case "gamesPlayed": return gamesPlayed; 
      default: return null;
    }
  }
  
  public void set(String prop, Object val) {
    switch (prop) {
      case "gameStatus": gameStatus = (int)val; break;
      case "deck":  deck = (Deck)val; break;
      case "board": board = (Card[])val; break;
      case "cardSelected": cardSelected = (boolean[])val; break;
      case "validSelection":  validSelection = (boolean)val; break;
      case "gameWon":  gameWon = (boolean)val; break;
      case "gamesWon":  gamesWon = (int)val; break;
      case "gamesPlayed":  gamesPlayed = (int)val;  break;
      default:  ;
    }
  }
  
  public void _newGame() {
    newGame();
  }
  
  public boolean _isLegalMoveSelected() {
    return isLegalMoveSelected();
  }
  
  public boolean _legalMovesAvailable() {
    return legalMovesAvailable();
  }
  
  public boolean _isGameOver() {
    return isGameOver();
  }
  
}
