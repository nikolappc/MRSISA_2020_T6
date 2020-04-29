package isamrs.dto;

import java.util.Date;

import isamrs.domain.Pregled;

public class TerminDTO {
	private Date start;
	private Date end;
	private String name;
	
	public TerminDTO() {
		super();
	}
	
	public TerminDTO(Pregled pregled) {
		super();
		
		this.start = pregled.getTermin().getPocetak();
		this.end = pregled.getTermin().getKraj();
		
		this.name = "Pacijent: ";
		this.name += pregled.getZdravstveniKarton().getPacijent().getIme() + 
				" " + pregled.getZdravstveniKarton().getPacijent().getPrezime();
		this.name += "\nLekar: " + pregled.getLekar().getIme() + 
				" " + pregled.getLekar().getPrezime() + "\n";
	}
	
	public Date getStart() {
		return start;
	}
	public void setStart(Date start) {
		this.start = start;
	}
	public Date getEnd() {
		return end;
	}
	public void setEnd(Date end) {
		this.end = end;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
