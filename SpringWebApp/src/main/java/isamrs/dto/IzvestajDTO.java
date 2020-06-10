package isamrs.dto;

import java.util.ArrayList;

public class IzvestajDTO {
	private double prosekKlinike;
	private ArrayList<LekarOcena> oceneLekara = new ArrayList<LekarOcena>();
	public double getProsekKlinike() {
		return prosekKlinike;
	}
	public void setProsekKlinike(double prosekKlinike) {
		this.prosekKlinike = prosekKlinike;
	}
	public ArrayList<LekarOcena> getOceneLekara() {
		return oceneLekara;
	}
	public void setOceneLekara(ArrayList<LekarOcena> oceneLekara) {
		this.oceneLekara = oceneLekara;
	}
	public IzvestajDTO(double prosekKlinike, ArrayList<LekarOcena> oceneLekara) {
		super();
		this.prosekKlinike = prosekKlinike;
		this.oceneLekara = oceneLekara;
	}
	public IzvestajDTO() {
		super();
	}
	
	
}
