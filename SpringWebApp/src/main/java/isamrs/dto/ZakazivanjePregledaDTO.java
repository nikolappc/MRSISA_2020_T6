package isamrs.dto;

import java.util.Date;

public class ZakazivanjePregledaDTO {
	private int idKlinike;
	private int idLekara;
	private int idPacijenta;
	private String nazivTipa;
	private Date terminPocetak;
	private int idPredefinisanogTermina;
	
	
	
	public int getIdPredefinisanogTermina() {
		return idPredefinisanogTermina;
	}
	public void setIdPredefinisanogTermina(int idPredefinisanogTermina) {
		this.idPredefinisanogTermina = idPredefinisanogTermina;
	}
	public int getIdKlinike() {
		return idKlinike;
	}
	public void setIdKlinike(int idKlinike) {
		this.idKlinike = idKlinike;
	}
	public int getIdLekara() {
		return idLekara;
	}
	public void setIdLekara(int idLekara) {
		this.idLekara = idLekara;
	}
	public int getIdPacijenta() {
		return idPacijenta;
	}
	public void setIdPacijenta(int idPacijenta) {
		this.idPacijenta = idPacijenta;
	}
	public String getNazivTipa() {
		return nazivTipa;
	}
	public void setNazivTipa(String nazivTipa) {
		this.nazivTipa = nazivTipa;
	}
	public Date getTerminPocetak() {
		return terminPocetak;
	}
	public void setTerminPocetak(Date terminPocetak) {
		this.terminPocetak = terminPocetak;
	}
	public ZakazivanjePregledaDTO(int idKlinike, int idLekara, int idPacijenta, String nazivTipa, Date terminPocetak, int idPredefinisanogTermina) {
		super();
		this.idKlinike = idKlinike;
		this.idLekara = idLekara;
		this.idPacijenta = idPacijenta;
		this.nazivTipa = nazivTipa;
		this.terminPocetak = terminPocetak;
		this.idPredefinisanogTermina = idPredefinisanogTermina;
	}
	
	public ZakazivanjePregledaDTO() {}

}
