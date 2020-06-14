package isamrs.dto;

import isamrs.domain.GodisnjiOdmor;

public class GodisnjiOdmorContainerDTO {
	private GodisnjiOdmor odmor;
	private String razlog;
	public GodisnjiOdmor getOdmor() {
		return odmor;
	}
	public void setOdmor(GodisnjiOdmor odmor) {
		this.odmor = odmor;
	}
	public String getRazlog() {
		return razlog;
	}
	public void setRazlog(String razlog) {
		this.razlog = razlog;
	}

	public GodisnjiOdmorContainerDTO() {

	}


}


