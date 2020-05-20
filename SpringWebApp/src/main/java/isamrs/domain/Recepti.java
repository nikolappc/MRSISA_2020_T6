package isamrs.domain;

import java.util.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="recepti")
public class Recepti {

	@Id
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;
	
	@Column(name = "overen", nullable = false)
	private boolean overen;
	
	@OneToOne
	@JoinColumn(name = "id_recepta")
	public Lek lek;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_pregleda")
	@JsonBackReference(value = "pregledReference")
	private Pregled pregled;

	public Lek getLek() {
		return lek;
	}

	public void setLek(Lek newLek) {
		this.lek = newLek;
	}
	
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public boolean isOveren() {
		return overen;
	}

	public void setOveren(boolean overen) {
		this.overen = overen;
	}

	public Pregled getPregled() {
		return pregled;
	}

	public void setPregled(Pregled pregled) {
		this.pregled = pregled;
	}

	public Recepti(Integer id, boolean overen, Lek lek) {
		super();
		this.id = id;
		this.overen = overen;
		this.lek = lek;
	}
	
	public Recepti() {}

}