/***********************************************************************
 * Module:  MedicinskaSestra.java
 * Author:  Dusan
 * Purpose: Defines the Class MedicinskaSestra
 ***********************************************************************/

package isamrs.domain;

import java.util.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
@Entity
@Table(name = "medicinske_sestre")
public class MedicinskaSestra extends MedicniskoOsoblje {
	@OneToMany
	@Column(name = "id")
   public java.util.Collection<Recepti> overeniRecepti;
   
   
   public MedicinskaSestra() {
		super();
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
   
   /** @pdGenerated default setter
     * @param newOvereniRecepti */
   public void setOvereniRecepti(java.util.Collection<Recepti> newOvereniRecepti) {
      removeAllOvereniRecepti();
      for (java.util.Iterator iter = newOvereniRecepti.iterator(); iter.hasNext();)
         addOvereniRecepti((Recepti)iter.next());
   }
   
   /** @pdGenerated default add
     * @param newRecepti */
   public void addOvereniRecepti(Recepti newRecepti) {
      if (newRecepti == null)
         return;
      if (this.overeniRecepti == null)
         this.overeniRecepti = new java.util.HashSet<Recepti>();
      if (!this.overeniRecepti.contains(newRecepti))
         this.overeniRecepti.add(newRecepti);
   }
   
   /** @pdGenerated default remove
     * @param oldRecepti */
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

}