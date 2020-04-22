package isamrs.dto;

import isamrs.domain.ZdravstveniKarton;

public class ZdravstveniKartonDTO {
	private Integer id;
	private Double visina;
	private Double tezina;
	private String krvnaGrupa;
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
	public ZdravstveniKartonDTO(Integer id, Double visina, Double tezina, String krvnaGrupa) {
		super();
		this.id = id;
		this.visina = visina;
		this.tezina = tezina;
		this.krvnaGrupa = krvnaGrupa;
	}
	
	public ZdravstveniKartonDTO() {}
	
	public ZdravstveniKartonDTO(ZdravstveniKarton zk) {
		this(zk.getId(), zk.getVisina(), zk.getTezina(), zk.getKrvnaGrupa());
	}
}
