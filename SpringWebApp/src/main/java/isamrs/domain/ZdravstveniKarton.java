package isamrs.domain;

import java.util.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity(name = "zdravstveni_karton")
public class ZdravstveniKarton {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@OneToMany(mappedBy = "poseta", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	public ArrayList<Poseta> poseta;
	
	@OneToMany(mappedBy = "dijagnoza", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	public ArrayList<Dijagnoza> dijagnoza;
	
	@OneToOne(mappedBy = "zdravstveni_karton")
	public Pacijent pacijent;

	public java.util.Collection<Poseta> getPoseta() {
		if (poseta == null)
			poseta = new ArrayList<Poseta>();
		return poseta;
	}

	public java.util.Iterator getIteratorPoseta() {
		if (poseta == null)
			poseta = new ArrayList<Poseta>();
		return poseta.iterator();
	}

	public void setPoseta(java.util.Collection<Poseta> newPoseta) {
		removeAllPoseta();
		for (java.util.Iterator iter = newPoseta.iterator(); iter.hasNext();)
			addPoseta((Poseta) iter.next());
	}

	public void addPoseta(Poseta newPoseta) {
		if (newPoseta == null)
			return;
		if (this.poseta == null)
			this.poseta = new ArrayList<Poseta>();
		if (!this.poseta.contains(newPoseta)) {
			this.poseta.add(newPoseta);
			newPoseta.setZdravstveniKarton(this);
		}
	}

	public void removePoseta(Poseta oldPoseta) {
		if (oldPoseta == null)
			return;
		if (this.poseta != null)
			if (this.poseta.contains(oldPoseta)) {
				this.poseta.remove(oldPoseta);
				oldPoseta.setZdravstveniKarton((ZdravstveniKarton) null);
			}
	}

	public void removeAllPoseta() {
		if (poseta != null) {
			Poseta oldPoseta;
			for (java.util.Iterator iter = getIteratorPoseta(); iter.hasNext();) {
				oldPoseta = (Poseta) iter.next();
				iter.remove();
				oldPoseta.setZdravstveniKarton((ZdravstveniKarton) null);
			}
		}
	}

	public java.util.Collection<Dijagnoza> getDijagnoza() {
		if (dijagnoza == null)
			dijagnoza = new ArrayList<Dijagnoza>();
		return dijagnoza;
	}

	public java.util.Iterator getIteratorDijagnoza() {
		if (dijagnoza == null)
			dijagnoza = new ArrayList<Dijagnoza>();
		return dijagnoza.iterator();
	}

	public void setDijagnoza(java.util.Collection<Dijagnoza> newDijagnoza) {
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

	public ZdravstveniKarton(Integer id, ArrayList<Poseta> poseta, ArrayList<Dijagnoza> dijagnoza, Pacijent pacijent) {
		super();
		this.id = id;
		this.poseta = poseta;
		this.dijagnoza = dijagnoza;
		this.pacijent = pacijent;
	}

	public ZdravstveniKarton() {
	}

}