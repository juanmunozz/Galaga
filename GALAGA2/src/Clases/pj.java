package Clases;

import javax.swing.JLabel;
import javax.swing.Timer;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Image;
import javax.swing.ImageIcon;

public class pj implements KeyListener {
    public int x, y, width, height;
    public int dx = 0;
    public int speed = 8;
    public int vida = 3;
    private Image sprite;
    
    public boolean gameover = false;
    private boolean puedeDisparar = true;
    
    private boolean leftPressed = false;
    private boolean rightPressed = false;
    private boolean spacePressed = false;
    
    private panel panel;
    private JLabel mensajeLabel;  // referencia al label de mensajes

    public pj(int startX, int startY, int width, int height, panel panel, JLabel mensajeLabel) {
        this.x = startX;
        this.y = startY;
        this.width = 100;
        this.height = 80;
        this.panel = panel;
        this.mensajeLabel = mensajeLabel;

        sprite = new ImageIcon("src/Sprites/player.png").getImage();
    }
    
    public void recibirGolpe() {
        this.vida -= 1;
        // Ahora actualizamos la etiqueta con el mensaje
        if (this.vida > 0) {
            mensajeLabel.setText("¡Has recibido daño! Vida restante: " + this.vida);
        } else {
            this.gameover = true;
        }
    }

    // Movimiento y disparo
    public void move(int panelWidth) {
        dx = 0;
        if (leftPressed) dx = -speed;
        if (rightPressed) dx = speed;

        x += dx;
        if (x < 0) x = 0;
        if (x + width > panelWidth) x = panelWidth - width;

        if (spacePressed && puedeDisparar) {
            panel.dispararJugador(x + width/2 - 6, y - 15);
            puedeDisparar = false;

            Timer timer = new Timer(400, ev -> {
                puedeDisparar = true;
                ((Timer) ev.getSource()).stop();
            });
            timer.setRepeats(false);
            timer.start();
        }
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
    
    public Image getSprite() {
        return sprite;
    }

    // KeyListener
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) leftPressed = true;
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) rightPressed = true;
        if (e.getKeyCode() == KeyEvent.VK_SPACE) spacePressed = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) leftPressed = false;
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) rightPressed = false;
        if (e.getKeyCode() == KeyEvent.VK_SPACE) spacePressed = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {}
}