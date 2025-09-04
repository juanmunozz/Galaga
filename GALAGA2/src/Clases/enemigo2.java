package Clases;

import java.awt.Rectangle;
import java.awt.Image;
import javax.swing.ImageIcon;

// DISPARADOR
public class enemigo2 {
	
    // Entradas
    public int x, y;
    private panel panel;
    
    public int width = 60;
    public int height = 80;
    public int vida = 3;
    public boolean muerto = false;
    
    private Image sprite;
    public javax.swing.Timer ataqueTimer;
    
    public enemigo2(int startX, int startY, panel panel) {
        this.x = startX;
        this.y = startY;
        this.panel = panel;
        sprite = new ImageIcon("src/Sprites/enemigos/disparador.png").getImage();
        
        // Cada enemigo tiene su propio timer
        ataqueTimer = new javax.swing.Timer(2000, e -> ataque());
        ataqueTimer.start();
    }
    
    public void recibirGolpe() {
        vida--;
        if (vida <= 0) {
            if (ataqueTimer != null) {
                ataqueTimer.stop();
            }
            muerto = true;
        }
    }

    public void ataque() {
        final int[] disparos = {0};
        
        // Repetir timer hasta 2 disparos
        javax.swing.Timer timer = new javax.swing.Timer(200, e -> {
            if(muerto) { return; }
            sprite = new ImageIcon("src/Sprites/enemigos/atacando_disp.png").getImage();
            
            if(panel != null) {
                panel.agregarBala(this.x + width/2 - 11, this.y + height); 
            }
            
            disparos[0]++;
            if (disparos[0] >= 2) {
                ((javax.swing.Timer) e.getSource()).stop(); // finalizar el temporizador jaja me interesa
                sprite = new ImageIcon("src/Sprites/enemigos/disparador.png").getImage();
            }
        });
        
        timer.start();
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
    
    public Image getSprite() {
        return sprite;
    }
}
