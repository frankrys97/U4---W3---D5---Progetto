package francescocristiano.entities;

import com.github.javafaker.Faker;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.time.LocalDate;
import java.util.List;

@Entity
public class Utente {

    @Id
    private String numeroTessera;
    private String nome;
    private String cognome;
    private LocalDate dataDiNascita;

    @OneToMany(mappedBy = "utente")
    private List<Prestito> prestiti;

    public Utente() {
    }

    public Utente(String nome, String cognome, LocalDate dataDiNascita) {
        this.numeroTessera = createTessera(); // Ho preferito optare per questo per non far generare tutto
        // al database e provare con metodi diversi
        this.nome = nome;
        this.cognome = cognome;
        this.dataDiNascita = dataDiNascita;

    }

    public String createTessera() {
        Faker faker = new Faker();
        String numeroTessera = faker.number().digits(7);
        return numeroTessera;
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

    public LocalDate getDataDiNascita() {
        return dataDiNascita;
    }

    public void setDataDiNascita(LocalDate dataDiNascita) {
        this.dataDiNascita = dataDiNascita;
    }
}
