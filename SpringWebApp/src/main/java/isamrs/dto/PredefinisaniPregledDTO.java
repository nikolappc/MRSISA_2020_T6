package isamrs.dto;

import java.text.SimpleDateFormat;

import isamrs.domain.Klinika;
import isamrs.domain.Pregled;
import isamrs.domain.StavkaCenovnika;

public class PredefinisaniPregledDTO {
	private int id;
	private int idSale;
	private double cena;
	private String nazivTipa;
	private String imeLjekara;
	private String prezimeLjekara;
	private String pocetak;
	private String kraj;
	private String datum;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIdSale() {
		return idSale;
	}
	public void setIdSale(int idSale) {
		this.idSale = idSale;
	}
	public double getCena() {
		return cena;
	}
	public void setCena(double cena) {
		this.cena = cena;
	}
	public String getImeLjekara() {
		return imeLjekara;
	}
	public void setImeLjekara(String imeLjekara) {
		this.imeLjekara = imeLjekara;
	}
	public String getPrezimeLjekara() {
		return prezimeLjekara;
	}
	public void setPrezimeLjekara(String prezimeLjekara) {
		this.prezimeLjekara = prezimeLjekara;
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
	public String getNazivTipa() {
		return nazivTipa;
	}
	public void setNazivTipa(String nazivTipa) {
		this.nazivTipa = nazivTipa;
	}
	public String getDatum() {
		return datum;
	}
	public void setDatum(String datum) {
		this.datum = datum;
	}
	public PredefinisaniPregledDTO(int id, int idSale, double cena, String nazivTipa, String imeLjekara, String prezimeLjekara,
			String pocetak, String kraj) {
		super();
		this.id = id;
		this.idSale = idSale;
		this.cena = cena;
		this.nazivTipa = nazivTipa;
		this.imeLjekara = imeLjekara;
		this.prezimeLjekara = prezimeLjekara;
		this.pocetak = pocetak;
		this.kraj = kraj;
	}
	
	public PredefinisaniPregledDTO() {}
	
	public PredefinisaniPregledDTO(Pregled p, Klinika k) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
		this.id = p.getId();
		this.idSale = p.getSala().getId();
		this.nazivTipa = p.getTipPosete().getNaziv();
		for (StavkaCenovnika sc : k.getCenovnik().getStavkaCenovnika()) {
			if (sc.getTipPosete().getId() == p.getTipPosete().getId()) {
				this.cena = sc.getCena();
			}
		}
		this.imeLjekara = p.getLekar().getIme();
		this.prezimeLjekara = p.getLekar().getPrezime();
		this.pocetak = sdf.format(p.getTermin().getPocetak());
		this.kraj = sdf.format(p.getTermin().getKraj());
		this.datum = sdf.format(p.getTermin().getPocetak());
	}
}
