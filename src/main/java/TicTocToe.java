import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TicTocToe extends JFrame implements ActionListener {
    private JLabel turnLabel, scoreLabel, resultLabel;
    private JButton [][] board;
    private boolean isPlayerOne;
    private JDialog resultDialog;
    private int xScore, oScore, moveCounter;
    public TicTocToe(){
        super("Tic Toc Toe (java swing)");
        setSize(CommonConstants.FRAME_SIZE);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(null);
        getContentPane().setBackground(CommonConstants.background_color);

//        init vars
        board=  new JButton[3][3];
        createResultDialog();
//        player X starts first
        isPlayerOne= true;


        addGuiComponement();
    }


    private void addGuiComponement(){

//  bar label
        JLabel barLabel = new JLabel();
        barLabel.setOpaque(true);
        barLabel.setBackground(CommonConstants.bar_color);
        barLabel.setBounds(0,0,CommonConstants.FRAME_SIZE.width, 25);

//        turn label
        turnLabel = new JLabel(CommonConstants.x_label);
        turnLabel.setHorizontalAlignment(SwingConstants.CENTER);
        turnLabel.setFont(new Font("Dialog",Font.PLAIN,40));
        turnLabel.setPreferredSize(new Dimension(100,turnLabel.getPreferredSize().height));
        turnLabel.setOpaque(true);
        turnLabel.setBackground(CommonConstants.x_color);
        turnLabel.setForeground(CommonConstants.board_color);
        turnLabel.setBounds((CommonConstants.FRAME_SIZE.width - turnLabel.getPreferredSize().width)/2,0,
                turnLabel.getPreferredSize().width,
                turnLabel.getPreferredSize().height
        );

//        Score label
        scoreLabel = new JLabel(CommonConstants.score_label);
        scoreLabel.setFont(new Font("Dialog",Font.PLAIN,40 ));
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        scoreLabel.setForeground(CommonConstants.board_color);
        scoreLabel.setBounds(0,
                turnLabel.getY()+turnLabel.getPreferredSize().height+25,
                CommonConstants.FRAME_SIZE.width,
                scoreLabel.getPreferredSize().height
        );

//      game board
        GridLayout gridLayout = new GridLayout(3,3);
        JPanel boardpanel = new JPanel(gridLayout);
        boardpanel.setBounds(0,
                scoreLabel.getY() +scoreLabel.getPreferredSize().height+35,
                CommonConstants.BOARD_SIZE.width,
                CommonConstants.BOARD_SIZE.height);

//        create board
        for (int i=0; i<board.length; i++){
            for (int j=0; j<board[i].length; j++){
                JButton button = new JButton();
                button.setFont(new Font("Dialog", Font.PLAIN,180));
                button.setPreferredSize(CommonConstants.buttonSize);
                button.setBackground(CommonConstants.background_color);
                button.addActionListener(this);
                button.setBorder(BorderFactory.createLineBorder(CommonConstants.board_color));

//               add button to board
                board[i][j] = button;
                boardpanel.add(board[i][j]);
            }

        }

//      reset button
        JButton resetButton= new JButton("Reset");
        resetButton.setFont(new Font("Dialog",Font.PLAIN, 24));
        resetButton.addActionListener(this);
        resetButton.setBackground(CommonConstants.board_color);
        resetButton.setBounds(
               ( CommonConstants.FRAME_SIZE.width - resetButton.getPreferredSize().width)/2,
                CommonConstants.FRAME_SIZE.height-100,
                resetButton.getPreferredSize().width,
                resetButton.getPreferredSize().height
        );


        getContentPane().add(turnLabel);
        getContentPane().add(barLabel);
        getContentPane().add(scoreLabel);
        getContentPane().add(boardpanel);
        getContentPane().add(resetButton);

    }
    private void createResultDialog(){
        resultDialog = new JDialog();
        resultDialog.getContentPane().setBackground(CommonConstants.background_color);
        resultDialog.setResizable(false);
        resultDialog.setTitle("Result");
        resultDialog.setSize(CommonConstants.result_dialog_size);
        resultDialog.setLocationRelativeTo(this);
        resultDialog.setModal(true);
        resultDialog.setLayout(new GridLayout(2,1));
        resultDialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                resetGame();
            }
        });
//        reset label
        resultLabel = new JLabel();
        resultLabel.setFont(new Font("Dialog", Font.BOLD, 18));
        resultLabel.setForeground(CommonConstants.board_color);
        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);

