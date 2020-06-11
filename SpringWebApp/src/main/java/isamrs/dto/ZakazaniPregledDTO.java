package isamrs.dto;

import java.text.SimpleDateFormat;

import isamrs.domain.Cenovnik;
import isamrs.domain.Klinika;
import isamrs.domain.Pregled;
import isamrs.domain.StavkaCenovnika;

public class ZakazaniPregledDTO {
	private int id;
	private String tip;
	private String datum;
	private String pocetak;
	private String kraj;
	private String lekar;
	private int sala;
	private String opis;
	private double cena;
	private String klinika;
	
	
	public double getCena() {
		return cena;
	}
	public void setCena(double cena) {
		this.cena = cena;
	}
	public String getKlinika() {
		return klinika;
	}
	public void setKlinika(String klinika) {
		this.klinika = klinika;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTip() {
		return tip;
	}
	public void setTip(String tip) {
		this.tip = tip;
	}
	public String getPocetak() {
		return pocetak;
	}
	public void setPocetak(String pocetak) {
		this.pocetak = pocetak;
	}
	public String getKraj() {
		return kraj;
	}
	public void setKraj(String kraj) {
		this.kraj = kraj;
	}
	public String getLekar() {
		return lekar;
	}
	public void setLekar(String lekar) {
		this.lekar = lekar;
	}
	public int getSala() {
		return sala;
	}
	public void setSala(int sala) {
		this.sala = sala;
	}
	public String getOpis() {
		return opis;
	}
	public void setOpis(String opis) {
		this.opis = opis;
	}
	
	public String getDatum() {
		return datum;
	}
	public void setDatum(String datum) {
		this.datum = datum;
	}
	public ZakazaniPregledDTO(int id, String klinika, double cena, String tip, String datum, String pocetak, String kraj, String lekar, int sala, String opis) {
		super();
		this.id = id;
		this.tip = tip;
		this.datum = datum;
		this.pocetak = pocetak;
		this.kraj = kraj;
		this.lekar = lekar;
		this.sala = sala;
		this.opis = opis;
		this.cena = cena;
		this.klinika = klinika;
	}
	
	public ZakazaniPregledDTO() {}
	
	public ZakazaniPregledDTO(Pregled p, Cenovnik c) {
		SimpleDateFormat sdfDatum = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdfVrijeme = new SimpleDateFormat("HH:mm");
		this.id = p.getId();
		this.tip = p.getTipPosete().getNaziv();
		this.datum = sdfDatum.format(p.getTermin().getPocetak());
		this.pocetak = sdfVrijeme.format(p.getTermin().getPocetak());
		this.kraj = sdfVrijeme.format(p.getTermin().getKraj());
		this.lekar = p.getLekar().getIme() + " " + p.getLekar().getPrezime();
		this.sala = p.getSala().getId();
		this.opis = p.getOpis();
		for (StavkaCenovnika sc : c.getStavkaCenovnika()) {
			if (sc.getTipPosete().getId() == p.getTipPosete().getId()) {
				this.cena = sc.getCena();
			}
		}
		this.klinika = p.getLekar().getKlinika().getNaziv();
	}

}
