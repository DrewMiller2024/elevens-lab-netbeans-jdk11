package act15;

import com.mrjaffesclass.apcs.messenger.*;
import java.awt.Color;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;

public class View extends javax.swing.JFrame implements MessageHandler {

  private final Messenger mvcMessaging;
  
  private JLabel[] cards;
  
  /**
   * Creates a new view
   * @param messages mvcMessaging object
   */
  public View(Messenger messages) {
    mvcMessaging = messages;   // Save the calling controller instance
    initComponents();           // Create and init the GUI components
    cards = new JLabel[9];
  }
  
  /**
   * Initialize the model here and subscribe
   * to any required messages
   */
  public void init() {
    // Subscribe to messages here
    mvcMessaging.subscribe("model:selectedCardsChanged", this);
    mvcMessaging.subscribe("model:isLegalMove", this);
    mvcMessaging.subscribe("model:boardChanged", this);
    mvcMessaging.subscribe("model:cardsLeftInDeck", this);
    mvcMessaging.subscribe("model:gamesWon", this);
    mvcMessaging.subscribe("model:gamesPlayed", this);
    mvcMessaging.subscribe("model:gameStatus", this);
    
    // Load card slots into an array for easier manipulation
    cards[0] = card1;
    cards[1] = card2;
    cards[2] = card3;
    cards[3] = card4;
    cards[4] = card5;
    cards[5] = card6;
    cards[6] = card7;
    cards[7] = card8;
    cards[8] = card9;
  }
  
  /**
   * Sets the borders of the cards -- blue for selected cards
   * and no border for unselected cards
   * @param cardSelectedStatus Boolean array representing the selected status
   * @return number of selected cards
   */
  private int setBorders(boolean[] cardSelectedStatus, Color color, int thickness) {
    int count = 0;
    for (int i=0; i<cardSelectedStatus.length; i++) {
      if (cardSelectedStatus[i]) {
        cards[i].setBorder(new LineBorder(color, thickness));
        count++;
      } else {
        cards[i].setBorder(null);
      }
    }    
    return count;
  }
  
  /**
   * Set the border of all the cards to the provided color
   * @param color Color to set the border
   */
  private void setAllBorders(Color color, int thickness) {
    for (JLabel card : cards) {
      card.setBorder(new LineBorder(color, thickness));
    }            
  } 
  
  /**
   * Sets the card faces based on the values stored in the
   * cardData.  Each Card object has the rank and suit which are used
   * to build the file name of the card image.
   * @param cardData 
   */
  private void setCards(Card[] cardData) {
    for (int i=0; i<cards.length; i++) {
      if (cardData[i] != null) {
        // If the card is defined then build the image file name from 
        // the rank and suit ...
        String imageFileName = "/"+cardData[i].getRank()+cardData[i].getSuit()+".GIF";

        // ... and create an ImageIcon and set it in the Card view object
        URL imageURL = getClass().getResource(imageFileName);
        cards[i].setIcon(new ImageIcon(imageURL));
      } else {
        // If the card is not defined, then just leave the card view blank
        cards[i].setText("");
      }
    }    
  }
  
  /**
   * The model must implement messageHandler so the Model can process
   * messages sent from the View.  messagePayload can be any object, but
   * it must be cast into the expected class.
   * @param messageName
   * @param messagePayload 
   */
  @Override
  public void messageHandler(String messageName, Object messagePayload) {
    switch(messageName) {
      
      // The selected cards have changed so we should re-draw the borders
      // and enable/disable the Clear all button accordingly
      case "model:selectedCardsChanged": {
        boolean[] cardSelectedStatus = (boolean[]) messagePayload;
        int count = setBorders(cardSelectedStatus, Constants.SELECTED_COLOR, Constants.BORDER_WIDTH);
        clearAllBtn.setEnabled(count > 0);
        break;        
      }
      
      // Received from the model if the selected cards represent a legal move.
      // We'll use this information to enable/disable the Play button. We will
      // only enable the Play button if the selected cards represent a legal move.
      case "model:isLegalMove": {
        playBtn.setEnabled((boolean)messagePayload);
        break;
      }
      
      // The board has changed so we should update the board display accordingly
      case "model:boardChanged": {
        Card[] cardData = (Card[]) messagePayload;
        setCards(cardData);
        break;
      }
      
      // The payload contains the number of cards left in the deck so we'll
      // update the UI
      case "model:cardsLeftInDeck": {
        cardsLeft.setText(Integer.toString((int)messagePayload));
        break;
      }
      
      // The payload contains the number of games won so we'll update 
      // the UI
      case "model:gamesWon": {
        gamesWon.setText(Integer.toString((int)messagePayload));
        break;
      }
      
      // The payload contains the number of games played so we'll update
      // the UI
      case "model:gamesPlayed": {
        gamesPlayed.setText(Integer.toString((int)messagePayload));
        break;
      }
      
      // The payload contains the current game state (IN_PLAY, YOU_WON, or 
      // YOU_LOST).  We will update the UI accordingly
      case "model:gameStatus": {
        switch ((int)messagePayload) {
          case Constants.IN_PLAY:
            directionsLabel.setText("Click to select a card; click again to de-select");
            break;
            
          case Constants.YOU_WIN:
            directionsLabel.setText("YOU WIN!!  Game over!");
            setAllBorders(Constants.WIN_COLOR, Constants.BORDER_WIDTH);
            break;
            
          case Constants.YOU_LOSE:
            directionsLabel.setText("YOU LOSE!!  Game over!");
            setAllBorders(Constants.LOSE_COLOR, Constants.BORDER_WIDTH);
            break;
            
          default:
            break;
        }
      }
      
      default: {
        
      }
    }
  }

