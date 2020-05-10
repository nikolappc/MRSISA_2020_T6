package isamrs.dto;

import isamrs.domain.Adresa;
import isamrs.domain.Klinika;

public class KlinikaZaPacijentaDTO {
	private int id;
	private String naziv;
	private String tip;
	private String opis;
	private Adresa adresa;
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
	public Adresa getAdresa() {
		return adresa;
	}
	public void setAdresa(Adresa adresa) {
		this.adresa = adresa;
	}
	public KlinikaZaPacijentaDTO(int id, String naziv, String tip, String opis, Adresa adresa) {
		super();
		this.id = id;
		this.naziv = naziv;
		this.tip = tip;
		this.opis = opis;
		this.adresa = adresa;
	}
	
	public KlinikaZaPacijentaDTO() {}
	
	public KlinikaZaPacijentaDTO(Klinika k) {
		this.id = k.getId();
		this.naziv = k.getNaziv();
		this.tip = k.getTipKlinike().getTip();
		this.opis = k.getOpis();
		this.adresa = k.getAdresa();

	}
}
