package isamrs.dto;

import isamrs.domain.Klinika;

public class KlinikaZaPacijentaDTO {
	private int id;
	private String naziv;
	private String tip;
	private String opis;
	private String adresa;
	private String grad;
	private String drzava;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNaziv() {
		return naziv;
	}
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	public String getTip() {
		return tip;
	}
	public void setTip(String tip) {
		this.tip = tip;
	}
	public String getOpis() {
		return opis;
	}
	public void setOpis(String opis) {
		this.opis = opis;
	}
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
	public KlinikaZaPacijentaDTO(int id, String naziv, String tip, String opis, String adresa, String grad,
			String drzava) {
		super();
		this.id = id;
		this.naziv = naziv;
		this.tip = tip;
		this.opis = opis;
		this.adresa = adresa;
		this.grad = grad;
		this.drzava = drzava;
	}
	
	public KlinikaZaPacijentaDTO() {}
	
	public KlinikaZaPacijentaDTO(Klinika k) {
		this.id = k.getId();
		this.naziv = k.getNaziv();
		this.tip = k.getTipKlinike().getTip();
		this.opis = k.getOpis();
		this.adresa = k.getAdresa().split(", ")[0];
		this.grad = k.getAdresa().split(", ")[1];
		this.drzava = k.getAdresa().split(", ")[2];

	}
}
