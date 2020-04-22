package isamrs.dto;

import java.util.ArrayList;

import isamrs.domain.Dijagnoza;
import isamrs.domain.Pregled;
import isamrs.domain.Recepti;
import isamrs.domain.Sala;
import isamrs.domain.Termin;
import isamrs.domain.TipPosete;

public class PregledDTO extends PosetaDTO {
	private ArrayList<ReceptiDTO> recepti;
	private ArrayList<DijagnozaDTO> dijagnoze;
	public ArrayList<ReceptiDTO> getRecepti() {
		return recepti;
	}
	public void setRecepti(ArrayList<ReceptiDTO> recepti) {
		this.recepti = recepti;
	}
	public ArrayList<DijagnozaDTO> getDijagnoze() {
		return dijagnoze;
	}
	public void setDijagnoze(ArrayList<DijagnozaDTO> dijagnoze) {
		this.dijagnoze = dijagnoze;
	}
	public PregledDTO(Integer id, String opis, Termin termin, TipPosete tip, Sala sala, ArrayList<ReceptiDTO> recepti,
			ArrayList<DijagnozaDTO> dijagnoze) {
		super(id, opis, termin, tip, sala);
		this.recepti = recepti;
		this.dijagnoze = dijagnoze;
	}
	
	public PregledDTO(Integer id, String opis, Termin termin, TipPosete tip, Sala sala) {
		super(id, opis, termin, tip, sala);
	}
	
	public PregledDTO() {}
	
	public PregledDTO(Pregled p) {
		this(p.getId(), p.getOpis(), p.getTermin(), p.getTipPosete(), p.getSala());
		this.recepti = new ArrayList<ReceptiDTO>();
		for (Recepti r : p.getRecepti()) {
			this.recepti.add(new ReceptiDTO(r));
		}
		this.dijagnoze = new ArrayList<DijagnozaDTO>();
		for (Dijagnoza d : p.getDijagnoza()) {
			this.dijagnoze.add(new DijagnozaDTO(d));
		}
	}
}
