/***********************************************************************
 * Module:  Klinika.java
 * Author:  Dusan
 * Purpose: Defines the Class Klinika
 ***********************************************************************/

package isamrs.domain;


import java.util.HashSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "klinike")
public class Klinika {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;
	
	@Column(name = "naziv", unique = true, nullable = false)
	private String naziv;

	
	@Column(name = "opis", unique = false, nullable = false)
	private String opis;
	
	@OneToOne(cascade = CascadeType.ALL)
	public Cenovnik cenovnik = new Cenovnik();

	@OneToOne
	@JoinColumn(name = "id_adrese", referencedColumnName = "id")
	private Adresa adresa;
	
	@OneToOne
	@JoinColumn(name = "tip_klinike_id", referencedColumnName = "id")
	public TipKlinike tipKlinike;

	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_klinike")
	public java.util.Collection<Lekar> lekari;
	
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_klinike")
	@JsonManagedReference
	public java.util.Collection<MedicinskaSestra> sestre;
	
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_klinike")
	public java.util.Collection<Sala> sala;
	
	/*@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "klinika_pacijent", joinColumns = @JoinColumn(name = "id_klinike"), inverseJoinColumns = @JoinColumn(name = "id_pacijenta"))
	@JsonIgnore
	public java.util.Collection<Pacijent> pacijent;*/
	
	@OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	@JoinColumn(name = "id_klinike")
	@JsonManagedReference
	public java.util.Collection<Pregled> pregledi;
	
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_klinike")
	@JsonManagedReference
	public java.util.Collection<Operacija> operacije;
	
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_klinike")
	@JsonManagedReference
	public java.util.Collection<TipPosete> tipPosete;
	
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_klinike")
	@JsonManagedReference
	public java.util.Collection<Ocena> ocena;

	@OneToMany(fetch = FetchType.LAZY)
	@JsonManagedReference("adminRefer")
	public java.util.Collection<AdministratorKlinike> administratorKlinike;


	public Klinika() {
		super();
	}

	public Klinika(String naziv, Adresa adresa, String opis, TipKlinike tipKlinike) {
		this.naziv = naziv;
		this.adresa = adresa;
		this.opis = opis;
		this.tipKlinike = tipKlinike;
	}

	public java.util.Collection<Sala> getSala() {
		if (sala == null)
			sala = new java.util.HashSet<>();
		return sala;
	}

	public void setSala(java.util.Collection<Sala> newSala) {
		removeAllSala();
		for (java.util.Iterator<Sala> iter = newSala.iterator(); iter.hasNext();)
			addSala((Sala) iter.next());
	}


	public void addSala(Sala newSala) {
		if (newSala == null)
			return;
		if (this.sala == null)
			this.sala = new java.util.HashSet<>();
		if (!this.sala.contains(newSala))
			this.sala.add(newSala);
	}


	public void removeSala(Sala oldSala) {
		if (oldSala == null)
			return;
		if ((this.sala != null) && (this.sala.contains(oldSala)))
			this.sala.remove(oldSala);

	}

	public void removeAllSala() {
		if (sala != null)
			sala.clear();
	}



	public Cenovnik getCenovnik() {
		return cenovnik;
	}

	public void setCenovnik(Cenovnik newCenovnik) {
		this.cenovnik = newCenovnik;
	}

	public java.util.Collection<TipPosete> getTipPosete() {
		if (tipPosete == null)
			tipPosete = new java.util.HashSet<>();
		return tipPosete;
	}

	public void setTipPosete(java.util.Collection<TipPosete> newTipPosete) {
		removeAllTipPosete();
		for (java.util.Iterator<TipPosete> iter = newTipPosete.iterator(); iter.hasNext();)
			addTipPosete((TipPosete) iter.next());
	}

	public void addTipPosete(TipPosete newTipPosete) {
		if (newTipPosete == null)
			return;
		if (this.tipPosete == null)
			this.tipPosete = new java.util.HashSet<>();
		if (!this.tipPosete.contains(newTipPosete))
			this.tipPosete.add(newTipPosete);
	}


