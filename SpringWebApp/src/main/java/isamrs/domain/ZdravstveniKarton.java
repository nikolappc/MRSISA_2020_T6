/***********************************************************************
 * Module:  ZdravstveniKarton.java
 * Author:  Dusan
 * Purpose: Defines the Class ZdravstveniKarton
 ***********************************************************************/

package isamrs.domain;

import java.util.*;

public class ZdravstveniKarton {
   private Integer id;
   
   public java.util.Collection<Poseta> poseta;
   public java.util.Collection<Dijagnoza> dijagnoza;
   public Pacijent pacijent;
   
   
   /** @pdGenerated default getter */
   public java.util.Collection<Poseta> getPoseta() {
      if (poseta == null)
         poseta = new java.util.HashSet<Poseta>();
      return poseta;
   }
   
   /** @pdGenerated default iterator getter */
   public java.util.Iterator getIteratorPoseta() {
      if (poseta == null)
         poseta = new java.util.HashSet<Poseta>();
      return poseta.iterator();
   }
   
   /** @pdGenerated default setter
     * @param newPoseta */
   public void setPoseta(java.util.Collection<Poseta> newPoseta) {
      removeAllPoseta();
      for (java.util.Iterator iter = newPoseta.iterator(); iter.hasNext();)
         addPoseta((Poseta)iter.next());
   }
   
   /** @pdGenerated default add
     * @param newPoseta */
   public void addPoseta(Poseta newPoseta) {
      if (newPoseta == null)
         return;
      if (this.poseta == null)
         this.poseta = new java.util.HashSet<Poseta>();
      if (!this.poseta.contains(newPoseta))
      {
         this.poseta.add(newPoseta);
         newPoseta.setZdravstveniKarton(this);      
      }
   }
   
   /** @pdGenerated default remove
     * @param oldPoseta */
   public void removePoseta(Poseta oldPoseta) {
      if (oldPoseta == null)
         return;
      if (this.poseta != null)
         if (this.poseta.contains(oldPoseta))
         {
            this.poseta.remove(oldPoseta);
            oldPoseta.setZdravstveniKarton((ZdravstveniKarton)null);
         }
   }
   
   /** @pdGenerated default removeAll */
   public void removeAllPoseta() {
      if (poseta != null)
      {
         Poseta oldPoseta;
         for (java.util.Iterator iter = getIteratorPoseta(); iter.hasNext();)
         {
            oldPoseta = (Poseta)iter.next();
            iter.remove();
            oldPoseta.setZdravstveniKarton((ZdravstveniKarton)null);
         }
      }
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
   /** @pdGenerated default parent getter */
   public Pacijent getPacijent() {
      return pacijent;
   }
   
   /** @pdGenerated default parent setter
     * @param newPacijent */
   public void setPacijent(Pacijent newPacijent) {
      if (this.pacijent == null || !this.pacijent.equals(newPacijent))
      {
         if (this.pacijent != null)
            this.pacijent.setZdravstveniKarton(null);
         this.pacijent = newPacijent;
         if (this.pacijent != null)
            this.pacijent.setZdravstveniKarton(this);
      }
   }

}