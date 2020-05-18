package isamrs.dto;

import isamrs.domain.Lek;
import isamrs.domain.Recepti;

public class ReceptiDTO {
	private Integer id;
	private boolean overen;
	private Lek lek;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public boolean isOveren() {
		return overen;
	}
	public void setOveren(boolean overen) {
		this.overen = overen;
	}
	public Lek getLek() {
		return lek;
	}
	public void setLek(Lek lek) {
		this.lek = lek;
	}
	public ReceptiDTO(Integer id, boolean overen, Lek lek) {
		super();
		this.id = id;
		this.overen = overen;
		this.lek = lek;
	}
	
	public ReceptiDTO() {}
	
	public ReceptiDTO(Recepti r) {
		this(r.getId(), r.isOveren(), r.getLek());
	}
}
