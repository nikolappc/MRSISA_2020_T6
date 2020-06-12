package isamrs.domain;

import static javax.persistence.InheritanceType.TABLE_PER_CLASS;

import java.util.*;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

//@Entity
//@Inheritance(strategy = TABLE_PER_CLASS)
@MappedSuperclass
public class Poseta {


	@Version
	private Long version;


	@Column(name = "ime", unique = false, nullable = true)
	private String opis;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_termina")
	public Termin termin;

	@OneToOne
	@JoinColumn(name = "id_tipa_posete", nullable = false)
	public TipPosete tipPosete;

	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinColumn(name="id_kartona")
	@JsonBackReference
	public ZdravstveniKarton zdravstveniKarton;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "id_sale")
	public Sala sala;

	@Column(name = "potvrdjen", nullable = false)
	private boolean potvrdjen;

	@Column(name = "odradjen", nullable = false)
	private boolean odradjen;

	public Poseta(String opis, Termin termin, TipPosete tipPosete) {
		this.opis = opis;
		this.termin = termin;
		this.tipPosete = tipPosete;
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


	public boolean isPotvrdjen() {
		return potvrdjen;
	}

	public void setPotvrdjen(boolean potvrdjen) {
		this.potvrdjen = potvrdjen;
	}

	public boolean isOdradjen() {
		return odradjen;
	}

	public void setOdradjen(boolean odradjen) {
		this.odradjen = odradjen;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

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
				//oldZdravstveniKarton.rmovePregled(this);
			}
			if (newZdravstveniKarton != null) {
				this.zdravstveniKarton = newZdravstveniKarton;
				//this.zdravstveniKarton.addPregled(this);
			}
		}
	}

	public Sala getSala() {
		return sala;
	}

	public void setSala(Sala newSala) {
		this.sala = newSala;
	}

	public Poseta() {
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}
}