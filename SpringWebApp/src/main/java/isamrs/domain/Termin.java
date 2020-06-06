package isamrs.domain;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="termini")
public class Termin {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "pocetak", nullable = false)
	private Date pocetak;

	@Column(name = "kraj", nullable = false)
	private Date kraj;

	public Termin(LocalDateTime pocetak, LocalDateTime kraj) {
		this.pocetak = Date.from(pocetak.atZone(ZoneId.systemDefault()).toInstant());
		this.kraj = Date.from(kraj.atZone(ZoneId.systemDefault()).toInstant());
	}

    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getPocetak() {
		return pocetak;
	}

	public void setPocetak(Date pocetak) {
		this.pocetak = pocetak;
	}

	public Date getKraj() {
		return kraj;
	}

	public void setKraj(Date kraj) {
		this.kraj = kraj;
	}

	public Termin(int id, Date pocetak, Date kraj) {
		super();
		this.id = id;
		this.pocetak = pocetak;
		this.kraj = kraj;

	}

	public Termin(Date pocetak, Date kraj) {
		this.pocetak = pocetak;
		this.kraj = kraj;
	}

	public Termin() {}


	public Duration getDuration(){
		return Duration.between(this.pocetak.toInstant(), this.kraj.toInstant());
	}

}