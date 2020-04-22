package isamrs.dto;

import isamrs.domain.Dijagnoza;

public class DijagnozaDTO {
	private Integer sifraDijagnoze;
	private String nazivDijagnoze;
	public Integer getSifraDijagnoze() {
		return sifraDijagnoze;
	}
	public void setSifraDijagnoze(Integer sifraDijagnoze) {
		this.sifraDijagnoze = sifraDijagnoze;
	}
	public String getNazivDijagnoze() {
		return nazivDijagnoze;
	}
	public void setNazivDijagnoze(String nazivDijagnoze) {
		this.nazivDijagnoze = nazivDijagnoze;
	}
	public DijagnozaDTO(Integer sifraDijagnoze, String nazivDijagnoze) {
		super();
		this.sifraDijagnoze = sifraDijagnoze;
		this.nazivDijagnoze = nazivDijagnoze;
	}
	
	public DijagnozaDTO() {}
	
	public DijagnozaDTO(Dijagnoza d) {
		this(d.getSifraDijagnoze(), d.getNazivDijagnoze());
	}
}