  /**
   * This method is called from within the constructor to initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is always
   * regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    canvas1 = new java.awt.Canvas();
    card1 = new javax.swing.JLabel();
    card2 = new javax.swing.JLabel();
    card3 = new javax.swing.JLabel();
    card4 = new javax.swing.JLabel();
    card5 = new javax.swing.JLabel();
    card9 = new javax.swing.JLabel();
    card6 = new javax.swing.JLabel();
    card7 = new javax.swing.JLabel();
    card8 = new javax.swing.JLabel();
    playBtn = new javax.swing.JButton();
    directionsLabel = new javax.swing.JLabel();
    numberOfCardsLabel = new javax.swing.JLabel();
    numberOfGamesWonLabel = new javax.swing.JLabel();
    numberOfGamesPlayedLabel = new javax.swing.JLabel();
    cardsLeft = new javax.swing.JLabel();
    gamesWon = new javax.swing.JLabel();
    gamesPlayed = new javax.swing.JLabel();
    clearAllBtn = new javax.swing.JButton();
    newGameBtn = new javax.swing.JButton();

    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

    card1.setText("card1");
    card1.setMaximumSize(new java.awt.Dimension(73, 97));
    card1.setMinimumSize(new java.awt.Dimension(73, 97));
    card1.setName("0"); // NOI18N
    card1.setPreferredSize(new java.awt.Dimension(73, 97));
    card1.setSize(new java.awt.Dimension(73, 97));
    card1.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(java.awt.event.MouseEvent evt) {
        cardClicked(evt);
      }
    });

    card2.setText("card2");
    card2.setMaximumSize(new java.awt.Dimension(77, 101));
    card2.setMinimumSize(new java.awt.Dimension(77, 101));
    card2.setName("1"); // NOI18N
    card2.setPreferredSize(new java.awt.Dimension(77, 101));
    card2.setSize(new java.awt.Dimension(77, 101));
    card2.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(java.awt.event.MouseEvent evt) {
        cardClicked(evt);
      }
    });

    card3.setText("card3");
    card3.setMaximumSize(new java.awt.Dimension(77, 101));
    card3.setMinimumSize(new java.awt.Dimension(77, 101));
    card3.setName("2"); // NOI18N
    card3.setPreferredSize(new java.awt.Dimension(77, 101));
    card3.setSize(new java.awt.Dimension(77, 101));
    card3.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(java.awt.event.MouseEvent evt) {
        cardClicked(evt);
      }
    });

    card4.setText("card4");
    card4.setMaximumSize(new java.awt.Dimension(77, 101));
    card4.setMinimumSize(new java.awt.Dimension(77, 101));
    card4.setName("3"); // NOI18N
    card4.setPreferredSize(new java.awt.Dimension(77, 101));
    card4.setSize(new java.awt.Dimension(77, 101));
    card4.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(java.awt.event.MouseEvent evt) {
        cardClicked(evt);
      }
    });

    card5.setText("card5");
    card5.setMaximumSize(new java.awt.Dimension(77, 101));
    card5.setMinimumSize(new java.awt.Dimension(77, 101));
    card5.setName("4"); // NOI18N
    card5.setPreferredSize(new java.awt.Dimension(77, 101));
    card5.setSize(new java.awt.Dimension(77, 101));
    card5.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(java.awt.event.MouseEvent evt) {
        cardClicked(evt);
      }
    });

    card9.setText("card9");
    card9.setMaximumSize(new java.awt.Dimension(77, 101));
    card9.setMinimumSize(new java.awt.Dimension(77, 101));
    card9.setName("8"); // NOI18N
    card9.setPreferredSize(new java.awt.Dimension(77, 101));
    card9.setSize(new java.awt.Dimension(77, 101));
    card9.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(java.awt.event.MouseEvent evt) {
        cardClicked(evt);
      }
    });

    card6.setText("card6");
    card6.setMaximumSize(new java.awt.Dimension(77, 101));
    card6.setMinimumSize(new java.awt.Dimension(77, 101));
    card6.setName("5"); // NOI18N
    card6.setPreferredSize(new java.awt.Dimension(77, 101));
    card6.setSize(new java.awt.Dimension(77, 101));
    card6.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(java.awt.event.MouseEvent evt) {
        cardClicked(evt);
      }
    });

    card7.setText("card7");
    card7.setMaximumSize(new java.awt.Dimension(77, 101));
    card7.setMinimumSize(new java.awt.Dimension(77, 101));
    card7.setName("6"); // NOI18N
    card7.setPreferredSize(new java.awt.Dimension(77, 101));
    card7.setSize(new java.awt.Dimension(77, 101));
    card7.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(java.awt.event.MouseEvent evt) {
        cardClicked(evt);
      }
    });

    card8.setText("card8");
    card8.setMaximumSize(new java.awt.Dimension(77, 101));
    card8.setMinimumSize(new java.awt.Dimension(77, 101));
    card8.setName("7"); // NOI18N
    card8.setPreferredSize(new java.awt.Dimension(77, 101));
    card8.setSize(new java.awt.Dimension(77, 101));
    card8.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(java.awt.event.MouseEvent evt) {
        cardClicked(evt);
      }
    });

    playBtn.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
    playBtn.setText("Play");
    playBtn.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        playBtnActionPerformed(evt);
      }
    });

    directionsLabel.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
    directionsLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    directionsLabel.setText("Click to select a card; click again to de-select");
    directionsLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
    directionsLabel.setSize(new java.awt.Dimension(368, 20));

    numberOfCardsLabel.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
    numberOfCardsLabel.setText("Number of cards left in the deck:");

    numberOfGamesWonLabel.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
    numberOfGamesWonLabel.setText("Number of games won:");

    numberOfGamesPlayedLabel.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
    numberOfGamesPlayedLabel.setText("Number of games played:");

    cardsLeft.setText("cardsLeft");

    gamesWon.setText("gamesWon");

    gamesPlayed.setText("gamesPlayed");

    clearAllBtn.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
    clearAllBtn.setText("Clear all");
    clearAllBtn.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        clearAllBtnActionPerformed(evt);
      }
    });

    newGameBtn.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
    newGameBtn.setText("New game");
    newGameBtn.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        newGameBtnActionPerformed(evt);
      }
    });

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
          .addComponent(directionsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 421, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
              .addGap(265, 265, 265)
              .addComponent(canvas1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
              .addComponent(playBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addGap(18, 18, 18)
              .addComponent(clearAllBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
              .addComponent(newGameBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
              .addGap(24, 24, 24)
              .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                .addGroup(layout.createSequentialGroup()
                  .addComponent(card6, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                  .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                  .addComponent(card7, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                  .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                  .addComponent(card8, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                  .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                  .addComponent(card9, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createSequentialGroup()
                  .addComponent(card1, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                  .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                  .addComponent(card2, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                  .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                  .addComponent(card3, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                  .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                  .addComponent(card4, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)))
              .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
              .addComponent(card5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
              .addGap(66, 66, 66)
              .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                .addComponent(numberOfCardsLabel)
                .addComponent(numberOfGamesWonLabel)
                .addComponent(numberOfGamesPlayedLabel))
              .addGap(18, 18, 18)
              .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(gamesWon)
                .addComponent(gamesPlayed)
                .addComponent(cardsLeft)))))
        .addContainerGap(46, Short.MAX_VALUE))
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addComponent(directionsLabel)
        .addGap(18, 18, 18)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(card1, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(card2, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(card3, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(card4, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(card5, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addGap(18, 18, 18)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(card9, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(card6, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(card7, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(card8, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(playBtn)
          .addComponent(clearAllBtn)
          .addComponent(newGameBtn))
        .addGap(30, 30, 30)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(numberOfCardsLabel)
          .addComponent(cardsLeft))
        .addGap(18, 18, Short.MAX_VALUE)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(numberOfGamesWonLabel)
          .addComponent(gamesWon))
        .addGap(18, 18, 18)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(numberOfGamesPlayedLabel)
          .addComponent(gamesPlayed))
        .addGap(81, 81, 81)
        .addComponent(canvas1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
    );

    pack();
  }// </editor-fold>//GEN-END:initComponents

  private void playBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_playBtnActionPerformed
    // Play button was pressed!
    mvcMessaging.notify("view:play");
  }//GEN-LAST:event_playBtnActionPerformed

  private void cardClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cardClicked
    // A card was clicked
    String name = ((JLabel)evt.getSource()).getName();
    int buttonNumber = Integer.parseInt(name);
    mvcMessaging.notify("view:cardClicked", buttonNumber);
  }//GEN-LAST:event_cardClicked

  private void clearAllBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearAllBtnActionPerformed
    // The Clear all button was pressed
    mvcMessaging.notify("view:clearAll");
  }//GEN-LAST:event_clearAllBtnActionPerformed

  private void newGameBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newGameBtnActionPerformed
    // The New game button was pressed
    mvcMessaging.notify("view:newGame");
  }//GEN-LAST:event_newGameBtnActionPerformed

  /**
   * Handler for the up button for field 1
   * @param evt 
   */
  /**
   * @param args the command line arguments
   */

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private java.awt.Canvas canvas1;
  private javax.swing.JLabel card1;
  private javax.swing.JLabel card2;
  private javax.swing.JLabel card3;
  private javax.swing.JLabel card4;
  private javax.swing.JLabel card5;
  private javax.swing.JLabel card6;
  private javax.swing.JLabel card7;
  private javax.swing.JLabel card8;
  private javax.swing.JLabel card9;
  private javax.swing.JLabel cardsLeft;
  private javax.swing.JButton clearAllBtn;
  private javax.swing.JLabel directionsLabel;
  private javax.swing.JLabel gamesPlayed;
  private javax.swing.JLabel gamesWon;
  private javax.swing.JButton newGameBtn;
  private javax.swing.JLabel numberOfCardsLabel;
  private javax.swing.JLabel numberOfGamesPlayedLabel;
  private javax.swing.JLabel numberOfGamesWonLabel;
  private javax.swing.JButton playBtn;
  // End of variables declaration//GEN-END:variables

