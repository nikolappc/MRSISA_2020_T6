package isamrs.dto;

import isamrs.domain.Adresa;
import isamrs.domain.Klinika;
import isamrs.domain.Ocena;
import isamrs.domain.StavkaCenovnika;

public class KlinikaZaPacijentaDTO {
	private int id;
	private String naziv;
	private String tip;
	private String opis;
	private double prosjek;
	private double cenaPregleda;
	private Adresa adresa;
	
	public double getCenaPregleda() {
		return cenaPregleda;
	}
	public void setCenaPregleda(double cijenaPregleda) {
		this.cenaPregleda = cijenaPregleda;
	}

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
	
	public double getProsjek() {
		return prosjek;
	}
	public void setProsjek(double prosjek) {
		this.prosjek = prosjek;
	}

	public KlinikaZaPacijentaDTO(int id, String naziv, String tip, String opis, Adresa adresa, double cijenaPregleda) {
		super();
		this.id = id;
		this.naziv = naziv;
		this.tip = tip;
		this.opis = opis;
		this.adresa = adresa;
		this.prosjek = prosjek;
		this.cenaPregleda = cijenaPregleda;
	}
	
	public KlinikaZaPacijentaDTO() {}
	
	public KlinikaZaPacijentaDTO(Klinika k) {
		this.id = k.getId();
		this.naziv = k.getNaziv();
		this.tip = k.getTipKlinike().getTip();
		this.opis = k.getOpis();
		this.adresa = k.getAdresa();

		double sum = 0.0;
		int count = 0;
		for (Ocena o : k.getOcena())
		{
			sum += o.getVrednost();
			count += 1;
		}
		this.prosjek = sum/count;
	}
	
	public void postaviCijenuPregleda(Klinika k, String naziv) {
		for (StavkaCenovnika sc : k.getCenovnik().getStavkaCenovnika()) {
			if (sc.getTipPosete().getNaziv().equals(naziv)) {
				this.cenaPregleda = sc.getCena();
			}
		}


	}
}
