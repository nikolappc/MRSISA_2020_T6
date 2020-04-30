package isamrs.domain;

import java.util.*;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="tipovi_posete")
public class TipPosete {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "tip", unique = true, nullable = false)
	private Tip tip;

	@Column(name = "naziv", unique = true, nullable = false)
	private String naziv;
	
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name="id_stavke")
	private StavkaCenovnika stavkaCenovnika;

	

	public StavkaCenovnika getStavkaCenovnika() {
		return stavkaCenovnika;
	}

	public void setStavkaCenovnika(StavkaCenovnika stavkaCenovnika) {
		this.stavkaCenovnika = stavkaCenovnika;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Tip getTip() {
		return tip;
	}

	public void setTip(Tip tip) {
		this.tip = tip;
	}
	
	

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public TipPosete(int id, Tip tip, StavkaCenovnika stavkaCenovnika, String naziv) {
		super();
		this.id = id;
		this.tip = tip;
		this.stavkaCenovnika = stavkaCenovnika;
		this.naziv = naziv;
	}

	public TipPosete() {
	}

}