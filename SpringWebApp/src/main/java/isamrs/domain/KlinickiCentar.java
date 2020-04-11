/***********************************************************************
 * Module:  KlinickiCentar.java
 * Author:  Dusan
 * Purpose: Defines the Class KlinickiCentar
 ***********************************************************************/

package isamrs.domain;

import java.util.*;

public class KlinickiCentar {
   public java.util.Collection<Klinika> klinika;
   public AdministratorKlinickogCentra administratorKlinickogCentra;
   public java.util.Collection<TipKlinike> tipKlinike;
   public java.util.Collection<Lek> šifrarnikLekova;
   public java.util.Collection<Dijagnoza> šifrarnikDijagnoza;
   
   
   /** @pdGenerated default getter */
   public java.util.Collection<Klinika> getKlinika() {
      if (klinika == null)
         klinika = new java.util.HashSet<Klinika>();
      return klinika;
   }
   
   /** @pdGenerated default iterator getter */
   public java.util.Iterator getIteratorKlinika() {
      if (klinika == null)
         klinika = new java.util.HashSet<Klinika>();
      return klinika.iterator();
   }
   
   /** @pdGenerated default setter
     * @param newKlinika */
   public void setKlinika(java.util.Collection<Klinika> newKlinika) {
      removeAllKlinika();
      for (java.util.Iterator iter = newKlinika.iterator(); iter.hasNext();)
         addKlinika((Klinika)iter.next());
   }
   
   /** @pdGenerated default add
     * @param newKlinika */
   public void addKlinika(Klinika newKlinika) {
      if (newKlinika == null)
         return;
      if (this.klinika == null)
         this.klinika = new java.util.HashSet<Klinika>();
      if (!this.klinika.contains(newKlinika))
         this.klinika.add(newKlinika);
   }
   
   /** @pdGenerated default remove
     * @param oldKlinika */
   public void removeKlinika(Klinika oldKlinika) {
      if (oldKlinika == null)
         return;
      if (this.klinika != null)
         if (this.klinika.contains(oldKlinika))
            this.klinika.remove(oldKlinika);
   }
   
   /** @pdGenerated default removeAll */
   public void removeAllKlinika() {
      if (klinika != null)
         klinika.clear();
   }
   /** @pdGenerated default parent getter */
   public AdministratorKlinickogCentra getAdministratorKlinickogCentra() {
      return administratorKlinickogCentra;
   }
   
   /** @pdGenerated default parent setter
     * @param newAdministratorKlinickogCentra */
   public void setAdministratorKlinickogCentra(AdministratorKlinickogCentra newAdministratorKlinickogCentra) {
      this.administratorKlinickogCentra = newAdministratorKlinickogCentra;
   }
   /** @pdGenerated default getter */
   public java.util.Collection<TipKlinike> getTipKlinike() {
      if (tipKlinike == null)
         tipKlinike = new java.util.HashSet<TipKlinike>();
      return tipKlinike;
   }
   
   /** @pdGenerated default iterator getter */
   public java.util.Iterator getIteratorTipKlinike() {
      if (tipKlinike == null)
         tipKlinike = new java.util.HashSet<TipKlinike>();
      return tipKlinike.iterator();
   }
   
   /** @pdGenerated default setter
     * @param newTipKlinike */
   public void setTipKlinike(java.util.Collection<TipKlinike> newTipKlinike) {
      removeAllTipKlinike();
      for (java.util.Iterator iter = newTipKlinike.iterator(); iter.hasNext();)
         addTipKlinike((TipKlinike)iter.next());
   }
   
   /** @pdGenerated default add
     * @param newTipKlinike */
   public void addTipKlinike(TipKlinike newTipKlinike) {
      if (newTipKlinike == null)
         return;
      if (this.tipKlinike == null)
         this.tipKlinike = new java.util.HashSet<TipKlinike>();
      if (!this.tipKlinike.contains(newTipKlinike))
         this.tipKlinike.add(newTipKlinike);
   }
   
   /** @pdGenerated default remove
     * @param oldTipKlinike */
   public void removeTipKlinike(TipKlinike oldTipKlinike) {
      if (oldTipKlinike == null)
         return;
      if (this.tipKlinike != null)
         if (this.tipKlinike.contains(oldTipKlinike))
            this.tipKlinike.remove(oldTipKlinike);
   }
   
