import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;
import javax.sound.sampled.*;

public class Hilos {

    public static void main(String[] args) {
        // Create a new thread to play the song
        Thread songThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // Play the song
                    InputStream inputStream = new BufferedInputStream(new FileInputStream("Passionfruit.wav"));
                    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(inputStream);
                    AudioFormat audioFormat = audioInputStream.getFormat();
                    DataLine.Info info = new DataLine.Info(Clip.class, audioFormat);
                    Clip clip = (Clip) AudioSystem.getLine(info);
                    clip.open(audioInputStream);
                    clip.start();
                } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
                    e.printStackTrace();
                }
            }
        });

        // Create a new thread to do the survey
        Thread surveyThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    try (// Do the survey
                    Scanner scanner = new Scanner(System.in)) {
                        System.out.println("Como te llamas?");
                        String nombre = scanner.next();
                        System.out.println("Dime tu edad?");
                        int edad = scanner.nextInt();
                        System.out.println("Color favorito?");
                        String color = scanner.next();
                        System.out.println("Comida favorita?");
                        String comida = scanner.next();
                        System.out.println("Gracias por tu colaboracion, Acontinuacion veras tus datos ingresados");
                        System.out.println(nombre +" "+ edad +" "+ color +" "+ comida);
                    }
                    
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        // Start both threads
        songThread.start();
        surveyThread.start();

        // Wait for both threads to finish
        try {
            songThread.join();
            surveyThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // The program is now finished
    }
}