package francescocristiano.dao;

import francescocristiano.entities.ElementoCatalogo;
import francescocristiano.entities.Utente;
import francescocristiano.exceptions.NotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;

import java.util.List;

public class UtenteDAO {

    private EntityManagerFactory emf;

    public UtenteDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public void aggiungiUtente(Utente utente) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(utente);
        transaction.commit();
        em.close();
        System.out.println("Utente aggiunto con successo!");
    }

    public Utente cercaUtenteByNumeroTessera(String numeroTessera) {
        EntityManager em = emf.createEntityManager();
        Utente utenteTrovato = em.find(Utente.class, numeroTessera);
        if (utenteTrovato == null) {
            throw new NotFoundException(numeroTessera);
        }
        em.close();
        return utenteTrovato;
    }

    public List<ElementoCatalogo> cercaElementiAttualmenteInPrestitoByNumeroTessera(String numeroTessera) {
        EntityManager em = emf.createEntityManager();
        List<ElementoCatalogo> elementiTrovati = em.createQuery("SELECT p.elementoPrestato FROM Prestito p WHERE p.utente.numeroTessera = :numeroTessera AND p.dataRestituzioneEffettiva IS NULL", ElementoCatalogo.class)
                .setParameter("numeroTessera", numeroTessera)
                .getResultList();
        em.close();
        return elementiTrovati;
    }


}
