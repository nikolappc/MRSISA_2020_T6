/***********************************************************************
 * Module:  Operacija.java
 * Author:  Dusan
 * Purpose: Defines the Class Operacija
 ***********************************************************************/

package isamrs.domain;

import java.util.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "operacije")
public class Operacija extends Poseta {

	@ManyToMany(mappedBy = "operacije", cascade = CascadeType.ALL)
	public java.util.Collection<Lekar> lekari;

	public Operacija() {
		super();
	}

	/** @pdGenerated default getter */
	public java.util.Collection<Lekar> getLekar() {
		if (lekari == null)
			lekari = new java.util.HashSet<Lekar>();
		return lekari;
	}

	/** @pdGenerated default iterator getter */
	public java.util.Iterator getIteratorLekar() {
		if (lekari == null)
			lekari = new java.util.HashSet<Lekar>();
		return lekari.iterator();
	}

	/**
	 * @pdGenerated default setter
	 * @param newLekar
	 */
	public void setLekar(java.util.Collection<Lekar> newLekar) {
		removeAllLekar();
		for (java.util.Iterator iter = newLekar.iterator(); iter.hasNext();)
			addLekar((Lekar) iter.next());
	}

	/**
	 * @pdGenerated default add
	 * @param newLekar
	 */
	public void addLekar(Lekar newLekar) {
		if (newLekar == null)
			return;
		if (this.lekari == null)
			this.lekari = new java.util.HashSet<Lekar>();
		if (!this.lekari.contains(newLekar))
			this.lekari.add(newLekar);
	}

	/**
	 * @pdGenerated default remove
	 * @param oldLekar
	 */
	public void removeLekar(Lekar oldLekar) {
		if (oldLekar == null)
			return;
		if (this.lekari != null)
			if (this.lekari.contains(oldLekar))
				this.lekari.remove(oldLekar);
	}

	/** @pdGenerated default removeAll */
	public void removeAllLekar() {
		if (lekari != null)
			lekari.clear();
	}

}