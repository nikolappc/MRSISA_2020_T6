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
import javax.persistence.OneToMany;
import javax.persistence.Table;
@Entity
@Table(name = "lekari")
public class Lekar extends MedicniskoOsoblje {
	@OneToMany
	@JoinColumn(name = "id")
	public java.util.Collection<Ocena> ocena;
	@OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY,mappedBy = "lekar")
	public Collection<Pregled> pregled;
   
   
   public Lekar() {
		super();
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
   
   /** @pdGenerated default setter
     * @param newOcena */
   public void setOcena(java.util.Collection<Ocena> newOcena) {
      removeAllOcena();
      for (java.util.Iterator iter = newOcena.iterator(); iter.hasNext();)
         addOcena((Ocena)iter.next());
   }
   
   /** @pdGenerated default add
     * @param newOcena */
   public void addOcena(Ocena newOcena) {
      if (newOcena == null)
         return;
      if (this.ocena == null)
         this.ocena = new java.util.HashSet<Ocena>();
      if (!this.ocena.contains(newOcena))
         this.ocena.add(newOcena);
   }
   
   /** @pdGenerated default remove
     * @param oldOcena */
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
   
   /** @pdGenerated default setter
     * @param newPregled */
   public void setPregled(Collection<Pregled> newPregled) {
      this.pregled = newPregled;
   }

public Integer getId() {
	// TODO Auto-generated method stub
	return null;
}

public void setId(Long id) {
	// TODO Auto-generated method stub
	
}

}