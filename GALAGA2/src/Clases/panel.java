package Clases;


import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class panel extends JPanel implements ActionListener {
    
    private Timer timer;
    private Timer timerCuenta;
    
    private pj player;
    

    
    // Enemigos 
    private ArrayList<enemigo1> kamikazes = new ArrayList<>();
    private ArrayList<enemigo2> disparadores = new ArrayList<>();
    private ArrayList<enemigo3> comunes = new ArrayList<>();
    private ArrayList<enemigo4> tanques = new ArrayList<>();
    
    private boss boss;
    
    // Ataque disparador
    private ArrayList<baladisp> balasd = new ArrayList<>();
    
    // Balas jugador
    private ArrayList<balapj> balap = new ArrayList<>();
    
    // Labels
    private JLabel contador;
    private JLabel contadorP;
    private JLabel nivelTexto;
    
    // Label para mostrar mensajes de daño o game over
    private JLabel mensajeLabel;

    // Estadísticas
    private int puntosJug = 0;
    private int nivel = 10;

    public panel() {
        setBackground(Color.BLACK);
        setFocusable(true);
        
        //Labels
        contador = new JLabel("Puntos");
        contador.setForeground(Color.WHITE);
        contador.setFont(main.Fuente.deriveFont(40f));
        contador.setBounds(1750, 30, 250, 60);
        

        contadorP = new JLabel("" + puntosJug);
        contadorP.setForeground(Color.WHITE);
        contadorP.setBounds(1750, 30, 250, 60);
        contadorP.setFont(main.Fuente.deriveFont(40f));
        contadorP.setHorizontalAlignment(SwingConstants.CENTER);

        nivelTexto = new JLabel("Nivel " + nivel + " - " + nivelToCirculo(nivel));
        nivelTexto.setForeground(Color.WHITE);
        nivelTexto.setFont(main.Fuente.deriveFont(40f));
        nivelTexto.setBounds(50, 50, 600, 60);
        
        mensajeLabel = new JLabel("");
        mensajeLabel.setForeground(Color.RED);
        mensajeLabel.setBounds(800, 900, 250, 60);
        mensajeLabel.setFont(main.Fuente);
        mensajeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Instanciar Jugador
        player = new pj(370, 800, 35, 35, this, mensajeLabel);
        addKeyListener(player);
        
        nivelToEnemigos(nivel);
        

        
        this.setLayout(null);
        this.add(contador);
        this.add(contadorP);
        this.add(nivelTexto);
        this.add(mensajeLabel);
        
       
        
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        g.drawImage(
        		nivelToFondo(nivel),0,0,getWidth(),getHeight(),this);
        
        g.setColor(new Color(0,0,0,200));
        g.fillRect(0,0,getWidth(),getHeight());
     		
        
        // Renderizado del juego
        
        if(boss != null) {
        	g.drawImage(boss.getSprite(), boss.x - 3, boss.y - 3, boss.width + 6, boss.height + 6, this);
        	
        }
        
        for(enemigo1 kamikaze : kamikazes) {
            g.drawImage(kamikaze.getSprite(),
                        kamikaze.x - 23, kamikaze.y - 26,
                        kamikaze.width + 42, kamikaze.height + 42, this);
        }
        
        for(enemigo2 disparador : disparadores) {
            g.drawImage(disparador.getSprite(),
            		disparador.x - 5, disparador.y - 5,
            		disparador.width + 10, disparador.height + 10, this);
        }
        
        for(enemigo3 comun : comunes) {
            g.drawImage(comun.getSprite(),
                        comun.x - 3, comun.y - 3,
                        comun.width + 6, comun.height + 6, this);
        }
        
        for(enemigo4 tanque : tanques) {
            g.drawImage(tanque.getSprite(),
                        tanque.x - 3, tanque.y - 3,
                        tanque.width + 6, tanque.height + 6, this);
        }
        
        for(baladisp balas : balasd) {
            g.drawImage(balas.getSprite(),
                        balas.x, balas.y,
                        balas.dimension, balas.dimension, this);
        }
        
        for(balapj balas : balap) {
            g.drawImage(balas.getSprite(),
                        balas.x, balas.y,
                        balas.width, balas.height, this);
        }
        
        if (player.vida > 0) {
            g.drawImage(player.getSprite(),
                        player.x - 10, player.y - 40,
                        player.width + 20, player.height + 46, this);
        }
        
        if (player.vida <= 0) {
            g.setColor(Color.RED);
            g.setFont(g.getFont().deriveFont(50f));
            g.drawString("GAME OVER", getWidth() / 2 - 150, getHeight() / 2);
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (player.vida <= 0) {
            if (timer != null) {
                timer.stop();
            }
            
            return;
        }

        // Movimiento del jugador
        player.move(this.getWidth());
        
        // Movimiento de enemigos
        for(enemigo1 k : kamikazes) k.move(this.getWidth());
        for(enemigo3 c : comunes) c.move();
        for(enemigo4 t : tanques) t.move();
        
        // Daño de balas del jugador a enemigos
        for(int i = 0; i < balap.size(); i++) {
        	balapj bala = balap.get(i);
            boolean impactada = false;
            
            
            if(boss != null){
            	         	
                if(bala.getBounds().intersects(boss.getBounds())) {
                    boss.recibirGolpe();
                    balap.remove(i); impactada = true; break;
                }
            }
            if(impactada) { i--; continue; }
            
            
                     
            for(enemigo1 k : kamikazes) {
                if(bala.getBounds().intersects(k.getBounds())) {
                    k.recibirGolpe();
                    balap.remove(i); impactada = true; break;
                }
            }
            if(impactada) { i--; continue; }
            
            for(enemigo2 m : disparadores) {
                if(bala.getBounds().intersects(m.getBounds())) {
                    m.recibirGolpe();
                    balap.remove(i); impactada = true; break;
                }
            }
            if(impactada) { i--; continue; }
            
            for(enemigo3 c : comunes) {
                if(bala.getBounds().intersects(c.getBounds())) {
                    c.recibirGolpe();
                    balap.remove(i); impactada = true; break;
                }
            }
            if(impactada) { i--; continue; }
            
            for(enemigo4 t : tanques) {
                if(bala.getBounds().intersects(t.getBounds())) {
                    t.recibirGolpe();
                    balap.remove(i); impactada = true; break;
                }
            }
            if(impactada) { i--; continue; }
        }
        
        // Movimiento y eliminación de balas del jugador
        for(int i = 0; i < balap.size(); i++) {
        	balapj b = balap.get(i);
            b.move();
            if(b.y + b.height <= 0) { balap.remove(i); i--; }
        }
        
        // Movimiento y daño de balas del disparador
        for (int i = 0; i < balasd.size(); i++) {
        	baladisp b = balasd.get(i);
            b.move();

            if (b.fueraDePantalla(getHeight())) {
            	balasd.remove(i);
                i--;
                continue;
            }

            if (player != null && b.getBounds().intersects(player.getBounds())) {
                player.recibirGolpe();
                balasd.remove(i);
                i--;
            }
        }
        
        // Llamada a la función de colisiones del jugador
        detectarColisionesJugador();

        // Eliminación de enemigos
        for(int i=0; i<kamikazes.size(); i++) {
            enemigo1 k = kamikazes.get(i);
            if(k.fueraDePantalla(getHeight())) { kamikazes.remove(i); i--; }
            else if(k.vida <= 0) { kamikazes.remove(i); puntosJug += 2; i--; }
        }
        for(int i=0; i<disparadores.size(); i++) {
            enemigo2 m = disparadores.get(i);
            if(m.vida <= 0) { m.ataqueTimer.stop(); disparadores.remove(i); puntosJug += 3; i--; }
        }
        for(int i=0; i<comunes.size(); i++) {
            enemigo3 c = comunes.get(i);
            if(c.fueraDePantalla(getHeight())) { comunes.remove(i); i--; }
            else if(c.vida <= 0) { comunes.remove(i); puntosJug += 1; i--; }
        }
        for(int i=0; i<tanques.size(); i++) {
            enemigo4 t = tanques.get(i);
            if(t.fueraDePantalla(getHeight())) { tanques.remove(i); i--; }
            else if(t.vida <= 0) { tanques.remove(i); puntosJug += 4; i--; }
        }
        
        // Si no queda ninguno
        if(kamikazes.isEmpty() && disparadores.isEmpty() && comunes.isEmpty() && tanques.isEmpty() && boss == null) {
            SiguienteNivel();
        }
        
        contadorP.setText("" + puntosJug);
        repaint();
    }
    
    public void iniciarJuego() {
        if (timer != null && timer.isRunning()) {
            timer.stop();
        }
        timerCuenta = new javax.swing.Timer(500, e -> {
            if (timer == null) {
                timer = new Timer(10, this);
                requestFocusInWindow();
            }
            timer.start();
        });
        timerCuenta.setRepeats(false);
        timerCuenta.start();
    }

    private int contadorEspera = 0;

    public void SiguienteNivel() {
        nivel++;
        nivelTexto.setText("Nivel " + nivel + " - " + nivelToCirculo(nivel));
        
        timer.stop();
        balap.clear();
        balasd.clear();
        
        this.removeKeyListener(player);
        player = new pj(370, 800, 35, 35, this, mensajeLabel); // Posición inicial del jugador
        this.addKeyListener(player);
        this.requestFocus();
        
        nivelToEnemigos(nivel);
        
        contadorEspera = 1;
        timerCuenta = new Timer(1000, null);
        timerCuenta.addActionListener(e -> {
            if (contadorEspera > 0) {
                contadorEspera--;
                repaint();
            } else {
                timerCuenta.stop();
                timer.start();
            }
        });
        timerCuenta.start();
    }
    
    public void agregarBala(int x, int y) {
        if(timer != null && timer.isRunning()) {
        	balasd.add(new baladisp(x, y));
        }
    }
    
    public void dispararJugador(int x, int y) {
    	balap.add(new balapj(x, y));
    }

    // detección de colisiones del jugador con enemigos
    private void detectarColisionesJugador() {
        // Kamikazes
        for (int i = 0; i < kamikazes.size(); i++) {
            enemigo1 k = kamikazes.get(i);
            if (k.getBounds().intersects(player.getBounds())) {
                player.recibirGolpe();
                kamikazes.remove(i);
                i--;
            }
        }

        // Disparadores
        for (int i = 0; i < disparadores.size(); i++) {
            enemigo2 d = disparadores.get(i);
            if (d.getBounds().intersects(player.getBounds())) {
                player.recibirGolpe();
                disparadores.remove(i);
                i--;
            }
        }

        // Comunes
        for (int i = 0; i < comunes.size(); i++) {
            enemigo3 c = comunes.get(i);
            if (c.getBounds().intersects(player.getBounds())) {
                player.recibirGolpe();
                comunes.remove(i);
                i--;
            }
        }

        // Tanques
        for (int i = 0; i < tanques.size(); i++) {
            enemigo4 t = tanques.get(i);
            if (t.getBounds().intersects(player.getBounds())) {
                player.recibirGolpe();
                tanques.remove(i);
                i--;
            }
        }
    }
      
    private String nivelToCirculo(int nivel) {
        switch (nivel) {
            case 1: return "Tierra";
            case 2: return "Luna";
            case 3: return "Marte";
            case 4: return "Júpiter";
            case 5: return "Saturno";
            case 6: return "Urano";
            case 7: return "Neptuno";
            case 8: return "Mercurio";
            case 9: return "Venus";
            default: return "Sol";
        }
    }
    
    private Image nivelToFondo(int nivel) {
    	Image fondo;
    	
    	if(nivel == 1) {
    		fondo = new ImageIcon("src/Resources/tierra.png").getImage();
    	}else if(nivel == 2) {
    		fondo = new ImageIcon("src/Resources/luna.png").getImage();
    	}else if(nivel == 3) {
    		fondo = new ImageIcon("src/Resources/marte.png").getImage();
    	}else if(nivel == 4) {
    		fondo = new ImageIcon("src/Resources/jupiter.png").getImage();
    	}else if(nivel == 5) {
    		fondo = new ImageIcon("src/Resources/saturno.png").getImage();
    	}else if(nivel == 6) {
    		fondo = new ImageIcon("src/Resources/urano.png").getImage();
    	}else if(nivel == 7) {
    		fondo = new ImageIcon("src/Resources/neptuno.png").getImage();
    	}else if(nivel == 8) {
    		fondo = new ImageIcon("src/Resources/mercurio.png").getImage();
    	}else if(nivel == 9) {
    		fondo = new ImageIcon("src/Resources/venus.png").getImage();
    	}else{
    		fondo = new ImageIcon("src/Resources/sol.png").getImage();
    	}
    	
    	return fondo;
    	
    }

    private void nivelToEnemigos(int nivel) {
        int coordX;
        switch (nivel) {
        
            case 1: 
                // Solo comunes básicos
                for (int x = 400; x <= 1200; x += 200) {
                    comunes.add(new enemigo3(x, 200, 1750));
                }
                break;
            

            case 2: 
                // Comunes + algunos disparadores
                for (int x = 300; x <= 1500; x += 300) {
                    comunes.add(new enemigo3(x, 250, 1750));
                }
                disparadores.add(new enemigo2(700, 100, this));
                disparadores.add(new enemigo2(1200, 100, this));
                break;
            

            case 3: {
                // 3 filas de comunes + 5 disparadores
                int[] filas = {250, 350, 450};
                for (int y : filas) {
                    for (int x = 300; x <= 1500; x += 300) {
                        comunes.add(new enemigo3(x, y, 1750));
                    }
                }
                // 5 disparadores alineados
                for (int x = 400; x <= 1600; x += 300) {
                    disparadores.add(new enemigo2(x, 100, this));
                }
                break;
            
            }
            case 4: 
                // Kamikazes + comunes + disparadores
                int coordX4 = 200;
                for (int i = 0; i < 8; i++) {
                    kamikazes.add(new enemigo1(coordX4, 200, 8, player, 1));
                    coordX4 += 150;
                }
                
                for (int x = 400; x <= 1200; x += 200) {
                    comunes.add(new enemigo3(x, 250, 1750));
                }
                disparadores.add(new enemigo2(800, 150, this));
                disparadores.add(new enemigo2(1200, 150, this));
                break;
            

            case 5: 
                // Aparecen los tanques + soporte
                tanques.add(new enemigo4(700, 200));
                tanques.add(new enemigo4(1200, 200));
                for (int x = 400; x <= 1500; x += 300) {
                    comunes.add(new enemigo3(x, 350, 1750));
                }
                disparadores.add(new enemigo2(1000, 100, this));
                break;
            

            case 6: 
                // Tanques + kamikazes
                tanques.add(new enemigo4(600, 200));
                tanques.add(new enemigo4(1400, 200));
                int coordX6 = 200;
                for (int i = 0; i < 12; i++) {
                    kamikazes.add(new enemigo1(coordX6,200, 8, player, 1));
                    coordX6 += 120;
                }
                disparadores.add(new enemigo2(1000, 150, this));
                break;
            

            case 7: {
                // Pantalla casi llena: 4 filas comunes + 6 disparadores
                int[] filas = {250, 350, 450, 550};
                for (int y : filas) {
                    for (int x = 300; x <= 1500; x += 300) {
                        comunes.add(new enemigo3(x, y, 1750));
                    }
                }
                for (int x = 300; x <= 1500; x += 240) {
                    disparadores.add(new enemigo2(x, 100, this));
                }
                break;
        }

            case 8: 
                // Tanques + disparadores + kamikazes en enjambre
                tanques.add(new enemigo4(700, 200));
                tanques.add(new enemigo4(1200, 200));
                for (int x = 500; x <= 1400; x += 300) {
                    disparadores.add(new enemigo2(x, 150, this));
                }
                int coordX8 = 200;
                for (int i = 0; i < 15; i++) {
                    kamikazes.add(new enemigo1(coordX8, 200, 8, player, 1));
                    coordX8 += 100;
                }
                
                break; 
                
           
            case 9: 
                tanques.add(new enemigo4(600, 150));
                tanques.add(new enemigo4(1000, 150));
                tanques.add(new enemigo4(1400, 150));

                for (int x = 300; x <= 1500; x += 300) {
                    comunes.add(new enemigo3(x, 300, 1750));
                }
                for (int x = 400; x <= 1400; x += 250) {
                    disparadores.add(new enemigo2(x, 200, this));
                }

                int coordX9 = 200;
                for (int i = 0; i < 20; i++) {
                    kamikazes.add(new enemigo1(coordX9, 200, 8, player, 1));
                    coordX9 += 100;

                }
                break;
            
            case 10: 
            	
            	musica.reproducirMusica("src/musica/boss.wav");
            	
                // Boss final
                    
            	boss = new boss(650,45);
            	
                
                break;
            }
        }
    }
