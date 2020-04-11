/***********************************************************************
 * Module:  Lekar.java
 * Author:  Dusan
 * Purpose: Defines the Class Lekar
 ***********************************************************************/

package isamrs.domain;

import java.util.*;

public class Lekar extends MedicniskoOsoblje {
   public java.util.Collection<Ocena> ocena;
   public Pregled[] pregled;
   
   
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
   public Pregled[] getPregled() {
      return pregled;
   }
   
   /** @pdGenerated default setter
     * @param newPregled */
   public void setPregled(Pregled[] newPregled) {
      this.pregled = newPregled;
   }

}