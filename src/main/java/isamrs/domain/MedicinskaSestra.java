/***********************************************************************
 * Module:  MedicinskaSestra.java
 * Author:  Dusan
 * Purpose: Defines the Class MedicinskaSestra
 ***********************************************************************/

package isamrs.domain;

import java.awt.*;
import java.util.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity
@Table(name = "medicinske_sestre")
public class MedicinskaSestra extends MedicinskoOsoblje {
	@OneToMany
	@Column(name = "id_sestre")
	public java.util.Collection<Recepti> overeniRecepti;
	@OneToMany
	@JoinColumn(name = "id_sestre")
	public java.util.Collection<GodisnjiOdmor> godisnjiOdmor;
	@OneToMany
	@JoinColumn(name = "id_sestre")
	public Collection<RadnoVreme> radnoVreme;
	
	@Transient
	public String tip = "SESTRA";

	public MedicinskaSestra() {
		super();
		this.setTip("SESTRA");
	}

	/** @pdGenerated default getter */
	public java.util.Collection<Recepti> getOvereniRecepti() {
		if (overeniRecepti == null)
			overeniRecepti = new java.util.HashSet<Recepti>();
		return overeniRecepti;
	}

	/** @pdGenerated default iterator getter */
	public java.util.Iterator getIteratorOvereniRecepti() {
		if (overeniRecepti == null)
			overeniRecepti = new java.util.HashSet<Recepti>();
		return overeniRecepti.iterator();
	}

	/**
	 * @pdGenerated default setter
	 * @param newOvereniRecepti
	 */
	public void setOvereniRecepti(java.util.Collection<Recepti> newOvereniRecepti) {
		removeAllOvereniRecepti();
		for (java.util.Iterator iter = newOvereniRecepti.iterator(); iter.hasNext();)
			addOvereniRecepti((Recepti) iter.next());
	}

	/**
	 * @pdGenerated default add
	 * @param newRecepti
	 */
	public void addOvereniRecepti(Recepti newRecepti) {
		if (newRecepti == null)
			return;
		if (this.overeniRecepti == null)
			this.overeniRecepti = new java.util.HashSet<Recepti>();
		if (!this.overeniRecepti.contains(newRecepti))
			this.overeniRecepti.add(newRecepti);
	}

	/**
	 * @pdGenerated default remove
	 * @param oldRecepti
	 */
	public void removeOvereniRecepti(Recepti oldRecepti) {
		if (oldRecepti == null)
			return;
		if (this.overeniRecepti != null)
			if (this.overeniRecepti.contains(oldRecepti))
				this.overeniRecepti.remove(oldRecepti);
	}

	/** @pdGenerated default removeAll */
	public void removeAllOvereniRecepti() {
		if (overeniRecepti != null)
			overeniRecepti.clear();
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