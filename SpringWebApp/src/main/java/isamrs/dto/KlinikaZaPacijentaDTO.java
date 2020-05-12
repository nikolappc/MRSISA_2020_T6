package isamrs.dto;

import isamrs.domain.Klinika;
import isamrs.domain.Ocena;
import isamrs.domain.StavkaCenovnika;

public class KlinikaZaPacijentaDTO {
	private int id;
	private String naziv;
	private String tip;
	private String opis;
	private String adresa;
	private String grad;
	private String drzava;
	private double prosjek;
	private double cenaPregleda;
	
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
	
	public double getProsjek() {
		return prosjek;
	}
	public void setProsjek(double prosjek) {
		this.prosjek = prosjek;
	}
	public KlinikaZaPacijentaDTO(int id, String naziv, String tip, String opis, String adresa, String grad,
			String drzava, double prosjek, double cijenaPregleda) {
		super();
		this.id = id;
		this.naziv = naziv;
		this.tip = tip;
		this.opis = opis;
		this.adresa = adresa;
		this.grad = grad;
		this.drzava = drzava;
		this.prosjek = prosjek;
		this.cenaPregleda = cijenaPregleda;
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
