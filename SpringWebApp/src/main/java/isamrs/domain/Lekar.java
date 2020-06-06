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
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "lekari")
public class Lekar extends MedicinskoOsoblje {
	
	@OneToMany
	@JoinColumn(name = "id_lekara")
	public java.util.Collection<Ocena> ocena;
	
	@JsonIgnore
	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "lekar")
	public Collection<Pregled> pregled;
	
	@JsonIgnore
	@OneToMany
	@JoinColumn(name = "id_lekara")
	public java.util.Collection<GodisnjiOdmor> godisnjiOdmor;
	
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name = "id_lekara")
	public Collection<RadnoVreme> radnoVreme;

	@JsonIgnore
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "lekari_operacije", joinColumns = { @JoinColumn(name = "lekar_id") }, inverseJoinColumns = {
	@JoinColumn(name = "operacija_id") })
	public Collection<Operacija> operacije = new LinkedList<>();
	
	@JsonIgnore
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "lekari_specijalnosti", joinColumns = { @JoinColumn(name = "lekar_id") }, inverseJoinColumns = {
	@JoinColumn(name = "tip_id") })
	public Collection<TipPosete> tipoviPoseta;
	
	@Transient
	public String tip = "LEKAR";

	public Lekar() {
		super();
		this.setTip("LEKAR");
	}

	
	public Collection<TipPosete> getTipoviPoseta() {
		return tipoviPoseta;
	}


	public void setTipoviPoseta(Collection<TipPosete> tipoviPoseta) {
		this.tipoviPoseta = tipoviPoseta;
	}


	public String getTip() {
		return tip;
	}


	public void setTip(String tip) {
		this.tip = tip;
	}


	public void setOperacije(Collection<Operacija> operacije) {
		this.operacije = operacije;
	}


	public java.util.Collection<Ocena> getOcena() {
		if (ocena == null)
			ocena = new java.util.HashSet<Ocena>();
		return ocena;
	}

	

	public void setOcena(java.util.Collection<Ocena> newOcena) {
		removeAllOcena();
		for (java.util.Iterator iter = newOcena.iterator(); iter.hasNext();)
			addOcena((Ocena) iter.next());
	}

	
	public void addOcena(Ocena newOcena) {
		if (newOcena == null)
			return;
		if (this.ocena == null)
			this.ocena = new java.util.HashSet<Ocena>();
		if (!this.ocena.contains(newOcena))
			this.ocena.add(newOcena);
	}

	
	public void removeOcena(Ocena oldOcena) {
		if (oldOcena == null)
			return;
		if (this.ocena != null)
			if (this.ocena.contains(oldOcena))
				this.ocena.remove(oldOcena);
	}

	
	public void removeAllOcena() {
		if (ocena != null)
			ocena.clear();
	}

	
	public Collection<Pregled> getPregled() {
		return pregled;
	}
	
	public Collection<Operacija> getOperacije() {
		return operacije;
	}

	
	public void setPregled(Collection<Pregled> newPregled) {
		this.pregled = newPregled;
	}

	
	public java.util.Collection<GodisnjiOdmor> getGodisnjiOdmor() {
		if (godisnjiOdmor == null)
			godisnjiOdmor = new java.util.HashSet<GodisnjiOdmor>();
		return godisnjiOdmor;
	}

	

	
	public void setGodisnjiOdmor(java.util.Collection<GodisnjiOdmor> newGodisnjiOdmor) {
		removeAllGodisnjiOdmor();
		for (java.util.Iterator iter = newGodisnjiOdmor.iterator(); iter.hasNext();)
			addGodisnjiOdmor((GodisnjiOdmor) iter.next());
	}

	public void addGodisnjiOdmor(GodisnjiOdmor newGodisnjiOdmor) {
		if (newGodisnjiOdmor == null)
			return;
		if (this.godisnjiOdmor == null)
			this.godisnjiOdmor = new java.util.HashSet<GodisnjiOdmor>();
		if (!this.godisnjiOdmor.contains(newGodisnjiOdmor))
			this.godisnjiOdmor.add(newGodisnjiOdmor);
	}

	
	public void removeGodisnjiOdmor(GodisnjiOdmor oldGodisnjiOdmor) {
		if (oldGodisnjiOdmor == null)
			return;
		if (this.godisnjiOdmor != null)
			if (this.godisnjiOdmor.contains(oldGodisnjiOdmor))
				this.godisnjiOdmor.remove(oldGodisnjiOdmor);
	}

	public void removeAllGodisnjiOdmor() {
		if (godisnjiOdmor != null)
			godisnjiOdmor.clear();
	}

	
	public Collection<RadnoVreme> getRadnoVreme() {
		return radnoVreme;
	}

	
	public void setRadnoVreme(Collection<RadnoVreme> newRadnoVreme) {
		this.radnoVreme = newRadnoVreme;
	}

	public double getProsjek() {
		double sum = 0.0;
		int count = 0;
		for (Ocena o : this.ocena)
		{
			sum += o.getVrednost();
			count += 1;
		}
		return sum/count;
	}


	public RadnoVreme lastRadnoVreme() {

		return new LinkedList<>(radnoVreme).get(radnoVreme.size()-1);
	}

	public void addOperacija(Operacija operacija) {
		this.operacije.add(operacija);
	}
}