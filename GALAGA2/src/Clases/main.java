package Clases;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class main extends JFrame {
	
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private panel gamePanel;
    public static Font Fuente; // para acceder desde otras clases

    public main() {
        setTitle("Los wachos del espacio");

        // Pantalla completa
        setUndecorated(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        

        // Layout principal
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Crear menú
        JPanel menuPanel = crearMenu();

        // Crear juego
        gamePanel = new panel();

        // Agregar ambos al contenedor principal
        mainPanel.add(menuPanel, "Menu");
        mainPanel.add(gamePanel, "Juego");

        add(mainPanel);
        setVisible(true);
        
        
        musica.reproducirMusica("src/musica/menu.wav");
        
        
    }

    private JPanel crearMenu() {
        // Panel con fondo espacial negro
        JPanel menuPanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.BLACK);
                g.fillRect(0, 0, getWidth(), getHeight());

                // Estrellas random
                g.setColor(Color.WHITE);
                for (int i = 0; i < 150; i++) {
                    int x = (int)(Math.random() * getWidth());
                    int y = (int)(Math.random() * getHeight());
                    g.fillRect(x, y, 2, 2);
                }
            }
        };

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 0, 20, 0);
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        // Título espacial
        JLabel titulo = new JLabel("Viaje por el Sistema Solar");
        titulo.setFont(main.Fuente.deriveFont(100f));
        titulo.setForeground(new Color(0xFFD700)); // dorado
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 0;
        menuPanel.add(titulo, gbc);

        // Botón Jugar
        JButton startButton = crearBoton("Comenzar Misión", 400, 100);
        gbc.gridy = 1;
        menuPanel.add(startButton, gbc);
        

        // Botón Salir
        JButton exitButton = crearBoton("Abandonar Nave", 400, 100);
        gbc.gridy = 2;
        menuPanel.add(exitButton, gbc);

        // Eventos
        startButton.addActionListener(e -> {
            cardLayout.show(mainPanel, "Juego");
            panel panel = new panel();
			panel.requestFocus();
            panel.iniciarJuego();
            musica.reproducirMusica("src/musica/basico.wav");
        });
        exitButton.addActionListener(e -> System.exit(0));

        return menuPanel;
    }

    public static JButton crearBoton(String texto, int ancho, int alto) {
        JButton boton = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();

                // Transparencia según estado
                float alpha = 0.5f; 
                if (getModel().isPressed()) alpha = 1f;
                else if (getModel().isRollover()) alpha = 0.8f;

                // Fondo brillante azul espacial
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
                g2.setColor(new Color(30, 144, 255)); // azul cósmico
                if (getModel().isPressed()) {
                    g2.setColor(new Color(255, 215, 0)); // dorado al presionar
                }
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);

                g2.dispose();
                super.paintComponent(g);
            }
        };

        boton.setFont(main.Fuente.deriveFont(60f));
        boton.setForeground(Color.WHITE);
        boton.setOpaque(false);
        boton.setContentAreaFilled(false);  
        boton.setBorder(BorderFactory.createLineBorder(new Color(173, 216, 230), 4)); // celeste claro
        boton.setFocusPainted(false);
        boton.setPreferredSize(new Dimension(ancho, alto));
        return boton;

    }

    public static void main(String[] args) {
    	
 	
    	 try {
             File fuenteArchivo = new File("src/resources/fuente.ttf");

             // Crear la fuente
             Fuente = Font.createFont(Font.TRUETYPE_FONT, fuenteArchivo);
             GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
             ge.registerFont(Fuente);

             Fuente = Fuente.deriveFont(24f); // Tamaño default

         } catch (IOException | FontFormatException e) {
             e.printStackTrace();
         }
    	
        SwingUtilities.invokeLater(() -> new main());
        
        
        
    }
}
