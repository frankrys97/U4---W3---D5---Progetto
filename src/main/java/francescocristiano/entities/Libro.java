package francescocristiano.entities;

import com.github.javafaker.Faker;
import jakarta.persistence.Entity;

import java.util.function.Supplier;

@Entity
public class Libro extends ElementoCatalogo {
    public static Supplier<Libro> randomicBook = () -> {
        Faker faker = new Faker();

        String ISBN = faker.code().isbn13();
        String titolo = faker.book().title();
        int annoPubblicazione = faker.number().numberBetween(1700, 2024);
        int numeroPagine = faker.number().numberBetween(1, 1000);
        /* String autore = faker.book().author();*/
        // Alla fine ha vinto la fantasia
        String autore = faker.harryPotter().character();
        String genere = faker.book().genre();
        return new Libro(ISBN, titolo, annoPubblicazione, numeroPagine, autore, genere);
    };
    private String autore;
    private String genere;

    public Libro() {
    }

    public Libro(String ISBN, String titolo, int annoPubblicazione, int numeroPagine, String autore, String genere) {
        super(ISBN, titolo, annoPubblicazione, numeroPagine);
        this.autore = autore;
        this.genere = genere;
    }

    public String getAutore() {
        return autore;
    }

    public void setAutore(String autore) {
        this.autore = autore;
    }

    public String getGenere() {
        return genere;
    }

    public void setGenere(String genere) {
        this.genere = genere;
    }


}
