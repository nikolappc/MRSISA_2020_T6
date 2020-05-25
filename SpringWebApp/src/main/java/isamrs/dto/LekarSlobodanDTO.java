package isamrs.dto;

import java.util.ArrayList;

import isamrs.domain.Klinika;
import isamrs.domain.StavkaCenovnika;

public class LekarSlobodanDTO {
	private ArrayList<String> listaVremena;
	private double cijenaTipaOpciono;

	public ArrayList<String> getListaVremena() {
		return listaVremena;
	}
	public void setListaVremena(ArrayList<String> listaVremena) {
		this.listaVremena = listaVremena;
	}
	public double getCijenaTipaOpciono() {
		return cijenaTipaOpciono;
	}
	public void setCijenaTipaOpciono(double cijenaTipaOpciono) {
		this.cijenaTipaOpciono = cijenaTipaOpciono;
	}
	public LekarSlobodanDTO(ArrayList<String> listaVremena, double cijenaTipaOpciono) {
		super();
		this.listaVremena = listaVremena;
		this.cijenaTipaOpciono = cijenaTipaOpciono;
	}
	
	public LekarSlobodanDTO() {}
	
	public void setCijena(String nazivTipa, Klinika k) {
		for (StavkaCenovnika sc : k.getCenovnik().getStavkaCenovnika()) {
			if (sc.getTipPosete().getNaziv().equals(nazivTipa)) {
				this.cijenaTipaOpciono = sc.getCena();
			}
		}
	}
}