//    reset button
        JButton restartButton = new JButton("Play Again");
        restartButton.setBackground(CommonConstants.board_color);
        restartButton.addActionListener(this);
        resultDialog.add(resultLabel);
        resultDialog.add(restartButton);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if(command.equals("Reset") || command.equals("Play Again")){
//            reset the game
            resetGame();
//            only reset the score when pressing reset
            if (command.equals("Reset"))
                xScore = oScore = 0;
            if(command.equals("Play Again"))
                resultDialog.setVisible(false);

        }else {
            //player move
            JButton button = (JButton) e.getSource();
            if (button.getText().equals("")){
                moveCounter++;
                // mark the button with x/o only if it hasn't been selected yet
                if(isPlayerOne){
                //player one (x player)
                    button.setText(CommonConstants.x_label);
                    button.setForeground(CommonConstants.x_color);
//                    update turn label
                    turnLabel.setText(CommonConstants.o_label);
                    turnLabel.setBackground(CommonConstants.o_color);

//                    update turn
                    isPlayerOne = false;
                }else {
//                    player Two( 0 player)
                    button.setText(CommonConstants.o_label);
                    button.setForeground(CommonConstants.o_color);
//                    update turn label
                    turnLabel.setText(CommonConstants.x_label);
                    turnLabel.setBackground(CommonConstants.x_color);
//                    update turn
                    isPlayerOne = true;
                }
//                check win conditions
                if (isPlayerOne){
//                    check to see if the last move From O was the winning move
                    checkOWin();
                }else{
//                    check to see if the last move From O was the winning move
                    checkXWin();
                }
//                check for draw conditions
                checkDraw();
//                update score label
                scoreLabel.setText("x : "+xScore +" | O : "+oScore);
            }
            repaint();
            revalidate();
        }
    }

    private void checkXWin(){
        String result = "X win";

//        check rows
        for (int row=0; row<board.length;row++){
            if (board[row][0].getText().equals("X") && board[row ][1].getText().equals("X") && board[row][2].getText().equals("X")){
                resultLabel.setText(result);
//                display result  Dialog
                resultDialog.setVisible(true);
//                update Score
                xScore++;
            }
        }

//        check columns
        for (int col = 0; col <board.length; col++){
            if (board[0][col].getText().equals("X") && board[1][col].getText().equals("X") && board[2][col].getText().equals("X")){
                resultLabel.setText(result);
//                display result  Dialog
                resultDialog.setVisible(true);
//                update Score
                xScore++;
            }
        }

//       check diagonals
        if (board[0][0].getText().equals("X") && board[1][1].getText().equals("X") && board[2][2].getText().equals("X")){
            resultLabel.setText(result);
//                display result  Dialog
            resultDialog.setVisible(true);
//                update Score
            xScore++;
        }
        if (board[0][2].getText().equals("X") && board[1][1].getText().equals("X") && board[2][0].getText().equals("X")){
            resultLabel.setText(result);
//                display result  Dialog
            resultDialog.setVisible(true);
//                update Score
            xScore++;
        }
    }

    private void checkOWin(){
        String result = "O win";

//        check rows
        for (int row=0; row<board.length;row++){
            if (board[row][0].getText().equals("O") && board[row ][1].getText().equals("O") && board[row][2].getText().equals("O")){
                resultLabel.setText(result);
//                display result  Dialog
                resultDialog.setVisible(true);
//                update Score
                oScore++;
            }
        }

//        check columns
        for (int col = 0; col <board.length; col++){
            if (board[0][col].getText().equals("O") && board[1][col].getText().equals("O") && board[2][col].getText().equals("O")){
                resultLabel.setText(result);
//                display result  Dialog
                resultDialog.setVisible(true);
//                update Score
                oScore++;
            }
        }

//       check diagonals
        if (board[0][0].getText().equals("O") && board[1][1].getText().equals("O") && board[2][2].getText().equals("O")){
            resultLabel.setText(result);
//                display result  Dialog
            resultDialog.setVisible(true);
//                update Score
            xScore++;
        }if (board[0][2].getText().equals("O") && board[1][1].getText().equals("O") && board[2][0].getText().equals("O")){
            resultLabel.setText(result);
//                display result  Dialog
            resultDialog.setVisible(true);
//                update Score
            oScore++;
        }

    }

    private void checkDraw(){
//        if there total of 9 moves and no player has won
        if (moveCounter >=9){
           resultLabel.setText("Draw !");
           resultDialog.setVisible(true);
        }
    }
    private void resetGame(){
//        reste player back to X_player
        isPlayerOne= true;
        turnLabel.setText(CommonConstants.x_label);
        turnLabel.setBackground(CommonConstants.x_color);
//      reset score
        scoreLabel.setText(CommonConstants.score_label);
//     reset counter
        moveCounter=0;
//     reset board
        for(int i=0; i<board.length; i++){
            for(int j=0; j<board[i].length; j++){
                board[i][j].setText("");
            }
        }
    }


}
