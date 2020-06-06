package isamrs.exceptions;

public class LekarZauzetException extends Error {
    public LekarZauzetException(String naslov, String opis) {
        super(naslov, opis, 400);
    }
}
