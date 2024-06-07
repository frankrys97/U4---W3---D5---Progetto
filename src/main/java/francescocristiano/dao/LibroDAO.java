package francescocristiano.dao;

import francescocristiano.entities.Libro;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class LibroDAO {
    private EntityManagerFactory emf;

    public LibroDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public List<Libro> cercaLibriPerAutore(String autore) {
        EntityManager em = emf.createEntityManager();
        List<Libro> libriTrovati = em.createQuery("SELECT l FROM Libro l WHERE l.autore = :autore", Libro.class)
                .setParameter("autore", autore)
                .getResultList();
        em.close();
        return libriTrovati;
    }
}
