package Clases;

import java.io.File;
import javax.sound.sampled.*;

public class musica {
    private static Clip clip;
    private static String rutaActual = null;

    public static void reproducirMusica(String rutaArchivo) {
        try {
            
             if (clip != null && clip.isRunning() && rutaArchivo.equals(rutaActual)) {
                 return;
             }

             // Si hay otra musica lo para
             if (clip != null) {
                 clip.stop();
                 clip.close();
             }

            AudioInputStream audioInput = AudioSystem.getAudioInputStream(new File(rutaArchivo));
            clip = AudioSystem.getClip();
            clip.open(audioInput);
            clip.loop(Clip.LOOP_CONTINUOUSLY); // Loop para que cuando termine vuelva
            clip.start();
            
            rutaActual = rutaArchivo;
        } catch (Exception e) {
            System.out.println("Error al reproducir música: " + e.getMessage());
        }
    }

}