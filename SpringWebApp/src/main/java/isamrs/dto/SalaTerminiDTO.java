package isamrs.dto;

import java.util.ArrayList;
import isamrs.domain.Poseta;
import isamrs.domain.Sala;

public class SalaTerminiDTO {
	
	private Sala sala;
	private ArrayList<TerminDTO> posete;
	
	
	
	public SalaTerminiDTO() {
		super();
	}
	
	
	
	public SalaTerminiDTO(Sala sala, ArrayList<TerminDTO> posete) {
		super();
		this.sala = sala;
		this.posete = posete;
	}



	public Sala getSala() {
		return sala;
	}
	public void setSala(Sala sala) {
		this.sala = sala;
	}



	public ArrayList<TerminDTO> getPosete() {
		return posete;
	}



	public void setPosete(ArrayList<TerminDTO> posete) {
		this.posete = posete;
	}

	
	
	
	
}