    /**
   * *********** FOR TESTING ONLY -- DO NOT CHANGE OR REMOVE ***********
   */
  public Object get(String prop) {
    switch (prop) {
      case "card1": return card1;
      case "card2": return card2;
      case "card3": return card3;
      case "card4": return card4;
      case "card5": return card5;
      case "card6": return card6;
      case "card7": return card7;
      case "card8": return card8;
      case "card9": return card9;
      case "cards": return cards;
      case "cardsLeft": return cardsLeft;
      case "clearAllBtn": return clearAllBtn;
      case "directionsLabel": return directionsLabel;
      case "gamesPlayed": return gamesPlayed;
      case "gamesWon": return gamesWon;
      case "newGameBtn": return newGameBtn;
      case "playBtn": return playBtn;
      default: return null;
    }
  }
    
  public void set(String prop, Object val) {
    switch (prop) {
      case "card1": card1 = (JLabel)val; break;
      case "card2": card2 = (JLabel)val; break;
      case "card3": card3 = (JLabel)val; break;
      case "card4": card4 = (JLabel)val; break;
      case "card5": card5 = (JLabel)val; break;
      case "card6": card6 = (JLabel)val; break;
      case "card7": card7 = (JLabel)val; break;
      case "card8": card8 = (JLabel)val; break;
      case "card9": card9 = (JLabel)val; break;
      case "cards": cards = (JLabel[])val; break;
      case "cardsLeft": cardsLeft = (JLabel)val; break;
      case "directionsLabel": directionsLabel = (JLabel)val; break;
      case "gamesPlayed": gamesPlayed = (JLabel)val; break;
      case "gamesWon": gamesWon = (JLabel)val; break;
      case "playBtn": playBtn = (JButton)val; break;
      case "clearAllBtn": clearAllBtn = (JButton)val; break;
      case "newGameBtn": newGameBtn = (JButton)val; break;      
      default:  ;
    }
  }
  
}
