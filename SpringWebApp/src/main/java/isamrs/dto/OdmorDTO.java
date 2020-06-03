package isamrs.dto;

import isamrs.domain.GodisnjiOdmor;
import isamrs.domain.MedicinskoOsoblje;

public class OdmorDTO {
	private GodisnjiOdmor odmor;
	private MedicinskoOsoblje mo;
	
	public OdmorDTO() {
		super();
	}
	
	
	public OdmorDTO(GodisnjiOdmor godisnjiOdmor, MedicinskoOsoblje medicinskoOsoblje) {
		odmor = godisnjiOdmor;
		mo = medicinskoOsoblje;
	}


	public GodisnjiOdmor getOdmor() {
		return odmor;
	}
	public void setOdmor(GodisnjiOdmor odmor) {
		this.odmor = odmor;
	}
	public MedicinskoOsoblje getMo() {
		return mo;
	}
	public void setMo(MedicinskoOsoblje mo) {
		this.mo = mo;
	}
	
	
	
	
}
