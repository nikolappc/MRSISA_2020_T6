/***********************************************************************
 * Module:  Pregled.java
 * Author:  Dusan
 * Purpose: Defines the Class Pregled
 ***********************************************************************/

package isamrs.domain;

import java.util.*;

public class Pregled extends Poseta {
   public java.util.Collection<Recepti> recepti;
   public Lekar lekar;
   public java.util.Collection<Dijagnoza> dijagnoza;
   
   
   /** @pdGenerated default getter */
   public java.util.Collection<Recepti> getRecepti() {
      if (recepti == null)
         recepti = new java.util.HashSet<Recepti>();
      return recepti;
   }
   
   /** @pdGenerated default iterator getter */
   public java.util.Iterator getIteratorRecepti() {
      if (recepti == null)
         recepti = new java.util.HashSet<Recepti>();
      return recepti.iterator();
   }
   
   /** @pdGenerated default setter
     * @param newRecepti */
   public void setRecepti(java.util.Collection<Recepti> newRecepti) {
      removeAllRecepti();
      for (java.util.Iterator iter = newRecepti.iterator(); iter.hasNext();)
         addRecepti((Recepti)iter.next());
   }
   
   /** @pdGenerated default add
     * @param newRecepti */
   public void addRecepti(Recepti newRecepti) {
      if (newRecepti == null)
         return;
      if (this.recepti == null)
         this.recepti = new java.util.HashSet<Recepti>();
      if (!this.recepti.contains(newRecepti))
         this.recepti.add(newRecepti);
   }
   
   /** @pdGenerated default remove
     * @param oldRecepti */
   public void removeRecepti(Recepti oldRecepti) {
      if (oldRecepti == null)
         return;
      if (this.recepti != null)
         if (this.recepti.contains(oldRecepti))
            this.recepti.remove(oldRecepti);
   }
   
   /** @pdGenerated default removeAll */
   public void removeAllRecepti() {
      if (recepti != null)
         recepti.clear();
   }
   /** @pdGenerated default parent getter */
   public Lekar getLekar() {
      return lekar;
   }
   
   /** @pdGenerated default parent setter
     * @param newLekar */
   public void setLekar(Lekar newLekar) {
      this.lekar = newLekar;
   }
   /** @pdGenerated default getter */
   public java.util.Collection<Dijagnoza> getDijagnoza() {
      if (dijagnoza == null)
         dijagnoza = new java.util.HashSet<Dijagnoza>();
      return dijagnoza;
   }
   
   /** @pdGenerated default iterator getter */
   public java.util.Iterator getIteratorDijagnoza() {
      if (dijagnoza == null)
         dijagnoza = new java.util.HashSet<Dijagnoza>();
      return dijagnoza.iterator();
   }
   
   /** @pdGenerated default setter
     * @param newDijagnoza */
   public void setDijagnoza(java.util.Collection<Dijagnoza> newDijagnoza) {
      removeAllDijagnoza();
      for (java.util.Iterator iter = newDijagnoza.iterator(); iter.hasNext();)
         addDijagnoza((Dijagnoza)iter.next());
   }
   
   /** @pdGenerated default add
     * @param newDijagnoza */
   public void addDijagnoza(Dijagnoza newDijagnoza) {
      if (newDijagnoza == null)
         return;
      if (this.dijagnoza == null)
         this.dijagnoza = new java.util.HashSet<Dijagnoza>();
      if (!this.dijagnoza.contains(newDijagnoza))
         this.dijagnoza.add(newDijagnoza);
   }
   
   /** @pdGenerated default remove
     * @param oldDijagnoza */
   public void removeDijagnoza(Dijagnoza oldDijagnoza) {
      if (oldDijagnoza == null)
         return;
      if (this.dijagnoza != null)
         if (this.dijagnoza.contains(oldDijagnoza))
            this.dijagnoza.remove(oldDijagnoza);
   }
   
   /** @pdGenerated default removeAll */
   public void removeAllDijagnoza() {
      if (dijagnoza != null)
         dijagnoza.clear();
   }

}