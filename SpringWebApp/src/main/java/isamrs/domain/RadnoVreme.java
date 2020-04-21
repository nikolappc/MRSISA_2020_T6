package isamrs.domain;

import java.util.*;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="radna_vremena")
public class RadnoVreme {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "pocetak", nullable = false)
	private Date pocetak;

	@Column(name = "kraj", nullable = false)
	private Date kraj;

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

	public RadnoVreme(int id, Date pocetak, Date kraj) {
		super();
		this.id = id;
		this.pocetak = pocetak;
		this.kraj = kraj;
	}
	
	public RadnoVreme() {}

}