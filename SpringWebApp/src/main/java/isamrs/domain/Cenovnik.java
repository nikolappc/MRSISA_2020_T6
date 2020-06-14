/***********************************************************************
 * Module:  Cenovnik.java
 * Author:  Dusan
 * Purpose: Defines the Class Cenovnik
 ***********************************************************************/

package isamrs.domain;

import java.util.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "cenovnici")
public class Cenovnik {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@OneToMany(mappedBy = "cenovnik", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	public Collection<StavkaCenovnika> stavkaCenovnika = new HashSet<StavkaCenovnika>();

	public Collection<StavkaCenovnika> getStavkaCenovnika() {
		if (stavkaCenovnika == null)
			stavkaCenovnika = new HashSet<StavkaCenovnika>();
		return stavkaCenovnika;
	}

	
	public void setStavkaCenovnika(java.util.Collection<StavkaCenovnika> newStavkaCenovnika) {
		removeAllStavkaCenovnika();
		for (java.util.Iterator iter = newStavkaCenovnika.iterator(); iter.hasNext();)
			addStavkaCenovnika((StavkaCenovnika) iter.next());
	}

	
	public void addStavkaCenovnika(StavkaCenovnika newStavkaCenovnika) {
		if (newStavkaCenovnika == null)
			return;
		if (this.stavkaCenovnika == null)
			this.stavkaCenovnika = new java.util.HashSet<StavkaCenovnika>();
		if (!this.stavkaCenovnika.contains(newStavkaCenovnika))
			this.stavkaCenovnika.add(newStavkaCenovnika);
	}

	
	public void removeStavkaCenovnika(StavkaCenovnika oldStavkaCenovnika) {
		if (oldStavkaCenovnika == null)
			return;
		if (this.stavkaCenovnika != null)
			if (this.stavkaCenovnika.contains(oldStavkaCenovnika))
				this.stavkaCenovnika.remove(oldStavkaCenovnika);
	}

	
	public void removeAllStavkaCenovnika() {
		if (stavkaCenovnika != null)
			stavkaCenovnika.clear();
	}

}