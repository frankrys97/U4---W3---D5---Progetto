package francescocristiano.entities;

import com.github.javafaker.Faker;
import francescocristiano.enums.Periodicità;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.util.Random;
import java.util.function.Supplier;

@Entity
public class Rivista extends ElementoCatalogo {
    public static Supplier<Rivista> randomicMagazine = () -> {
        Faker faker = new Faker();
        String ISBN = faker.code().isbn13();
        String titolo = faker.book().title();
        int annoPubblicazione = faker.number().numberBetween(1700, 2024);
        int numeroPagine = faker.number().numberBetween(1, 1000);
        Periodicità periodicità = Periodicità.values()[new Random().nextInt(Periodicità.values().length)];
        return new Rivista(ISBN, titolo, annoPubblicazione, numeroPagine, periodicità);
    };
    @Enumerated(EnumType.STRING)
    private Periodicità periodicità;

    public Rivista() {
    }

    public Rivista(String ISBN, String titolo, int annoPubblicazione, int numeroPagine, Periodicità periodicità) {
        super(ISBN, titolo, annoPubblicazione, numeroPagine);
        this.periodicità = periodicità;
    }

    public Periodicità getPeriodicità() {
        return periodicità;
    }

    public void setPeriodicità(Periodicità periodicità) {
        this.periodicità = periodicità;
    }

    @Override
    public String toString() {
        return "Rivista: " + super.toString() + ", " +
                "periodicità=" + periodicità +
                '}';
    }
}
