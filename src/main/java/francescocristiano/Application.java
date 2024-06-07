package francescocristiano;

import francescocristiano.entities.Catalogo;
import jakarta.persistence.EntityManagerFactory;

import static jakarta.persistence.Persistence.createEntityManagerFactory;

public class Application {

    private static final EntityManagerFactory emf = createEntityManagerFactory("u4w3d5");

    public static void main(String[] args) {
        Catalogo catalogo = new Catalogo(emf);
        catalogo.startApp();
    }
}
