package isamrs.dto;

import java.util.ArrayList;

public class GrafDTO {
	private ArrayList<Podatak> podaci = new ArrayList<Podatak>();

	public GrafDTO() {
		super();
	}

	public GrafDTO(ArrayList<Podatak> podaci) {
		super();
		this.podaci = podaci;
	}

	public ArrayList<Podatak> getPodaci() {
		return podaci;
	}

	public void setPodaci(ArrayList<Podatak> podaci) {
		this.podaci = podaci;
	}
	
}
