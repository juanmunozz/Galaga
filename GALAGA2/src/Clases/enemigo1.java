package Clases;

import java.awt.Rectangle;
import java.awt.Image;
import javax.swing.ImageIcon;

public class enemigo1 {
    
    // Entradas
    public int x, y;
    public int speed;
    public pj player;

    public int width = 50;
    public int height = 45;

    private boolean esperando = false;
    private boolean kamikaze = false;
    private Image sprite;
    public int vida = 1;

    // Movimiento
    private double dy, dx;
    int direction = 1; // 1 = derecha, -1 = izquierda
    public int altura_ataque = 400;

    // Zig-zag
    private boolean bajando = false;
    private int objetivoY;

    int oldSpeed;

    // Posicion
    private int posX;
    private int PlayerPosX;
    private int posY;
    private int PlayerPosY;

    private int distanciaY;
    private int distanciaX;

    private long attackStartTime = -1;

    public enemigo1(int startX, int startY, int speed, pj player, int direccion_inicial) {
        this.x = startX;
        this.y = startY;
        this.speed = speed;
        this.player = player;
        this.direction = direccion_inicial;
        this.objetivoY = y;
        sprite = new ImageIcon("src/Sprites/enemigos/kamikaze.png").getImage();
    }

    public void recibirGolpe() {
        vida--;
    }

    public void move(int panelWidth) {

        // Modo zig-zag (antes de kamikaze)
        if (!kamikaze) {
            if (!bajando) {
                // Movimiento horizontal
                x += speed * direction;

                // Cambio de dirección y bajar
                if (x <= 50) {
                    direction = 1;
                    bajando = true;
                    objetivoY = y + 50; // bajar 50 px
                }
                if (x >= panelWidth - width - 50) {
                    direction = -1;
                    bajando = true;
                    objetivoY = y + 50;
                }
            } else {
                // Bajar hasta objetivo
                y += speed;
                if (y >= objetivoY) {
                    bajando = false;
                }
            }
        }

        // Condición para activar ataque kamikaze
        if (!kamikaze && y >= altura_ataque &&
            x >= panelWidth/2 - 10 && x <= panelWidth/2 + 10) {

            if (!esperando) {
                esperando = true;
                int oldSpeed = speed;
                speed = 0;

                attackStartTime = System.currentTimeMillis();
                this.oldSpeed = oldSpeed;
            }

            // Después de 0.5s inicia kamikaze
            if (System.currentTimeMillis() - attackStartTime >= 500) {
                speed = oldSpeed;
                esperando = false;
                kamikaze = true;
                dx = 0;
                dy = 0;

                // Posiciones para calcular la distancia
                posX = x + width / 2;
                posY = y + height / 2;

                PlayerPosX = player.x + player.width / 2;
                PlayerPosY = player.y + player.height / 2;

                // Distancia para calcular dirección
                distanciaY = PlayerPosY - posY;
                distanciaX = PlayerPosX - posX;
            }
        }

        // Movimiento kamikaze
        if (kamikaze && !esperando) {
            if (dx == 0 && dy == 0) {
                double magnitud = Math.sqrt(distanciaX * distanciaX + distanciaY * distanciaY);

                dx = (distanciaX / magnitud) * Math.abs(speed);
                dy = (distanciaY / magnitud) * Math.abs(speed);
            }

            x += dx;
            y += dy;
        }
    }

    public boolean fueraDePantalla(int panelHeight) {
        return (y > panelHeight);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public Image getSprite() {
        return sprite;
    }
}
