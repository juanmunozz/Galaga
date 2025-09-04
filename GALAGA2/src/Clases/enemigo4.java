package Clases;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.awt.Image;
import javax.swing.ImageIcon;

// TANQUES
public class enemigo4 {
    
    public int x, y;
    public int width = 90;
    public int height = 90;
    
    private double pasos = 10;
    public int vida = 8;
    private long lastMoveTime;
    
    private static final Image SPRITE = new ImageIcon("src/Sprites/enemigos/tanque.png").getImage();

    public enemigo4(int startX, int startY) {
        this.x = startX;
        this.y = startY;
        this.lastMoveTime = System.currentTimeMillis();
    }
    
    public void move() {
        long now = System.currentTimeMillis();
        if (now - lastMoveTime >= 2000) {
            y += pasos;
            lastMoveTime = now;
        }
    }
    
    public void recibirGolpe() {
        vida--;
    }
    
    public boolean fueraDePantalla(int panelHeight) {
        return (y > panelHeight);
    }
    
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
    
    public Image getSprite() {
        return SPRITE;
    }
}
