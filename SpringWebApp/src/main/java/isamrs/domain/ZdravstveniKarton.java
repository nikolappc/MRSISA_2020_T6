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

import com.fasterxml.jackson.annotation.JsonBackReference;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import isamrs.dto.ZdravstveniKartonDTO;

@Entity
@Table(name = "zdravstveni_kartoni")
public class ZdravstveniKarton {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "visina", unique = false)
	private Double visina;
	
	@Column(name = "tezina", unique = false)
	private Double tezina;
	
	@Column(name = "krvna_grupa", unique = false)
	private String krvnaGrupa;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name="id_kartona")
	@JsonManagedReference
	public Collection<Pregled> pregledi;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name="id_kartona")
	public Collection<Dijagnoza> dijagnoza;

	@OneToOne(mappedBy = "zdravstveniKarton", fetch = FetchType.LAZY)
	@JsonBackReference(value = "zdravstveniKartonReference")
	public Pacijent pacijent;

	public ZdravstveniKarton(Integer id, Double visina, Double tezina, String krvnaGrupa) {
		this.id = id;
		this.visina = visina;
		this.tezina = tezina;
		this.krvnaGrupa = krvnaGrupa;
	}

	public ZdravstveniKarton(ZdravstveniKartonDTO zdravstveniKarton) {
		this(zdravstveniKarton.getId(), zdravstveniKarton.getVisina(), zdravstveniKarton.getTezina(),zdravstveniKarton.getKrvnaGrupa());
	}


	public Collection<Dijagnoza> getDijagnoza() {
		if (dijagnoza == null)
			dijagnoza = new ArrayList<Dijagnoza>();
		return dijagnoza;
	}

	public java.util.Iterator getIteratorDijagnoza() {
		if (dijagnoza == null)
			dijagnoza = new ArrayList<Dijagnoza>();
		return dijagnoza.iterator();
	}

	public void setDijagnoza(Collection<Dijagnoza> newDijagnoza) {
		removeAllDijagnoza();
		for (java.util.Iterator iter = newDijagnoza.iterator(); iter.hasNext();)
			addDijagnoza((Dijagnoza) iter.next());
	}

	public void addDijagnoza(Dijagnoza newDijagnoza) {
		if (newDijagnoza == null)
			return;
		if (this.dijagnoza == null)
			this.dijagnoza = new ArrayList<Dijagnoza>();
		if (!this.dijagnoza.contains(newDijagnoza))
			this.dijagnoza.add(newDijagnoza);
	}

	public void removeDijagnoza(Dijagnoza oldDijagnoza) {
		if (oldDijagnoza == null)
			return;
		if (this.dijagnoza != null)
			if (this.dijagnoza.contains(oldDijagnoza))
				this.dijagnoza.remove(oldDijagnoza);
	}

	public void removeAllDijagnoza() {
		if (dijagnoza != null)
			dijagnoza.clear();
	}

	public Pacijent getPacijent() {
		return pacijent;
	}

	public void setPacijent(Pacijent newPacijent) {
		if (this.pacijent == null || !this.pacijent.equals(newPacijent)) {
			if (this.pacijent != null)
				this.pacijent.setZdravstveniKarton(null);
			this.pacijent = newPacijent;
			if (this.pacijent != null)
				this.pacijent.setZdravstveniKarton(this);
		}
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public ZdravstveniKarton(Integer id, Collection<Pregled> pregled, Collection<Dijagnoza> dijagnoza, Pacijent pacijent) {
		super();
		this.id = id;
		this.pregledi = pregled;
		this.dijagnoza = dijagnoza;
		this.pacijent = pacijent;
	}

	public ZdravstveniKarton() {
	}
	
	public ZdravstveniKarton(Integer id, Double visina, Double tezina, String krvnaGrupa, Collection<Pregled> pregledi,
			Collection<Dijagnoza> dijagnoza, Pacijent pacijent) {
		super();
		this.id = id;
		this.visina = visina;
		this.tezina = tezina;
		this.krvnaGrupa = krvnaGrupa;
		this.pregledi = pregledi;
		this.dijagnoza = dijagnoza;
		this.pacijent = pacijent;
	}

	public Double getVisina() {
		return visina;
	}

	public void setVisina(Double visina) {
		this.visina = visina;
	}

	public Double getTezina() {
		return tezina;
	}

	public void setTezina(Double tezina) {
		this.tezina = tezina;
	}

	public String getKrvnaGrupa() {
		return krvnaGrupa;
	}

	public void setKrvnaGrupa(String krvnaGrupa) {
		this.krvnaGrupa = krvnaGrupa;
	}

	public Collection<Pregled> getPregledi() {
		return pregledi;
	}

	public void setPregledi(Collection<Pregled> pregledi) {
		this.pregledi = pregledi;
	}


}