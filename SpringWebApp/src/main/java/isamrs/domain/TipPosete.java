package isamrs.domain;

import java.util.*;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class TipPosete {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "tip", unique = true, nullable = false)
	private Tip tip;

	@OneToMany(mappedBy = "tipPosete", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	public ArrayList<StavkaCenovnika> stavkaCenovnika;

	public ArrayList<StavkaCenovnika> getStavkaCenovnika() {
		if (stavkaCenovnika == null)
			stavkaCenovnika = new ArrayList<StavkaCenovnika>();
		return stavkaCenovnika;
	}

	public java.util.Iterator getIteratorStavkaCenovnika() {
		if (stavkaCenovnika == null)
			stavkaCenovnika = new ArrayList<StavkaCenovnika>();
		return stavkaCenovnika.iterator();
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
			this.stavkaCenovnika = new ArrayList<StavkaCenovnika>();
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

	public TipPosete(int id, Tip tip, ArrayList<StavkaCenovnika> stavkaCenovnika) {
		super();
		this.id = id;
		this.tip = tip;
		this.stavkaCenovnika = stavkaCenovnika;
	}

	public TipPosete() {
	}

}
