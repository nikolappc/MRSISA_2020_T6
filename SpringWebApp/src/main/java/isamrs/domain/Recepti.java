package isamrs.domain;

import java.util.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Recepti {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "overen", unique = true, nullable = false)
	private boolean overen;
	
	@OneToOne
	@JoinColumn(name = "id")
	public Lek lek;

	public Lek getLek() {
		return lek;
	}

	public void setLek(Lek newLek) {
		this.lek = newLek;
	}

	public Recepti(Integer id, boolean overen, Lek lek) {
		super();
		this.id = id;
		this.overen = overen;
		this.lek = lek;
	}
	
	public Recepti() {}

}