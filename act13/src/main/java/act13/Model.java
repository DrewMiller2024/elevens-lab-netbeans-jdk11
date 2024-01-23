package act13;

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
    // Message subscriptions
    mvcMessaging.subscribe("view:cardClicked", this);
    mvcMessaging.subscribe("view:clearAll", this);
    mvcMessaging.subscribe("view:play", this);  
    mvcMessaging.subscribe("view:newGame", this);
    
    // Create a new deck, new board array, and new cardSelected array to keep
    // track of the selected cards
    deck = new Deck(Constants.RANKS, Constants.SUITS, Constants.POINT_VALUES);
    board = new Card[Constants.BOARD_SIZE];
    cardSelected = new boolean[Constants.BOARD_SIZE];

    // Initialize the game counters
    gamesWon = 0;
    gamesPlayed = 0;
    
    // Start a new game
    newGame();    
  }
  
  /**
   * Initialize the model to start a new game
   */
  private void newGame() {
    deck.shuffle();
    
    // Deal the first BOARD_SIZE cards from the deck to the board and
    // de-select them all
    for (int i=0; i<Constants.BOARD_SIZE; i++) {
      board[i] = deck.deal();
      cardSelected[i] = false;
    }
    
    // Initialize the other model variables to start the game
    validSelection = false;
    gameStatus = Constants.IN_PLAY;
    gameWon = false;

    // Check to see if the game is over before it's begun!
    if (isGameOver()) {
      // Adjust the counters and the gameStatus variable
      gamesPlayed++;
      gameStatus = Constants.YOU_LOSE;
    }

  }

  /**
   * Check to see if the selected cards represent a legal move
   * @return True if move is legal
   */
  private boolean isLegalMoveSelected() {
    int numberSelected = 0;       // Number of selected cards
    int total = 0;                // Rank total of the selected cards
    
    // Scan through the selected cards and find the total number of 
    // cards selected, the total point value, and if the selected cards
    // are a jack, queen, and/or king
    boolean jack = false, queen = false, king = false;
    for (int i=0; i<cardSelected.length; i++) {
      if (board[i] != null) {
        numberSelected = (cardSelected[i]) ? numberSelected+1 : numberSelected;
        total = (cardSelected[i]) ? total+board[i].getPointValue() : total;
        jack = jack || (cardSelected[i] && board[i].getRank().equals("jack"));
        queen = queen || (cardSelected[i] && board[i].getRank().equals("queen"));
        king = king || (cardSelected[i] && board[i].getRank().equals("king"));
      }
    }
    // If the are not either two or three cards selected, then the
    // play is not legal
    if (numberSelected != 2 && numberSelected != 3) return false;
    
    // If there are two cards selected, but the rank total is not 11
    // then the play is not legal
    if (numberSelected == 2 && total != 11) return false;
    // If the number selected is 3, but the three selected are not exactly
    // one of a jack, queen, and king then the move is not legal.
    // Otherwise, the move is legal -- yay!!
    return !(numberSelected == 3 && !(jack && queen && king));
  }
  
  /**
   * Checks the board to see if there are legal plays available (but not
   * necessarily selected)
   * @return True if there are legal plays available
   */
  private boolean legalMovesAvailable() {
    // Keeps track of if we have seen at least one of the face cards
    boolean jack = false, queen = false, king = false;
    
    // For each card on the board...
    for (int i=0; i<board.length; i++) {
      if (board[i] != null) {
        // check to see if it's a face card/
        jack = jack || board[i].getRank().equals("jack");
        queen = queen || board[i].getRank().equals("queen");
        king = king || board[i].getRank().equals("king");
        // Then for each second card...
        for (int j=i+1; j<board.length; j++) {
          if (board[j] != null) {
            // ... check to see if the total point value is 11 representing
            // a legal card combination
            if (board[i].getPointValue() + board[j].getPointValue() == 11) return true;
          }
        }
      }
    }
    // If we've seen at least one jack, queen, and king, then we also 
    // have a legal play.  Otherwise there is no legal play available
    return jack && queen && king;
  }
  
  /**
   * Check to see if the game is over
   * @return True if the game is over
   */
  private boolean isGameOver() {
    return !legalMovesAvailable() || deck.isEmpty();
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
      
      // A card was clicked in the view
      case "view:cardClicked": {
        if (gameStatus == Constants.IN_PLAY) {
          int buttonNumber = (int)messagePayload;
          if (board[buttonNumber] != null) {
            // Toggle the cardSelected value for the button's index
            cardSelected[buttonNumber] = !cardSelected[buttonNumber];
          }
        }
        
        mvcMessaging.notify("model:selectedCardsChanged", cardSelected);
        mvcMessaging.notify("model:isLegalMove", isLegalMoveSelected());
        break;
      }
      
      // The Clear all button was pressed which should de-select all the cards
      case "view:clearAll": {
        for (int i=0; i<cardSelected.length; i++) {
          cardSelected[i] = false;
        }
        mvcMessaging.notify("model:selectedCardsChanged", cardSelected);
        mvcMessaging.notify("model:isLegalMove", isLegalMoveSelected());
        break;
      }
      
      // The Play button was pressed. We should play the selected cards
      case "view:play": {
        for (int i=0; i<cardSelected.length; i++) {
          // For each selected card...
          if (cardSelected[i]) {
            // ... deal another card from the deck and de-select the card
            board[i] = deck.deal();
            cardSelected[i] = false;
          }
        }
      
        if (isGameOver()) {
          // Adjust the counters and the gameStatus variable
          gamesPlayed++;
          if (deck.isEmpty()) {
            gameStatus = Constants.YOU_WIN;
            gamesWon++;
          } else {
            gameStatus = Constants.YOU_LOSE;
          }
        }
        
        mvcMessaging.notify("model:boardChanged", board);
        mvcMessaging.notify("model:selectedCardsChanged", cardSelected);
        mvcMessaging.notify("model:isLegalMove", isLegalMoveSelected());
        mvcMessaging.notify("model:cardsLeftInDeck", deck.size());
        mvcMessaging.notify("model:gamesWon", gamesWon);
        mvcMessaging.notify("model:gamesPlayed", gamesPlayed);
        mvcMessaging.notify("model:gameStatus", gameStatus);
        break;
      }
      
      // The New game button was pressed
      case "view:newGame": {
        this.newGame();
        mvcMessaging.notify("model:boardChanged", board);
        mvcMessaging.notify("model:selectedCardsChanged", cardSelected);
        mvcMessaging.notify("model:isLegalMove", false);
        mvcMessaging.notify("model:cardsLeftInDeck", deck.size());
        mvcMessaging.notify("model:gamesWon", gamesWon);
        mvcMessaging.notify("model:gamesPlayed", gamesPlayed);
        mvcMessaging.notify("model:gameStatus", gameStatus);
        break;
      }
      
      default: {
        
      }
    }
  }
  
  /**
   * *********** FOR TESTING ONLY -- DO NOT CHANGE OR REMOVE ***********
   * Method to access private fields
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
  
}
