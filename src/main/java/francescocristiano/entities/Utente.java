package francescocristiano.entities;

import com.github.javafaker.Faker;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.Date;
import java.util.List;
import java.util.function.Supplier;

@Entity
public class Utente {

    public static Supplier<Utente> randomicUser = () -> {
        Faker faker = new Faker();
        String nome = faker.name().firstName();
        String cognome = faker.name().lastName();
        Date dataDiNascita = faker.date().birthday();
        return new Utente(nome, cognome, dataDiNascita);
    };
    @Id
    private String numeroTessera;
    private String nome;
    private String cognome;
    private Date dataDiNascita;
    @OneToMany(mappedBy = "utente")
    private List<Prestito> prestiti;

    public Utente() {
    }

    public Utente(String nome, String cognome, Date dataDiNascita) {
        this.numeroTessera = createTessera(); // Ho preferito optare per questo per non far generare tutto
        // al database e provare con metodi diversi
        this.nome = nome;
        this.cognome = cognome;
        this.dataDiNascita = dataDiNascita;

    }

    public String createTessera() {
        Faker faker = new Faker();
        return faker.number().digits(7);
    }

    public String getNumeroTessera() {
        return numeroTessera;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public Date getDataDiNascita() {
        return dataDiNascita;
    }

    public void setDataDiNascita(Date dataDiNascita) {
        this.dataDiNascita = dataDiNascita;
    }

    @Override
    public String toString() {
        return "Utente{" +
                "numeroTessera='" + numeroTessera + '\'' +
                ", nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", dataDiNascita=" + dataDiNascita +
                '}';
    }
}
