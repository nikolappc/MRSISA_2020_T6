/***********************************************************************
 * Module:  Klinika.java
 * Author:  Dusan
 * Purpose: Defines the Class Klinika
 ***********************************************************************/

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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
	
	@OneToOne
	public Cenovnik cenovnik ;

	@OneToOne
	@JoinColumn(name = "id_adrese", referencedColumnName = "id")
	private Adresa adresa;
	
	@OneToOne
	@JoinColumn(name = "tip_klinike_id", referencedColumnName = "id")
	public TipKlinike tipKlinike;

	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_klinike")
//	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
	public java.util.Collection<Lekar> lekari;
	
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_klinike")
//	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
	@JsonManagedReference
	public java.util.Collection<MedicinskaSestra> sestre;
	
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_klinike")
//	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
	public java.util.Collection<Sala> sala;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "klinika_pacijent", joinColumns = @JoinColumn(name = "id_klinike"), inverseJoinColumns = @JoinColumn(name = "id_pacijenta"))
//	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@JsonIgnore
	public java.util.Collection<Pacijent> pacijent;
	
	@OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	@JoinColumn(name = "id_klinike")
//	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
	@JsonManagedReference
	public java.util.Collection<Pregled> pregledi;
	
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_klinike")
//	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
	@JsonManagedReference
	public java.util.Collection<Operacija> operacije;
	
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_klinike")
//	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
	@JsonManagedReference
	public java.util.Collection<TipPosete> tipPosete;
	
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_klinike")
//	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
	@JsonManagedReference
	public java.util.Collection<Ocena> ocena;

	@OneToMany(fetch = FetchType.LAZY)
