package isamrs.dto;

import isamrs.domain.Adresa;

public class AdresaDTO {
	private String adresa;
	private String grad;
	private String drzava;
	public String getAdresa() {
		return adresa;
	}
	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}
	public String getGrad() {
		return grad;
	}
	public void setGrad(String grad) {
		this.grad = grad;
	}
	public String getDrzava() {
		return drzava;
	}
	public void setDrzava(String drzava) {
		this.drzava = drzava;
	}
	public AdresaDTO(String adresa, String grad, String drzava) {
		super();
		this.adresa = adresa;
		this.grad = grad;
		this.drzava = drzava;
	}
	
	public AdresaDTO() {}
}
