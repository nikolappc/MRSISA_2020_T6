package isamrs.domain;

import java.util.*;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonIgnore;
import isamrs.dto.TipPoseteDTO;

@Entity
@Table(name="tipovi_posete")
public class TipPosete {

	

	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Enumerated
	@Column(name = "tip", nullable = false,columnDefinition = "smallint")
	private Tip tip;

	@Column(name = "naziv", unique = true, nullable = false)
	private String naziv;
	
	@JsonIgnore
	@ManyToMany(mappedBy = "tipoviPoseta")
	public java.util.Collection<Lekar> lekari;
	
	@OneToMany(mappedBy = "tipPosete", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
	public Collection<StavkaCenovnika> stavkeCenovnika;
	@Version
	private Long version;
	
	public TipPosete(int id, Tip tip, String naziv) {
		this.id = id;
		this.tip = tip;
		this.naziv = naziv;
	}

	public TipPosete(TipPoseteDTO tip) {
		this(tip.getId(), tip.getTip(), tip.getNaziv());
	}

	
    public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public java.util.Collection<Lekar> getLekari() {
		return lekari;
	}

	public void setLekari(java.util.Collection<Lekar> lekari) {
		this.lekari = lekari;
	}

	public Collection<StavkaCenovnika> getStavkeCenovnika() {
		return stavkeCenovnika;
	}

	public void setStavkeCenovnika(Collection<StavkaCenovnika> stavkeCenovnika) {
		this.stavkeCenovnika = stavkeCenovnika;
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

	public TipPosete(int id, Tip tip, ArrayList<StavkaCenovnika> stavkeCenovnika, String naziv) {
		super();
		this.id = id;
		this.tip = tip;
		this.stavkeCenovnika = stavkeCenovnika;
		this.naziv = naziv;
	}
	
	public TipPosete() {
	}

}
