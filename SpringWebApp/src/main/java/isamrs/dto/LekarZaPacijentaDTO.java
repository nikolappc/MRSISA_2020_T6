package isamrs.dto;

import java.util.ArrayList;
import java.util.Date;

import isamrs.domain.Lekar;

public class LekarZaPacijentaDTO {
	private int id;
	private String ime;
	private String prezime;
	private double prosjek;
	private ArrayList<String> listaVremena;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getIme() {
		return ime;
	}
	public void setIme(String ime) {
		this.ime = ime;
	}
	public String getPrezime() {
		return prezime;
	}
	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}
	public double getProsjek() {
		return prosjek;
	}
	public void setProsjek(double prosjek) {
		this.prosjek = prosjek;
	}
	public ArrayList<String> getListaVremena() {
		return listaVremena;
	}
	public void setListaVremena(ArrayList<String> listaVremena) {
		this.listaVremena = listaVremena;
	}
	public LekarZaPacijentaDTO(int id, String ime, String prezime, double prosjek, ArrayList<String> listaVremena) {
		super();
		this.id = id;
		this.ime = ime;
		this.prezime = prezime;
		this.prosjek = prosjek;
		this.listaVremena = listaVremena;
	}
	
	public LekarZaPacijentaDTO(){}
	
	public LekarZaPacijentaDTO(Lekar l) {
		this.id = l.getId();
		this.ime = l.getIme();
		this.prezime = l.getPrezime();
		this.prosjek = l.getProsjek();
	}
}