//	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
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

	/** @pdGenerated default getter */
	public java.util.Collection<Sala> getSala() {
		if (sala == null)
			sala = new java.util.HashSet<>();
		return sala;
	}
	
	public java.util.Iterator<Sala> getIteratorSala() {
		if (sala == null)
			sala = new java.util.HashSet<>();
		return sala.iterator();
	}


	/**
	 * @pdGenerated default setter
	 * @param newSala
	 */
	public void setSala(java.util.Collection<Sala> newSala) {
		removeAllSala();
		for (java.util.Iterator<Sala> iter = newSala.iterator(); iter.hasNext();)
			addSala((Sala) iter.next());
	}

	/**
	 * @pdGenerated default add
	 * @param newSala
	 */
	public void addSala(Sala newSala) {
		if (newSala == null)
			return;
		if (this.sala == null)
			this.sala = new java.util.HashSet<>();
		if (!this.sala.contains(newSala))
			this.sala.add(newSala);
	}

	/**
	 * @pdGenerated default remove
	 * @param oldSala
	 */
	public void removeSala(Sala oldSala) {
		if (oldSala == null)
			return;
		if ((this.sala != null) && (this.sala.contains(oldSala)))
			this.sala.remove(oldSala);
		/*if (this.sala != null)
			if (this.sala.contains(oldSala))
				this.sala.remove(oldSala);
		*/
	}

	/** @pdGenerated default removeAll */
	public void removeAllSala() {
		if (sala != null)
			sala.clear();
	}

	/** @pdGenerated default getter */
	public java.util.Collection<Pacijent> getPacijent() {
		if (pacijent == null)
			pacijent = new java.util.HashSet<>();
		return pacijent;
	}

	/** @pdGenerated default iterator getter */
	public java.util.Iterator<Pacijent> getIteratorPacijent() {
		if (pacijent == null)
			pacijent = new java.util.HashSet<>();
		return pacijent.iterator();
	}

	/**
	 * @pdGenerated default setter
	 * @param newPacijent
	 */
	public void setPacijent(java.util.Collection<Pacijent> newPacijent) {
		removeAllPacijent();
		for (java.util.Iterator<Pacijent> iter = newPacijent.iterator(); iter.hasNext();)
			addPacijent((Pacijent) iter.next());
	}

	/**
	 * @pdGenerated default add
	 * @param newPacijent
	 */
	public void addPacijent(Pacijent newPacijent) {
		if (newPacijent == null)
			return;
		if (this.pacijent == null)
			this.pacijent = new java.util.HashSet<Pacijent>();
		if (!this.pacijent.contains(newPacijent))
			this.pacijent.add(newPacijent);
	}

	/**
	 * @pdGenerated default remove
	 * @param oldPacijent
	 */
	public void removePacijent(Pacijent oldPacijent) {
		if (oldPacijent == null)
			return;
		if ((this.pacijent != null) && (this.pacijent.contains(oldPacijent)))
			this.pacijent.remove(oldPacijent);
	}

	/** @pdGenerated default removeAll */
	public void removeAllPacijent() {
		if (pacijent != null)
			pacijent.clear();
	}


	/** @pdGenerated default parent getter */
	public Cenovnik getCenovnik() {
		return cenovnik;
	}

	/**
	 * @pdGenerated default parent setter
	 * @param newCenovnik
	 */
	public void setCenovnik(Cenovnik newCenovnik) {
		this.cenovnik = newCenovnik;
	}

	/** @pdGenerated default getter */
	public java.util.Collection<TipPosete> getTipPosete() {
		if (tipPosete == null)
			tipPosete = new java.util.HashSet<>();
		return tipPosete;
	}

	/** @pdGenerated default iterator getter */
	public java.util.Iterator<TipPosete> getIteratorTipPosete() {
		if (tipPosete == null)
			tipPosete = new java.util.HashSet<>();
		return tipPosete.iterator();
	}

	/**
	 * @pdGenerated default setter
	 * @param newTipPosete
	 */
	public void setTipPosete(java.util.Collection<TipPosete> newTipPosete) {
		removeAllTipPosete();
		for (java.util.Iterator<TipPosete> iter = newTipPosete.iterator(); iter.hasNext();)
			addTipPosete((TipPosete) iter.next());
	}

	/**
	 * @pdGenerated default add
	 * @param newTipPosete
	 */
	public void addTipPosete(TipPosete newTipPosete) {
		if (newTipPosete == null)
			return;
		if (this.tipPosete == null)
			this.tipPosete = new java.util.HashSet<>();
		if (!this.tipPosete.contains(newTipPosete))
			this.tipPosete.add(newTipPosete);
	}

	/**
	 * @pdGenerated default remove
	 * @param oldTipPosete
	 */
	public void removeTipPosete(TipPosete oldTipPosete) {
		if (oldTipPosete == null)
			return;
		if ((this.tipPosete != null) && (this.tipPosete.contains(oldTipPosete)))
			this.tipPosete.remove(oldTipPosete);
	}

	/** @pdGenerated default removeAll */
	public void removeAllTipPosete() {
		if (tipPosete != null)
			tipPosete.clear();
	}

	/** @pdGenerated default getter */
	public java.util.Collection<Ocena> getOcena() {
		if (ocena == null)
			ocena = new java.util.HashSet<>();
		return ocena;
	}

	/** @pdGenerated default iterator getter */
	public java.util.Iterator<Ocena> getIteratorOcena() {
		if (ocena == null)
			ocena = new java.util.HashSet<>();
		return ocena.iterator();
	}

	/**
	 * @pdGenerated default setter
	 * @param newOcena
	 */
	public void setOcena(java.util.Collection<Ocena> newOcena) {
		removeAllOcena();
		for (java.util.Iterator<Ocena> iter = newOcena.iterator(); iter.hasNext();)
			addOcena((Ocena) iter.next());
	}

	/**
	 * @pdGenerated default add
	 * @param newOcena
	 */
	public void addOcena(Ocena newOcena) {
		if (newOcena == null)
			return;
		if (this.ocena == null)
			this.ocena = new java.util.HashSet<>();
		if (!this.ocena.contains(newOcena))
			this.ocena.add(newOcena);
	}

	/**
	 * @pdGenerated default remove
	 * @param oldOcena
	 */
	public void removeOcena(Ocena oldOcena) {
		if (oldOcena == null)
			return;
		if ((this.ocena != null) && (this.ocena.contains(oldOcena)))
			this.ocena.remove(oldOcena);
	}

	/** @pdGenerated default removeAll */
	public void removeAllOcena() {
		if (ocena != null)
			ocena.clear();
	}

	/** @pdGenerated default getter */
	public java.util.Collection<AdministratorKlinike> getAdministratorKlinike() {
		if (administratorKlinike == null)
			administratorKlinike = new java.util.HashSet<>();
		return administratorKlinike;
	}

	/** @pdGenerated default iterator getter */
	public java.util.Iterator<AdministratorKlinike> getIteratorAdministratorKlinike() {
		if (administratorKlinike == null)
			administratorKlinike = new java.util.HashSet<>();
		return administratorKlinike.iterator();
	}

	/**
	 * @pdGenerated default setter
	 * @param newAdministratorKlinike
	 */
	public void setAdministratorKlinike(java.util.Collection<AdministratorKlinike> newAdministratorKlinike) {
		removeAllAdministratorKlinike();
		for (java.util.Iterator<AdministratorKlinike> iter = newAdministratorKlinike.iterator(); iter.hasNext();)
			addAdministratorKlinike((AdministratorKlinike) iter.next());
	}

	/**
	 * @pdGenerated default add
	 * @param newAdministratorKlinike
	 */
	public void addAdministratorKlinike(AdministratorKlinike newAdministratorKlinike) {
		if (newAdministratorKlinike == null)
			return;
		if (this.administratorKlinike == null)
			this.administratorKlinike = new java.util.HashSet<>();
		if (!this.administratorKlinike.contains(newAdministratorKlinike))
			this.administratorKlinike.add(newAdministratorKlinike);
	}

	/**
	 * @pdGenerated default remove
	 * @param oldAdministratorKlinike
	 */
	public void removeAdministratorKlinike(AdministratorKlinike oldAdministratorKlinike) {
		if (oldAdministratorKlinike == null)
			return;
		if ((this.administratorKlinike != null) && (this.administratorKlinike.contains(oldAdministratorKlinike)))
			this.administratorKlinike.remove(oldAdministratorKlinike);
	}

	/** @pdGenerated default removeAll */
	public void removeAllAdministratorKlinike() {
		if (administratorKlinike != null)
			administratorKlinike.clear();
	}

	/** @pdGenerated default parent getter */
	public TipKlinike getTipKlinike() {
		return tipKlinike;
	}

	/**
	 * @pdGenerated default parent setter
	 * @param newTipKlinike
	 */
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