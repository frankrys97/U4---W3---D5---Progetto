package francescocristiano.entities;

import com.github.javafaker.Faker;
import francescocristiano.dao.ElementoCatalogoDAO;
import francescocristiano.dao.LibroDAO;
import francescocristiano.dao.PrestitoDAO;
import francescocristiano.dao.UtenteDAO;
import francescocristiano.enums.Periodicità;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Catalogo {

    static Scanner scanner = new Scanner(System.in);
    private EntityManagerFactory emf;
    private ElementoCatalogoDAO elementoCatalogoDAO;
    private PrestitoDAO prestitoDAO;
    private LibroDAO libroDAO;
    private UtenteDAO utenteDAO;


    public Catalogo(EntityManagerFactory emf) {
        this.emf = emf;
        elementoCatalogoDAO = new ElementoCatalogoDAO(emf);
        prestitoDAO = new PrestitoDAO(emf);
        libroDAO = new LibroDAO(emf);
        utenteDAO = new UtenteDAO(emf);
    }

    public static LocalDate generateRandomDate() {
        // Con questa funzione genero una data casuale tra il 1 gennaio 1900 e la data di oggi
        long minDay = LocalDate.of(1900, 1, 1).toEpochDay();
        long maxDay = LocalDate.now().toEpochDay();
        long randomDay = minDay + ThreadLocalRandom.current().nextLong(maxDay - minDay);

        // alla fine converto il numero casuale di giorni in una data
        return LocalDate.ofEpochDay(randomDay);
    }

    public void startApp() {
        System.out.println("Benvenuto in Epibooks - JPA Edition!");

        inizializzaDataBase();

        System.out.println();
        System.out.println("Questa è la nostra libreria:");
        System.out.println();
        elementoCatalogoDAO.trovaTuttiGliElementi().forEach(System.out::println);

        System.out.println();

        while (true) {
            System.out.println("Scegli un'opzione:");
            System.out.println("1. Aggiungi elemento al catalogo");
            System.out.println("2. Rimuovi elemento dal catalogo");
            System.out.println("3. Cerca un elemento dal catalogo");
            System.out.println("4. Lista prestiti per utente");
            System.out.println("5. Ricerca tutti i prestiti scaduti e non ancora restituiti");
            System.out.println("6. Esci");
            System.out.println();

            try {
                int scelta = Integer.parseInt(scanner.nextLine());
                switch (scelta) {
                    case 1:
                        addElementChoose();
                        break;
                    case 2:
                        removeElementChoose();
                        break;
                    case 3:
                        searchElement();
                        break;
                    case 4:
                        searchAllLoans();
                        break;
                    case 5:
                        searchAllLoanExpired();
                        break;
                    case 6:
                        System.out.println("Arrivederci!");
                        resetDataBase();
                        return;
                }
            } catch (Exception e) {
                System.out.println("Scelta non valida");
            }
        }


    }

    public void searchAllLoanExpired() {
        System.out.println();
        System.out.println("Tutti i prestiti scaduti e non ancora restituiti:");
        prestitoDAO.cercaPrestitiScadutiENonRestituiti().forEach(System.out::println);
        System.out.println();
    }

    public void inizializzaDataBase() {

        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            elementoCatalogoDAO.aggiungiElementoCatalogo(Libro.randomicBook.get());
            elementoCatalogoDAO.aggiungiElementoCatalogo(Rivista.randomicMagazine.get());
            utenteDAO.aggiungiUtente(Utente.randomicUser.get());
        }

        // Una volta creati libri, riviste ed utenti passiamo alla creazione di dei prestiti randomici

        List<Utente> utenti = utenteDAO.trovaTuttiGliUtenti();
        List<ElementoCatalogo> elementi = elementoCatalogoDAO.trovaTuttiGliElementi();
        for (int i = 0; i < 5; i++) {
            prestitoDAO.aggiungiPrestito(new Prestito(utenti.get(random.nextInt(utenti.size())), elementi.get(random.nextInt(elementi.size())), generateRandomDate()));
        }
    }

    public void resetDataBase() {
        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.createNativeQuery("DROP TABLE elementocatalogo, prestito, utente, libro, rivista").executeUpdate();
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    // So bene che il reset del database non è previsto e pericoloso, ma essendo questa un'esercitazione didattica ho preferito
    // inserirlo in modo da rendere più agevole il testing del programma mentre scrivevo codice ed anche un'eventuale correzione

    private void addElementChoose() {
        System.out.println("1. Aggiungi manualmente");
        System.out.println("2. Genera randomicamente");
        int scelta = Integer.parseInt(scanner.nextLine());
        if (scelta == 1) {
            addManuallyElement();
        } else if (scelta == 2) {
            addRandomicElement();
        }
    }

    public void addManuallyElement() {
        System.out.println("Scegli un'opzione: ");
        System.out.println("1. Libro");
        System.out.println("2. Rivista");

        int scelta = Integer.parseInt(scanner.nextLine());
        if (scelta == 1) {
            Faker faker = new Faker();
            String ISBN = faker.code().isbn13();
            System.out.println("Inserisci titolo");
            String titolo = scanner.nextLine();
            System.out.println("Inserisci anno di pubblicazione");
            int annoPubblicazione = scanner.nextInt();
            scanner.nextLine();
            System.out.println("Inserisci numero di pagine");
            int numeroPagine = scanner.nextInt();
            scanner.nextLine();
            System.out.println("Inserisci autore");
            String autore = scanner.nextLine();
            System.out.println("Inserisci genere");
            String genere = scanner.nextLine();
            elementoCatalogoDAO.aggiungiElementoCatalogo(new Libro(ISBN, titolo, annoPubblicazione, numeroPagine, autore, genere));
            System.out.println("Libro aggiunto con successo");
            System.out.println();
            ;
        } else if (scelta == 2) {
            Faker faker = new Faker();
            String ISBN = faker.code().isbn13();
            System.out.println("Inserisci titolo");
            String titolo = scanner.nextLine();
            System.out.println("Inserisci anno di pubblicazione");
            int annoPubblicazione = Integer.parseInt(scanner.nextLine());
            scanner.nextLine();
            System.out.println("Inserisci numero di pagine");
            int numeroPagine = Integer.parseInt(scanner.nextLine());
            System.out.println("Inserisci periodicità ( SETTIMANALE, MENSILE, SEMESTRALE ):");
            Periodicità periodicità = Periodicità.valueOf(scanner.nextLine().toUpperCase());
            elementoCatalogoDAO.aggiungiElementoCatalogo(new Rivista(ISBN, titolo, annoPubblicazione, numeroPagine, periodicità));
            System.out.println("Rivista aggiunta con successo");
            System.out.println();
            ;
        }
    }

    public void addRandomicElement() {
        System.out.println("Scegli un'opzione: ");
        System.out.println("1. Libro");
        System.out.println("2. Rivista");

        int scelta = Integer.parseInt(scanner.nextLine());
        if (scelta == 1) {
            elementoCatalogoDAO.aggiungiElementoCatalogo(Libro.randomicBook.get());
            System.out.println("Libro aggiunto con successo");
        } else if (scelta == 2) {
            elementoCatalogoDAO.aggiungiElementoCatalogo(Rivista.randomicMagazine.get());
            System.out.println("Rivista aggiunto con successo");
        }
    }

    public void removeElementChoose() {
        System.out.println("Inserisci ISBN dell'elemento da rimuovere");
        String ISBN = scanner.nextLine();
        elementoCatalogoDAO.rimuoviElementoCatalogoByISBN(ISBN);
    }

    public void searchElement() {
        System.out.println("Scegli un'opzione: ");
        System.out.println("1. Cerca per ISBN");
        System.out.println("2. Cerca per anno di pubblicazione");
        System.out.println("3. Cerca per autore");

        int scelta = Integer.parseInt(scanner.nextLine());
        switch (scelta) {
            case 1:
                System.out.println("Inserisci ISBN");
                String ISBN = scanner.nextLine();
                elementoCatalogoDAO.cercaElementoCatalogoByISBN(ISBN);
                System.out.println();
                if (elementoCatalogoDAO.cercaElementoCatalogoByISBN(ISBN) == null) {
                    System.out.println("Elemento non trovato");
                } else {
                    System.out.println("Elemento trovato: ");
                    System.out.println(elementoCatalogoDAO.cercaElementoCatalogoByISBN(ISBN));
                }
                break;
            case 2:
                System.out.println("Inserisci anno di pubblicazione");
                int annoPubblicazione = Integer.parseInt(scanner.nextLine());
                elementoCatalogoDAO.cercaElementiPerAnnoPubblicazione(annoPubblicazione);
                System.out.println();
                if (elementoCatalogoDAO.cercaElementiPerAnnoPubblicazione(annoPubblicazione) == null) {
                    System.out.println("Elementi non trovati");
                } else {
                    elementoCatalogoDAO.cercaElementiPerAnnoPubblicazione(annoPubblicazione).forEach(System.out::println);
                    System.out.println();
                }
                break;
            case 3:
                System.out.println("Inserisci autore");
                String autore = scanner.nextLine();
                libroDAO.cercaLibriPerAutore(autore);
                System.out.println();
                if (libroDAO.cercaLibriPerAutore(autore) == null) {
                    System.out.println("Libri non trovati");
                } else {
                    libroDAO.cercaLibriPerAutore(autore).forEach(System.out::println);
                    System.out.println();
                }
                break;
            default:
                break;
        }
    }

    public void searchAllLoans() {
        System.out.println("Inserisci il numero tessera dell'utente per visualizzare i suoi prestiti attivi");
        String numeroTessera = scanner.nextLine();
        utenteDAO.cercaElementiAttualmenteInPrestitoByNumeroTessera(numeroTessera);
        if (utenteDAO.cercaElementiAttualmenteInPrestitoByNumeroTessera(numeroTessera) == null) {
            System.out.println();
            System.out.println("Nessun elemento in prestito");
            System.out.println();
        } else {
            System.out.println();
            System.out.println("Elementi in prestito: ");
            utenteDAO.cercaElementiAttualmenteInPrestitoByNumeroTessera(numeroTessera).forEach(System.out::println);
            System.out.println();
        }
    }

}
