package francescocristiano.dao;


import francescocristiano.entities.ElementoCatalogo;
import francescocristiano.exceptions.NotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;

import java.util.List;

public class ElementoCatalogoDAO {
    private EntityManagerFactory emf;

    public ElementoCatalogoDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public void aggiungiElementoCatalogo(ElementoCatalogo elemento) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(elemento);
        transaction.commit();
        em.close();
        System.out.println("Elemento aggiunto con successo!");
    }

    public List<ElementoCatalogo> trovaTuttiGliElementi() {
        EntityManager em = emf.createEntityManager();
        List<ElementoCatalogo> elementiTrovati = em.createQuery("SELECT e FROM ElementoCatalogo e", ElementoCatalogo.class)
                .getResultList();
        em.close();
        return elementiTrovati;
    }

    public void rimuoviElementoCatalogoByISBN(String ISBN) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        ElementoCatalogo elementoTrovato = em.find(ElementoCatalogo.class, ISBN);
        if (elementoTrovato == null) {
            throw new NotFoundException(ISBN);
        }
        em.remove(elementoTrovato);
        transaction.commit();
        em.close();

        System.out.println("Elemento con id " + ISBN + " eliminato con successo!");
    }

    public ElementoCatalogo cercaElementoCatalogoByISBN(String ISBN) {
        EntityManager em = emf.createEntityManager();
        ElementoCatalogo elementoTrovato = em.find(ElementoCatalogo.class, ISBN);
        if (elementoTrovato == null) {
            throw new NotFoundException(ISBN);
        }
        em.close();
        return elementoTrovato;
    }


    public List<ElementoCatalogo> cercaElementiPerAnnoPubblicazione(int annoPubblicazione) {
        EntityManager em = emf.createEntityManager();
        List<ElementoCatalogo> elementiTrovati = em.createQuery("SELECT e FROM ElementoCatalogo e WHERE e.annoPubblicazione = :annoPubblicazione", ElementoCatalogo.class)
                .setParameter("annoPubblicazione", annoPubblicazione)
                .getResultList();
        em.close();
        return elementiTrovati;
    }

    public List<ElementoCatalogo> cercaElementiPerTitolo(String titolo) {
        EntityManager em = emf.createEntityManager();
        List<ElementoCatalogo> elementiTrovati = em.createQuery("SELECT e FROM ElementoCatalogo e WHERE e.titolo LIKE CONCAT('%', :titolo, '%')", ElementoCatalogo.class)
                .setParameter("titolo", titolo)
                .getResultList();
        em.close();
        return elementiTrovati;
    }
}
