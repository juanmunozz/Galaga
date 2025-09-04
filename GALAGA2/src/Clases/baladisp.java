package Clases;

import java.awt.Rectangle;
import java.awt.Image;
import javax.swing.ImageIcon;

public class baladisp {
	
	public int x,y;
	public int dimension = 44;
	private int dy = 8; // Velocidad
	
	private Image sprite;
	
	public baladisp(int startX, int startY) {
		this.x = startX;
		this.y = startY;
		
		sprite = new ImageIcon("src/Sprites/enemigos/bala.png").getImage();
	}
	
	public Rectangle getBounds() {
        return new Rectangle(x, y, dimension, dimension);
    }
	
	public void move() {
		y += dy;
	}
	
	public boolean fueraDePantalla(int panelHeight) {
	    return (y > panelHeight);
	}
	
	public Image getSprite() {
        return sprite;
    }
	
}
