package francescocristiano;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Application {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("u4w3d5");

    public static void main(String[] args) {
        System.out.println("Hello World!");
    }
}
