package isamrs.dto;

import java.util.ArrayList;
import java.util.List;

import isamrs.domain.Lekar;
import isamrs.domain.Pregled;
import isamrs.domain.Sala;

public class SalaTerminiDTO {
	
	private Sala sala;
	private Lekar lekar;
	
	
	
	public SalaTerminiDTO() {
		super();
	}
	
	
	
	public SalaTerminiDTO(Sala sala, Lekar lekar) {
		super();
		this.sala = sala;
		this.lekar = lekar;
	}



	public Sala getSala() {
		return sala;
	}
	public void setSala(Sala sala) {
		this.sala = sala;
	}

	public Lekar getLekar() {
		return lekar;
	}

	public void setLekar(Lekar lekar) {
		this.lekar = lekar;
	}
	
	
	
	
}
