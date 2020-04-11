/***********************************************************************
 * Module:  MedicniskoOsoblje.java
 * Author:  Dusan
 * Purpose: Defines the Class MedicniskoOsoblje
 ***********************************************************************/

package isamrs.domain;

import java.util.*;

import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
@MappedSuperclass
public abstract class MedicniskoOsoblje extends Osoba {
	@OneToMany
	@JoinColumn(name = "id")
   public java.util.Collection<GodisnjiOdmor> godisnjiOdmor;
	@OneToMany
	@JoinColumn(name = "id")
   public RadnoVreme[] radnoVreme;
   
   
   /** @pdGenerated default getter */
   public java.util.Collection<GodisnjiOdmor> getGodisnjiOdmor() {
      if (godisnjiOdmor == null)
         godisnjiOdmor = new java.util.HashSet<GodisnjiOdmor>();
      return godisnjiOdmor;
   }
   
   /** @pdGenerated default iterator getter */
   public java.util.Iterator getIteratorGodisnjiOdmor() {
      if (godisnjiOdmor == null)
         godisnjiOdmor = new java.util.HashSet<GodisnjiOdmor>();
      return godisnjiOdmor.iterator();
   }
   
   /** @pdGenerated default setter
     * @param newGodisnjiOdmor */
   public void setGodisnjiOdmor(java.util.Collection<GodisnjiOdmor> newGodisnjiOdmor) {
      removeAllGodisnjiOdmor();
      for (java.util.Iterator iter = newGodisnjiOdmor.iterator(); iter.hasNext();)
         addGodisnjiOdmor((GodisnjiOdmor)iter.next());
   }
   
   /** @pdGenerated default add
     * @param newGodisnjiOdmor */
   public void addGodisnjiOdmor(GodisnjiOdmor newGodisnjiOdmor) {
      if (newGodisnjiOdmor == null)
         return;
      if (this.godisnjiOdmor == null)
         this.godisnjiOdmor = new java.util.HashSet<GodisnjiOdmor>();
      if (!this.godisnjiOdmor.contains(newGodisnjiOdmor))
         this.godisnjiOdmor.add(newGodisnjiOdmor);
   }
   
   /** @pdGenerated default remove
     * @param oldGodisnjiOdmor */
   public void removeGodisnjiOdmor(GodisnjiOdmor oldGodisnjiOdmor) {
      if (oldGodisnjiOdmor == null)
         return;
      if (this.godisnjiOdmor != null)
         if (this.godisnjiOdmor.contains(oldGodisnjiOdmor))
            this.godisnjiOdmor.remove(oldGodisnjiOdmor);
   }
   
   /** @pdGenerated default removeAll */
   public void removeAllGodisnjiOdmor() {
      if (godisnjiOdmor != null)
         godisnjiOdmor.clear();
   }
   /** @pdGenerated default getter */
   public RadnoVreme[] getRadnoVreme() {
      return radnoVreme;
   }
   
   /** @pdGenerated default setter
     * @param newRadnoVreme */
   public void setRadnoVreme(RadnoVreme[] newRadnoVreme) {
      this.radnoVreme = newRadnoVreme;
   }

}