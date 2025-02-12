import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class SnakeGame extends JPanel implements ActionListener, KeyListener {
    int boardHeight;
    int boardWidth;
    int tileSize = 25;
    // Snake
    Tile SnakeHead;
    ArrayList<Tile> snakeBody;
    // Food
    Tile food;
    Random random;
    ImageIcon images = new ImageIcon("foodIcon.jpg");
    Image image = images.getImage();
    // ImageIcon snakeImage = new ImageIcon("snake.jpg");
    // Image snkImage = snakeImage.getImage();
    // game logic
    Timer gameLoop;
    int velocityX;
    int velocityY;
    boolean gameOver = false;

    private class Tile {
        int x, y;

        Tile(int x, int y) {
            this.x = x;
            this.y = y;

        }
    }

    SnakeGame(int boardHeight, int boardWidth) {
        this.boardHeight = boardHeight;
        this.boardWidth = boardWidth;
        SnakeHead = new Tile(5, 5);
        setPreferredSize(new Dimension(this.boardHeight, this.boardWidth));
        setBackground(Color.black);
        addKeyListener(this);
        setFocusable(true);
        food = new Tile(10, 10);
        random = new Random();
        placeFood();
        velocityX = 0;
        velocityY = 1;
        gameLoop = new Timer(150, this);
        gameLoop.start();
        snakeBody = new ArrayList<Tile>();
    }

    public void placeFood() {
        food.x = random.nextInt(boardWidth / tileSize);
        food.y = random.nextInt(boardHeight / tileSize);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public boolean collision(Tile tile1, Tile tile2) {
        return tile1.x == tile2.x && tile1.y == tile2.y;
    }

    public void draw(Graphics g) {
        // Grid
        // for (int i = 0; i < boardHeight / tileSize; i++) {
        // g.drawLine(i * tileSize, 0, i * tileSize, boardHeight);
        // g.drawLine(0, i * tileSize, boardWidth, i * tileSize);
        // }
        // food
      
        if (image != null) {
            g.drawImage(image, food.x * tileSize, food.y * tileSize,tileSize,tileSize, this);
        }else{
            g.setColor(Color.red);
            g.fill3DRect(food.x * tileSize, food.y * tileSize, tileSize, tileSize, true);
        }
        // g.fillRect(food.x * tileSize, food.y * tileSize, tileSize, tileSize);
       
        // SnakeHead
        // if(snkImage !=null){
        //     g.drawImage(snkImage, SnakeHead.x * tileSize, SnakeHead.y * tileSize, tileSize, tileSize, this);
        // }else{
        g.setColor(Color.green);
        // g.fillRect(SnakeHead.x * tileSize, SnakeHead.y * tileSize, tileSize,
        // tileSize);
        g.fill3DRect(SnakeHead.x * tileSize, SnakeHead.y * tileSize, tileSize, tileSize, true);
    

        // Snake Body
        for (int i = 0; i < snakeBody.size(); i++) {
            Tile snakePart = snakeBody.get(i);
            // g.fillRect(snakePart.x * tileSize, snakePart.y * tileSize, tileSize,
            // tileSize);
            g.fill3DRect(snakePart.x * tileSize, snakePart.y * tileSize, tileSize, tileSize, true);

        }
        // Score
        g.setFont(new Font("Arial", Font.PLAIN, 16));
        if (gameOver) {
            g.setColor(Color.red);
            g.drawString("Game Over: " + String.valueOf(snakeBody.size()), tileSize - 16, tileSize);
        } else {
            g.drawString("Score: " + String.valueOf(snakeBody.size()), tileSize - 16, tileSize);
        }
    }

    public void move() {
        if (collision(SnakeHead, food)) {
            snakeBody.add(new Tile(food.x, food.y));
            placeFood();
        }
        for (int i = snakeBody.size() - 1; i >= 0; i--) {
            Tile snakePart = snakeBody.get(i);
            if (i == 0) {
                snakePart.x = SnakeHead.x;
                snakePart.y = SnakeHead.y;
            } else {
                Tile prevSnakePart = snakeBody.get(i - 1);
                snakePart.x = prevSnakePart.x;
                snakePart.y = prevSnakePart.y;
            }
        }
        // Snake head
        SnakeHead.x += velocityX;
        SnakeHead.y += velocityY;
        // game over condition
        for (int i = 0; i < snakeBody.size(); i++) {
            Tile snakePart = snakeBody.get(i);
            if (collision(SnakeHead, snakePart)) {
                gameOver = true;
            }
        }
        if (SnakeHead.x * tileSize < 0 || SnakeHead.x * tileSize > boardWidth
                || SnakeHead.y * tileSize < 0 || SnakeHead.y * tileSize > boardWidth) {
            gameOver = true;
        }
    }

    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if (gameOver) {
            gameLoop.stop();
        }
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP && velocityY != 1) {
            velocityX = 0;
            velocityY = -1;
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN && velocityY != -1) {
            velocityX = 0;
            velocityY = 1;
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT && velocityX != 1) {
            velocityX = -1;
            velocityY = 0;
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT && velocityX != -1) {
            velocityX = 1;
            velocityY = 0;
        }
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }

}
// velocity is change position over time
