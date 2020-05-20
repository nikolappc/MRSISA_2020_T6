package isamrs.dto;

import isamrs.domain.Dijagnoza;

public class DijagnozaDTO {
	private Long id;
	private Long sifraDijagnoze;
	private String nazivDijagnoze;
	public Long getSifraDijagnoze() {
		return sifraDijagnoze;
	}
	
	public void setSifraDijagnoze(Long sifraDijagnoze) {
		this.sifraDijagnoze = sifraDijagnoze;
	}
	public String getNazivDijagnoze() {
		return nazivDijagnoze;
	}
	public void setNazivDijagnoze(String nazivDijagnoze) {
		this.nazivDijagnoze = nazivDijagnoze;
	}
	public DijagnozaDTO(Long id,Long sifraDijagnoze, String nazivDijagnoze) {
		super();
		this.id = id;
		this.sifraDijagnoze = sifraDijagnoze;
		this.nazivDijagnoze = nazivDijagnoze;
	}
	
	public DijagnozaDTO() {}
	
	public DijagnozaDTO(Dijagnoza d) {
		this(d.getId(), d.getSifraDijagnoze(), d.getNazivDijagnoze());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
