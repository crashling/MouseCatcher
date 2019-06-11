/**
 * @author Ben Forgy at touro
 * 11/13/13
 */
package mousecatcher;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MouseCatcherGame extends JFrame {
    
    private PointList pointList;
    private Point mousePositon;
    private Point misslePosition;
    private int boardWidth;
    private int boardHeight;   
    private int trailLength;
    private int missleSize;
    private int missleSpeed;
    private Graphics graphics;
    private Graphics mouseEventGraphics;
    private boolean wasHit;
    
    // inner class
    private class MyMouseActionListener implements MouseMotionListener {
        @Override
        public void mouseDragged(MouseEvent me) {
        }
        @Override
        public void mouseMoved(MouseEvent me) {
            mouseEventGraphics.setColor(Color.DARK_GRAY);
            mouseEventGraphics.fillOval(mousePositon.x - 12, 
                    mousePositon.y - 6, 24, 12);
            mousePositon.x = me.getX();
            mousePositon.y = me.getY();
            if(!wasHit)
                mouseEventGraphics.setColor(Color.GRAY);
            mouseEventGraphics.fillOval(mousePositon.x - 12, 
                    mousePositon.y - 6, 24, 12);
        }
    }

    private void drawBoard() {
        JPanel panel = new JPanel();
        panel.setBounds(0, 0, boardWidth, boardHeight);
        panel.setBackground(Color.DARK_GRAY);
        this.add(panel);
        JLabel label = new JLabel("Avoid The Nuclear Mouse Seeking Missle!");
        panel.add(label);
        setSize(boardWidth, boardHeight);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        graphics = getGraphics();
        mouseEventGraphics = getGraphics();
    }
    
    private void drawDot(Color color, int indexInList){
        Point toDraw = pointList.get(indexInList);
        graphics.setColor(color);
        graphics.fillOval(toDraw.x, toDraw.y, missleSize, missleSize);
    }   
    private void drawMissle() {
        drawDot(Color.white, 0);
        drawDot(Color.white, 3);
    }
    private void drawFire(){
        drawDot(Color.magenta, 10);
        drawDot(Color.red, 11);
        drawDot(Color.yellow, 8);
        drawDot(Color.blue, 7);
    }
    private void drawTrail() {
        drawDot(Color.LIGHT_GRAY, 13);
    }   
    private void eraseTrail() {
        drawDot(Color.DARK_GRAY, trailLength - 1);
        drawDot(Color.LIGHT_GRAY, trailLength - 2);
    }
      
    private void paintExplosion() throws InterruptedException {
        Point toExplode = misslePosition;
        drawBoard();
        for(int i = 0; i < 100; i++)
            for(int j = 0; j < 2; j++){
            Thread.sleep(1);
            graphics.setColor(Color.red);
            graphics.fillOval(toExplode.x - i/2, toExplode.y - i/2, i, i);
            Thread.sleep(1);
            graphics.setColor(Color.yellow);
            graphics.fillOval(toExplode.x - i/2, toExplode.y - i/2, i, i);
            }
    }
 
    public MouseCatcherGame() {
        boardWidth = 1200;
        boardHeight = 700;
        missleSize = 10;
        trailLength = 30;
        missleSpeed = 4;
        misslePosition = new Point();
        mousePositon = new Point();
    }
    public void start() {
        pointList = new PointList(trailLength);
        misslePosition.x = boardWidth/2;
        misslePosition.y = boardHeight;
        addMouseMotionListener(new MyMouseActionListener());
        drawBoard();
        playGame();
        drawBoard();
    }
    
    private void playGame() {
        wasHit = false;
        int iterator = trailLength;
        while(!wasHit) {
            
            if(iterator == 0)
                eraseTrail();
            else if (iterator > 0)
                iterator--;
            
            pointList.add(misslePosition);
            drawTrail();
            drawFire();
            drawMissle();
            if(mousePositon.x == misslePosition.x && 
                    mousePositon.y == misslePosition.y)
                wasHit = true;
            if(mousePositon.x > misslePosition.x){
                misslePosition.x++;
            }
            if(mousePositon.y > misslePosition.y){
                misslePosition.y++;
            }
            if(mousePositon.x < misslePosition.x){
                misslePosition.x--;
            }
            if(mousePositon.y < misslePosition.y){
                misslePosition.y--;
            }
            try {
                Thread.sleep(missleSpeed);
            } catch (InterruptedException ex) {
                System.out.println(ex);}
        }
        try {
            paintExplosion();
        } catch (InterruptedException ex) { }
    }
}
