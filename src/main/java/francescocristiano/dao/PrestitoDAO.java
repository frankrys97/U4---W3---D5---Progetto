package francescocristiano.dao;

import francescocristiano.entities.Prestito;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;

import java.util.List;

public class PrestitoDAO {

    private EntityManagerFactory emf;

    public PrestitoDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public void aggiungiPrestito(Prestito prestito) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(prestito);
        transaction.commit();
        em.close();
        System.out.println("Prestito aggiunto con successo!");
    }

    public List<Prestito> cercaPrestitiScadutiENonRestituiti() {
        EntityManager em = emf.createEntityManager();
        List<Prestito> prestitiTrovati = em.createQuery("SELECT p FROM Prestito p WHERE p.dataRestituzionePrevista < CURRENT_DATE AND p.dataRestituzioneEffettiva IS NULL", Prestito.class)
                .getResultList();
        em.close();
        return prestitiTrovati;
    }
}
