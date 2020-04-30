/***********************************************************************
 * Module:  Lekar.java
 * Author:  Dusan
 * Purpose: Defines the Class Lekar
 ***********************************************************************/

package isamrs.domain;

import java.util.*;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "lekari")
public class Lekar extends Osoba {
	
	@OneToMany
	@JoinColumn(name = "id_lekara")
	public java.util.Collection<Ocena> ocena;
	
	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "lekar")
	@JsonManagedReference(value = "lekarRefer")
	public Collection<Pregled> pregled;
	
	@OneToMany
	@JoinColumn(name = "id_lekara")
	public java.util.Collection<GodisnjiOdmor> godisnjiOdmor;
	
	@OneToMany
	@JoinColumn(name = "id_lekara")
	public Collection<RadnoVreme> radnoVreme;

	@ManyToMany
	@JoinTable(name = "lekari_operacije", joinColumns = { @JoinColumn(name = "lekar_id") }, inverseJoinColumns = {
	@JoinColumn(name = "operacija_id") })
	@JsonBackReference(value = "operacijeReference")
	public Collection<Operacija> operacije;
	
	@Transient
	public String tip = "LEKAR";

	public Lekar() {
		super();
		this.setTip("LEKAR");
	}

	/** @pdGenerated default getter */
	public java.util.Collection<Ocena> getOcena() {
		if (ocena == null)
			ocena = new java.util.HashSet<Ocena>();
		return ocena;
	}

	/** @pdGenerated default iterator getter */
	public java.util.Iterator getIteratorOcena() {
		if (ocena == null)
			ocena = new java.util.HashSet<Ocena>();
		return ocena.iterator();
	}

	/**
	 * @pdGenerated default setter
	 * @param newOcena
	 */
	public void setOcena(java.util.Collection<Ocena> newOcena) {
		removeAllOcena();
		for (java.util.Iterator iter = newOcena.iterator(); iter.hasNext();)
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
			this.ocena = new java.util.HashSet<Ocena>();
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
		if (this.ocena != null)
			if (this.ocena.contains(oldOcena))
				this.ocena.remove(oldOcena);
	}

	/** @pdGenerated default removeAll */
	public void removeAllOcena() {
		if (ocena != null)
			ocena.clear();
	}

	/** @pdGenerated default getter */
	public Collection<Pregled> getPregled() {
		return pregled;
	}

	/**
	 * @pdGenerated default setter
	 * @param newPregled
	 */
	public void setPregled(Collection<Pregled> newPregled) {
		this.pregled = newPregled;
	}

	/** @pdGenerated default getter */
	public java.util.Collection<GodisnjiOdmor> getGodisnjiOdmor() {
		if (godisnjiOdmor == null)
			godisnjiOdmor = new java.util.HashSet<GodisnjiOdmor>();
		return godisnjiOdmor;
	}

	/** @pdGenerated default iterator getter */
	public java.util.Iterator getIteratorGodisnjiOdmor() {
		if (godisnjiOdmor == null)
			godisnjiOdmor = new java.util.HashSet<GodisnjiOdmor>();
		return godisnjiOdmor.iterator();
	}

	/**
	 * @pdGenerated default setter
	 * @param newGodisnjiOdmor
	 */
	public void setGodisnjiOdmor(java.util.Collection<GodisnjiOdmor> newGodisnjiOdmor) {
		removeAllGodisnjiOdmor();
		for (java.util.Iterator iter = newGodisnjiOdmor.iterator(); iter.hasNext();)
			addGodisnjiOdmor((GodisnjiOdmor) iter.next());
	}

	/**
	 * @pdGenerated default add
	 * @param newGodisnjiOdmor
	 */
	public void addGodisnjiOdmor(GodisnjiOdmor newGodisnjiOdmor) {
		if (newGodisnjiOdmor == null)
			return;
		if (this.godisnjiOdmor == null)
			this.godisnjiOdmor = new java.util.HashSet<GodisnjiOdmor>();
		if (!this.godisnjiOdmor.contains(newGodisnjiOdmor))
			this.godisnjiOdmor.add(newGodisnjiOdmor);
	}

	/**
	 * @pdGenerated default remove
	 * @param oldGodisnjiOdmor
	 */
	public void removeGodisnjiOdmor(GodisnjiOdmor oldGodisnjiOdmor) {
		if (oldGodisnjiOdmor == null)
			return;
		if (this.godisnjiOdmor != null)
			if (this.godisnjiOdmor.contains(oldGodisnjiOdmor))
				this.godisnjiOdmor.remove(oldGodisnjiOdmor);
	}

	/** @pdGenerated default removeAll */
	public void removeAllGodisnjiOdmor() {
		if (godisnjiOdmor != null)
			godisnjiOdmor.clear();
	}

	/** @pdGenerated default getter */
	public Collection<RadnoVreme> getRadnoVreme() {
		return radnoVreme;
	}

	/**
	 * @pdGenerated default setter
	 * @param newRadnoVreme
	 */
	public void setRadnoVreme(Collection<RadnoVreme> newRadnoVreme) {
		this.radnoVreme = newRadnoVreme;
	}

	


}