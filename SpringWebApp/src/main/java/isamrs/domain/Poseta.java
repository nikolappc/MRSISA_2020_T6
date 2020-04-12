package isamrs.domain;

import static javax.persistence.InheritanceType.TABLE_PER_CLASS;

import java.util.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
//@MappedSuperclass

@Entity
@Inheritance(strategy = TABLE_PER_CLASS)
public class Poseta {

	@Column(name = "ime", unique = false, nullable = false)
	private String opis;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Integer id;

	@OneToOne
	@JoinColumn(name = "id")
	public Termin termin;

	@OneToOne
	@JoinColumn(name = "id")
	public TipPosete tipPosete;

	@OneToOne
	@JoinColumn(name = "id")
	public ZdravstveniKarton zdravstveniKarton;

	@OneToOne
	@JoinColumn(name = "id")
	public Sala sala;

	public Termin getTermin() {
		return termin;
	}

	public void setTermin(Termin newTermin) {
		this.termin = newTermin;
	}

	public TipPosete getTipPosete() {
		return tipPosete;
	}

	public void setTipPosete(TipPosete newTipPosete) {
		this.tipPosete = newTipPosete;
	}

	public ZdravstveniKarton getZdravstveniKarton() {
		return zdravstveniKarton;
	}

	public void setZdravstveniKarton(ZdravstveniKarton newZdravstveniKarton) {
		if (this.zdravstveniKarton == null || !this.zdravstveniKarton.equals(newZdravstveniKarton)) {
			if (this.zdravstveniKarton != null) {
				ZdravstveniKarton oldZdravstveniKarton = this.zdravstveniKarton;
				this.zdravstveniKarton = null;
				oldZdravstveniKarton.removePoseta(this);
			}
			if (newZdravstveniKarton != null) {
				this.zdravstveniKarton = newZdravstveniKarton;
				this.zdravstveniKarton.addPoseta(this);
			}
		}
	}

	public Sala getSala() {
		return sala;
	}

	public void setSala(Sala newSala) {
		this.sala = newSala;
	}

	public Poseta(String opis, Integer id, Termin termin, TipPosete tipPosete, ZdravstveniKarton zdravstveniKarton,
			Sala sala) {
		super();
		this.opis = opis;
		this.id = id;
		this.termin = termin;
		this.tipPosete = tipPosete;
		this.zdravstveniKarton = zdravstveniKarton;
		this.sala = sala;
	}

	public Poseta() {
	}

}