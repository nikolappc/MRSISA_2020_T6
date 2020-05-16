package isamrs.dto;

import isamrs.domain.Tip;
import isamrs.domain.TipPosete;

public class TipPoseteDTO {
	private Integer id;
	private Tip tip;
	private String naziv;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Tip getTip() {
		return tip;
	}
	public void setTip(Tip tip) {
		this.tip = tip;
	}
	public TipPoseteDTO(Integer id, Tip tip) {
		super();
		this.id = id;
		this.tip = tip;
	}
	
	public TipPoseteDTO(int id, Tip tip, String naziv) {
		super();
		this.id = id;
		this.tip = tip;
		this.naziv = naziv;
	}
	
	public TipPoseteDTO(TipPosete tp) {
		this(tp.getId(), tp.getTip(), tp.getNaziv());
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
}
