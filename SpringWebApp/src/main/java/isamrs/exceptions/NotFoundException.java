package isamrs.exceptions;

import javax.xml.ws.http.HTTPException;

public class NotFoundException extends HTTPException{

	public NotFoundException() {
		super(404);
	}

}
