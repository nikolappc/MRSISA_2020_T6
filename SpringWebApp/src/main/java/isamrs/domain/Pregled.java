package isamrs.domain;

import java.util.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "pregled")
public class Pregled extends Poseta {

	@OneToMany(mappedBy = "pregled", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	public Collection<Recepti> recepti;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name="id_lekara")
	public Lekar lekar;

	@OneToMany(mappedBy = "pregled", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	public Collection<Dijagnoza> dijagnoza;

	public Collection<Recepti> getRecepti() {
		if (recepti == null)
			recepti = new ArrayList<Recepti>();
		return recepti;
	}

	public java.util.Iterator getIteratorRecepti() {
		if (recepti == null)
			recepti = new ArrayList<Recepti>();
		return recepti.iterator();
	}

	public void setRecepti(Collection<Recepti> newRecepti) {
		removeAllRecepti();
		for (java.util.Iterator iter = newRecepti.iterator(); iter.hasNext();)
			addRecepti((Recepti) iter.next());
	}

	public void addRecepti(Recepti newRecepti) {
		if (newRecepti == null)
			return;
		if (this.recepti == null)
			this.recepti = new ArrayList<Recepti>();
		if (!this.recepti.contains(newRecepti))
			this.recepti.add(newRecepti);
	}

	public void removeRecepti(Recepti oldRecepti) {
		if (oldRecepti == null)
			return;
		if (this.recepti != null)
			if (this.recepti.contains(oldRecepti))
				this.recepti.remove(oldRecepti);
	}

	public void removeAllRecepti() {
		if (recepti != null)
			recepti.clear();
	}

	public Lekar getLekar() {
		return lekar;
	}

	public void setLekar(Lekar newLekar) {
		this.lekar = newLekar;
	}

	public Collection<Dijagnoza> getDijagnoza() {
		if (dijagnoza == null)
			dijagnoza = new ArrayList<Dijagnoza>();
		return dijagnoza;
	}

	public java.util.Iterator getIteratorDijagnoza() {
		if (dijagnoza == null)
			dijagnoza = new ArrayList<Dijagnoza>();
		return dijagnoza.iterator();
	}

	public void setDijagnoza(Collection<Dijagnoza> newDijagnoza) {
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

	public Pregled(String opis, Integer id, Termin termin, TipPosete tipPosete, ZdravstveniKarton zdravstveniKarton,
			Sala sala, Collection<Recepti> recepti, Lekar lekar, Collection<Dijagnoza> dijagnoza) {
		super(opis, id, termin, tipPosete, zdravstveniKarton, sala);
		this.recepti = recepti;
		this.lekar = lekar;
		this.dijagnoza = dijagnoza;
	}

	public Pregled() {
	}

}