package francescocristiano.exceptions;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String ISBN) {
        super("Il record con l'id " + ISBN + " non è stato trovato!");
    }

}
