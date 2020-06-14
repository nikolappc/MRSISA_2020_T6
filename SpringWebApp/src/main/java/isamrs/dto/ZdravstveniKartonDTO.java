package isamrs.dto;

import isamrs.domain.ZdravstveniKarton;

public class ZdravstveniKartonDTO {
	private Integer id;
	private Double visina;
	private Double tezina;
	private String krvnaGrupa;
	private String ime;
	private String prezime;
	private String email;

	public ZdravstveniKartonDTO(Integer id, Double visina, Double tezina, String krvnaGrupa) {
		this.id = id;
		this.visina = visina;
		this.tezina = tezina;
		this.krvnaGrupa = krvnaGrupa;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Double getVisina() {
		return visina;
	}
	public void setVisina(Double visina) {
		this.visina = visina;
	}
	public Double getTezina() {
		return tezina;
	}
	public void setTezina(Double tezina) {
		this.tezina = tezina;
	}
	public String getKrvnaGrupa() {
		return krvnaGrupa;
	}
	public void setKrvnaGrupa(String krvnaGrupa) {
		this.krvnaGrupa = krvnaGrupa;
	}
	public ZdravstveniKartonDTO(Integer id, Double visina, Double tezina, String krvnaGrupa, String ime, String prezime, String email) {
		super();
		this.id = id;
		this.visina = visina;
		this.tezina = tezina;
		this.krvnaGrupa = krvnaGrupa;
		this.ime = ime;
		this.prezime = prezime;
		this.email = email;
	}
	
	public ZdravstveniKartonDTO() {}
	
	public ZdravstveniKartonDTO(ZdravstveniKarton zk) {
		this(zk.getId(), zk.getVisina(), zk.getTezina(), zk.getKrvnaGrupa());
		if(zk.getPacijent()!=null){
			this.ime = zk.getPacijent().getIme();
			this.prezime = zk.getPacijent().getPrezime();
			this.email = zk.getPacijent().getEmail();
		}
	}
}
