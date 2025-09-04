package Clases;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.awt.Image;
import javax.swing.ImageIcon;

// COMÚN BAJADOR
public class enemigo3 {
    
    public int x, y;
    public int width = 60;
    public int height = 75;
    
    private int pasos = 20;
    public int vida = 1;
    public int delay;
    private long lastMoveTime;  // último movimiento en ms
    
    private static final Image SPRITE = new ImageIcon("src/Sprites/enemigos/comun.png").getImage();

    public enemigo3(int startX, int startY, int delay) {
        this.x = startX;
        this.y = startY;
        this.delay = delay;
        this.lastMoveTime = System.currentTimeMillis();
    }
    
    public void move() {
        long now = System.currentTimeMillis();
        if (now - lastMoveTime >= delay) {
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
