package Clases;

import java.awt.Rectangle;
import java.awt.Image;
import javax.swing.ImageIcon;

public class balapj {
	
	public int x,y;
	public int width = 40;
	public int height = 80;
	private int dy = 10;
	
	private Image sprite;
	
	public balapj(int startX, int startY) {
		this.x = startX;
		this.y = startY;
		
		sprite = new ImageIcon("src/Sprites/bala.png").getImage();
	}
	
	public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
	
	public void move() {
		y -= dy;
	}
	
	public Image getSprite() {
        return sprite;
    }
	
}
