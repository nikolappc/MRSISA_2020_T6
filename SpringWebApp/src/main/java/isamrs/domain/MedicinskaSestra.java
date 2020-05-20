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

	public java.util.Collection<Recepti> getOvereniRecepti() {
		if (overeniRecepti == null)
			overeniRecepti = new java.util.HashSet<Recepti>();
		return overeniRecepti;
	}


	public void setOvereniRecepti(java.util.Collection<Recepti> newOvereniRecepti) {
		removeAllOvereniRecepti();
		for (java.util.Iterator iter = newOvereniRecepti.iterator(); iter.hasNext();)
			addOvereniRecepti((Recepti) iter.next());
	}


	public void addOvereniRecepti(Recepti newRecepti) {
		if (newRecepti == null)
			return;
		if (this.overeniRecepti == null)
			this.overeniRecepti = new java.util.HashSet<Recepti>();
		if (!this.overeniRecepti.contains(newRecepti))
			this.overeniRecepti.add(newRecepti);
	}


	public void removeOvereniRecepti(Recepti oldRecepti) {
		if (oldRecepti == null)
			return;
		if (this.overeniRecepti != null)
			if (this.overeniRecepti.contains(oldRecepti))
				this.overeniRecepti.remove(oldRecepti);
	}


	public void removeAllOvereniRecepti() {
		if (overeniRecepti != null)
			overeniRecepti.clear();
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
	
	
	
	
	

}