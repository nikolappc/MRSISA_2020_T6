package isamrs.dto;

import java.util.Date;

import isamrs.domain.Operacija;
import isamrs.domain.Poseta;
import isamrs.domain.Pregled;

public class TerminDTO {
	private Date start;
	private Date end;
	private String name;
	private String details;
	
	public TerminDTO() {
		super();
	}
	
	public TerminDTO(Poseta poseta) {
		super();
		
		this.start = poseta.getTermin().getPocetak();
		this.end = poseta.getTermin().getKraj();
		
		if( poseta instanceof Pregled) {
			Pregled pregled = (Pregled) poseta;
			this.name = pregled.getLekar().getIme() + " " + pregled.getLekar().getPrezime() ;
		}
		else if(poseta instanceof Operacija){
			Operacija operacija = (Operacija) poseta;
			this.name = "Operacija";
		}
		this.details = poseta.getOpis();
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

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}
	
	
}