	public void removeTipPosete(TipPosete oldTipPosete) {
		if (oldTipPosete == null)
			return;
		if ((this.tipPosete != null) && (this.tipPosete.contains(oldTipPosete)))
			this.tipPosete.remove(oldTipPosete);
	}

	public void removeAllTipPosete() {
		if (tipPosete != null)
			tipPosete.clear();
	}

	public java.util.Collection<Ocena> getOcena() {
		if (ocena == null)
			ocena = new java.util.HashSet<>();
		return ocena;
	}

	public void setOcena(java.util.Collection<Ocena> newOcena) {
		removeAllOcena();
		for (java.util.Iterator<Ocena> iter = newOcena.iterator(); iter.hasNext();)
			addOcena((Ocena) iter.next());
	}

	public void addOcena(Ocena newOcena) {
		if (newOcena == null)
			return;
		if (this.ocena == null)
			this.ocena = new java.util.HashSet<>();
		if (!this.ocena.contains(newOcena))
			this.ocena.add(newOcena);
	}

	public void removeOcena(Ocena oldOcena) {
		if (oldOcena == null)
			return;
		if ((this.ocena != null) && (this.ocena.contains(oldOcena)))
			this.ocena.remove(oldOcena);
	}

	public void removeAllOcena() {
		if (ocena != null)
			ocena.clear();
	}

	public java.util.Collection<AdministratorKlinike> getAdministratorKlinike() {
		if (administratorKlinike == null)
			administratorKlinike = new java.util.HashSet<>();
		return administratorKlinike;
	}


	public void setAdministratorKlinike(java.util.Collection<AdministratorKlinike> newAdministratorKlinike) {
		removeAllAdministratorKlinike();
		for (java.util.Iterator<AdministratorKlinike> iter = newAdministratorKlinike.iterator(); iter.hasNext();)
			addAdministratorKlinike((AdministratorKlinike) iter.next());
	}


	public void addAdministratorKlinike(AdministratorKlinike newAdministratorKlinike) {
		if (newAdministratorKlinike == null)
			return;
		if (this.administratorKlinike == null)
			this.administratorKlinike = new java.util.HashSet<>();
		if (!this.administratorKlinike.contains(newAdministratorKlinike))
			this.administratorKlinike.add(newAdministratorKlinike);
	}


	public void removeAdministratorKlinike(AdministratorKlinike oldAdministratorKlinike) {
		if (oldAdministratorKlinike == null)
			return;
		if ((this.administratorKlinike != null) && (this.administratorKlinike.contains(oldAdministratorKlinike)))
			this.administratorKlinike.remove(oldAdministratorKlinike);
	}

	public void removeAllAdministratorKlinike() {
		if (administratorKlinike != null)
			administratorKlinike.clear();
	}

	public TipKlinike getTipKlinike() {
		return tipKlinike;
	}

	public void setTipKlinike(TipKlinike newTipKlinike) {
		this.tipKlinike = newTipKlinike;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public Adresa getAdresa() {
		return adresa;
	}

	public void setAdresa(Adresa adresa) {
		this.adresa = adresa;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public java.util.Collection<Lekar> getLekari() {
		return lekari;
	}

	public void setLekari(java.util.Collection<Lekar> lekari) {
		this.lekari = lekari;
	}

	public java.util.Collection<MedicinskaSestra> getSestre() {
		return sestre;
	}

	public void setSestre(java.util.Collection<MedicinskaSestra> sestre) {
		this.sestre = sestre;
	}

	public java.util.Collection<Pregled> getPregledi() {
		return pregledi;
	}

	public void setPregledi(java.util.Collection<Pregled> pregledi) {
		this.pregledi = pregledi;
	}

	public java.util.Collection<Operacija> getOperacije() {
		return operacije;
	}

	public void setOperacije(java.util.Collection<Operacija> operacije) {
		this.operacije = operacije;
	}
	
	public double getProsjek() {
		double sum = 0.0;
		int count = 0;
		for (Ocena o : this.ocena)
		{
			sum += o.getVrednost();
			count += 1;
		}
		if (count != 0) {
			return sum/count;
		} else {
			return 0;
		}

	}
	
	

}