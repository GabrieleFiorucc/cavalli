import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

class Cavallo implements Runnable {
    private String nome;
    private int distanzaTotale;
    private int distanzaPercorsa = 0;
    private static final int DISTANZA_STEP_MIN = 1;  // Minimo avanzamento per step
    private static final int DISTANZA_STEP_MAX = 10; // Massimo avanzamento per step
    private static boolean garaTerminata = false;

    public Cavallo(String nome, int distanzaTotale) {
        this.nome = nome;
        this.distanzaTotale = distanzaTotale;
    }

    @Override
    public void run() {
        Random random = new Random();
        while (!garaTerminata && distanzaPercorsa < distanzaTotale) {
            int avanzamento = random.nextInt(DISTANZA_STEP_MAX - DISTANZA_STEP_MIN + 1) + DISTANZA_STEP_MIN;
            distanzaPercorsa += avanzamento;

            // Stampa lo stato attuale del cavallo
            System.out.println(nome + " ha percorso " + Math.min(distanzaPercorsa, distanzaTotale) + " metri.");

            // Controlla se il cavallo ha raggiunto o superato il traguardo
            if (distanzaPercorsa >= distanzaTotale) {
                garaTerminata = true;
                System.out.println(nome + " ha vinto la gara!");
            }

            try {
                Thread.sleep(500); // Pausa per simulare il tempo tra i passi
            } catch (InterruptedException e) {
                System.out.println(nome + " è stato interrotto.");
            }
        }
    }
}

public class GaraDiCavalli {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Inserisci la lunghezza del percorso
        System.out.print("Inserisci la lunghezza del percorso della gara (in metri): ");
        int distanzaGara = scanner.nextInt();
        scanner.nextLine(); // Consuma la linea successiva

        // Inserisci il numero di cavalli
        System.out.print("Inserisci il numero di cavalli partecipanti: ");
        int numeroCavalli = scanner.nextInt();
        scanner.nextLine();

        // Crea i cavalli
        List<Thread> cavalli = new ArrayList<>();
        for (int i = 1; i <= numeroCavalli; i++) {
            System.out.print("Inserisci il nome del cavallo " + i + ": ");
            String nomeCavallo = scanner.nextLine();
            Cavallo cavallo = new Cavallo(nomeCavallo, distanzaGara);
            Thread threadCavallo = new Thread(cavallo);
            cavalli.add(threadCavallo);
        }

        System.out.println("La gara sta per iniziare! Tutti i cavalli sono pronti...");

        // Avvia la gara
        for (Thread cavallo : cavalli) {
            cavallo.start();
        }

        // Attendi il termine di tutti i cavalli
        for (Thread cavallo : cavalli) {
            try {
                cavallo.join();
            } catch (InterruptedException e) {
                System.out.println("La gara è stata interrotta.");
            }
        }

        System.out.println("La gara è terminata!");
        scanner.close();
    }
}
