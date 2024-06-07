package francescocristiano.entities;

import jakarta.persistence.Entity;

@Entity
public abstract class ElementoCatalogo {
    private final String ISBN;
    private final String titolo;
    private final int annoPubblicazione;
    private final int numeroPagine;

    public ElementoCatalogo(String ISBN, String titolo, int annoPubblicazione, int numeroPagine) {
        this.ISBN = ISBN;
        this.titolo = titolo;
        this.annoPubblicazione = annoPubblicazione;
        this.numeroPagine = numeroPagine;
    }


    public String getISBN() {
        return ISBN;
    }

    public String getTitolo() {
        return titolo;
    }

    public int getAnnoPubblicazione() {
        return annoPubblicazione;
    }

    public int getNumeroPagine() {
        return numeroPagine;
    }

    public String toString() {
        return "ISBN: " + ISBN + ", Titolo: " + titolo + ", Anno di pubblicazione: " + annoPubblicazione + ", Numero pagine: " + numeroPagine;
    }
}
