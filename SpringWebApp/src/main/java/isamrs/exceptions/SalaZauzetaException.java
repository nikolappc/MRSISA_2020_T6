package isamrs.exceptions;

public class SalaZauzetaException extends Error {
    public SalaZauzetaException(String naslov, String opis) {
        super(naslov, opis, 400);
    }


}
