import java.awt.Image;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        int boardHeight = 600;
        int boardWidth = boardHeight;
        JFrame frame = new JFrame("SNAKE GAME");
        frame.setVisible(true);
        frame.setSize(boardHeight, boardWidth);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        SnakeGame snakeGame = new SnakeGame(boardHeight, boardWidth);
        
        
        frame.add(snakeGame);
        frame.pack();
        snakeGame.requestFocus();
    }
}