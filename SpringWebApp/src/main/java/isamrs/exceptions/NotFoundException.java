package isamrs.exceptions;

import javax.xml.ws.http.HTTPException;

public class NotFoundException extends Error{

	public NotFoundException(String naslov, String opis, Integer kod) {
		super(naslov, opis, kod);
	}

	public NotFoundException() {
		super();
	}

	public NotFoundException(String naslov, String opis) {
		super(naslov, opis, 404);
	}
}