   /** @pdGenerated default removeAll */
   public void removeAllTipKlinike() {
      if (tipKlinike != null)
         tipKlinike.clear();
   }
   /** @pdGenerated default getter */
   public java.util.Collection<Lek> getšifrarnikLekova() {
      if (šifrarnikLekova == null)
         šifrarnikLekova = new java.util.HashSet<Lek>();
      return šifrarnikLekova;
   }
   
   /** @pdGenerated default iterator getter */
   public java.util.Iterator getIteratoršifrarnikLekova() {
      if (šifrarnikLekova == null)
         šifrarnikLekova = new java.util.HashSet<Lek>();
      return šifrarnikLekova.iterator();
   }
   
   /** @pdGenerated default setter
     * @param newšifrarnikLekova */
   public void setšifrarnikLekova(java.util.Collection<Lek> newšifrarnikLekova) {
      removeAllšifrarnikLekova();
      for (java.util.Iterator iter = newšifrarnikLekova.iterator(); iter.hasNext();)
         addšifrarnikLekova((Lek)iter.next());
   }
   
   /** @pdGenerated default add
     * @param newLek */
   public void addšifrarnikLekova(Lek newLek) {
      if (newLek == null)
         return;
      if (this.šifrarnikLekova == null)
         this.šifrarnikLekova = new java.util.HashSet<Lek>();
      if (!this.šifrarnikLekova.contains(newLek))
         this.šifrarnikLekova.add(newLek);
   }
   
   /** @pdGenerated default remove
     * @param oldLek */
   public void removešifrarnikLekova(Lek oldLek) {
      if (oldLek == null)
         return;
      if (this.šifrarnikLekova != null)
         if (this.šifrarnikLekova.contains(oldLek))
            this.šifrarnikLekova.remove(oldLek);
   }
   
   /** @pdGenerated default removeAll */
   public void removeAllšifrarnikLekova() {
      if (šifrarnikLekova != null)
         šifrarnikLekova.clear();
   }
   /** @pdGenerated default getter */
   public java.util.Collection<Dijagnoza> getšifrarnikDijagnoza() {
      if (šifrarnikDijagnoza == null)
         šifrarnikDijagnoza = new java.util.HashSet<Dijagnoza>();
      return šifrarnikDijagnoza;
   }
   
   /** @pdGenerated default iterator getter */
   public java.util.Iterator getIteratoršifrarnikDijagnoza() {
      if (šifrarnikDijagnoza == null)
         šifrarnikDijagnoza = new java.util.HashSet<Dijagnoza>();
      return šifrarnikDijagnoza.iterator();
   }
   
   /** @pdGenerated default setter
     * @param newšifrarnikDijagnoza */
   public void setšifrarnikDijagnoza(java.util.Collection<Dijagnoza> newšifrarnikDijagnoza) {
      removeAllšifrarnikDijagnoza();
      for (java.util.Iterator iter = newšifrarnikDijagnoza.iterator(); iter.hasNext();)
         addšifrarnikDijagnoza((Dijagnoza)iter.next());
   }
   
   /** @pdGenerated default add
     * @param newDijagnoza */
   public void addšifrarnikDijagnoza(Dijagnoza newDijagnoza) {
      if (newDijagnoza == null)
         return;
      if (this.šifrarnikDijagnoza == null)
         this.šifrarnikDijagnoza = new java.util.HashSet<Dijagnoza>();
      if (!this.šifrarnikDijagnoza.contains(newDijagnoza))
         this.šifrarnikDijagnoza.add(newDijagnoza);
   }
   
   /** @pdGenerated default remove
     * @param oldDijagnoza */
   public void removešifrarnikDijagnoza(Dijagnoza oldDijagnoza) {
      if (oldDijagnoza == null)
         return;
      if (this.šifrarnikDijagnoza != null)
         if (this.šifrarnikDijagnoza.contains(oldDijagnoza))
            this.šifrarnikDijagnoza.remove(oldDijagnoza);
   }
   
   /** @pdGenerated default removeAll */
   public void removeAllšifrarnikDijagnoza() {
      if (šifrarnikDijagnoza != null)
         šifrarnikDijagnoza.clear();
   }

}