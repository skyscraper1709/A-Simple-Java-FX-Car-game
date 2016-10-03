/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gamethread;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;

/**
 *
 * @author risulkarim
 */
public class GameObject implements Runnable {

    public JButton gameSprite;
   static boolean game=true;
    public GameObject(JButton gameSprite) {
        this.gameSprite = gameSprite;
    }

    private int getRandom(int a, int b) {
            return  new Random().nextInt((b - a) + 1) + a;
    }

    @Override
    public void run() {
        
        for (int i = 0; i < 270 && game; i++) {
            if(i>=269) GameThreadView.carsBeaten++;
            if (Thread.currentThread().getName().equals("leftCar")) {
               
                
                gameSprite.setBounds(gameSprite.getX() + getRandom(-5, 5), i, 50, 50);
                    
                if (i == 269 || checkCollision()) {
                    i = 0;
                    gameSprite.setBounds(50, 0, 50, 100);
                }
                try {
                    Thread.sleep(getRandom(20, 100));
                } catch (InterruptedException ex) {
                    Logger.getLogger(GameObject.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else if (Thread.currentThread().getName().equals("middleCar")) {
                for (int j = 0; j <5; j++) 
                gameSprite.setBounds(gameSprite.getX() + getRandom(-5, 5), i, 50, 50);
                if (i == 269 || checkCollision()) {
                    i = 0;
                    gameSprite.setBounds(300, i, 50, 100);
                }
                try {
                    Thread.sleep(getRandom(20, 100));
                } catch (InterruptedException ex) {
                    Logger.getLogger(GameObject.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if (Thread.currentThread().getName().equals("rightCar")) {
                for (int j = 0; j <5; j++) 
                gameSprite.setBounds(gameSprite.getX() + getRandom(-5, 5), i, 50, 50);
                if (i == 269 || checkCollision()) {
                    i = 0;
                    gameSprite.setBounds(550, i, 50, 100);
                }
                try {
                    Thread.sleep(getRandom(20, 100));
                } catch (InterruptedException ex) {
                    Logger.getLogger(GameObject.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            Thread.currentThread().setPriority(getRandom(1, 3));
            synchronize:
            {
                for (int j = 0; j < getRandom(0, 100); j++) {
                    Thread.currentThread().yield();
                    System.out.println(Thread.currentThread().getName()+"yielding..");
                }
            }
            Thread.currentThread().yield();
        }
    }

    private boolean checkCollision() {

        //DETECT COLLISION
        int enemyLow = gameSprite.getY() + gameSprite.getHeight();
        int carTop = GameThreadView.mCarY;

        int enemyLeft = gameSprite.getX();
        int enemyRight = gameSprite.getX() + gameSprite.getWidth();
        int carLeft = GameThreadView.mCarX;
        int carRight = carLeft + GameThreadView.mCarWidth;

        if (enemyLow > carTop && ((enemyLeft > carLeft && enemyLeft < carRight) || (enemyRight > carLeft && enemyRight < carRight))) {
            System.out.println("Collision detected");
            game=false;
            return true;
        }

        return false;

    }
}
