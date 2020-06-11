package isamrs.dto;

import java.util.ArrayList;

import isamrs.domain.Lekar;
import isamrs.domain.Operacija;
import isamrs.domain.Sala;
import isamrs.domain.Termin;
import isamrs.domain.TipPosete;

public class OperacijaDTO extends PosetaDTO {
	private ArrayList<String> lekari;
	private ArrayList<DijagnozaDTO> dijagnoze = new ArrayList<DijagnozaDTO>();
	private ZdravstveniKartonDTO zdravstveniKarton;

	public ArrayList<String> getLekari() {
		return lekari;
	}

	public void setLekari(ArrayList<String> lekari) {
		this.lekari = lekari;
	}

	public ArrayList<DijagnozaDTO> getDijagnoze() {
		return dijagnoze;
	}

	public void setDijagnoze(ArrayList<DijagnozaDTO> dijagnoze) {
		this.dijagnoze = dijagnoze;
	}

	public ZdravstveniKartonDTO getZdravstveniKarton() {
		return zdravstveniKarton;
	}

	public void setZdravstveniKarton(ZdravstveniKartonDTO zdravstveniKarton) {
		this.zdravstveniKarton = zdravstveniKarton;
	}

	public OperacijaDTO(Integer id, String opis, Termin termin, TipPosete tip, Sala sala) {
		super(id, opis, termin, tip, sala);
	}

	public OperacijaDTO() {}
	
	public OperacijaDTO(Operacija p) {
		this(p.getId(), p.getOpis(), p.getTermin(), p.getTipPosete(), p.getSala());
		this.lekari = new ArrayList<String>();
		for (Lekar r : p.getLekari()) {
			this.lekari.add(r.getIme() + " " + r.getPrezime());
		}
	}
}
