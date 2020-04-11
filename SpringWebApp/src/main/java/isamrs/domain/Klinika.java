/***********************************************************************
 * Module:  Klinika.java
 * Author:  Dusan
 * Purpose: Defines the Class Klinika
 ***********************************************************************/

package isamrs.domain;

import java.util.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
@Entity
@Table(name = "klinike")
public class Klinika {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id", unique = true, nullable = false)
	private Integer id;
	@Column(name = "naziv", unique = true, nullable = false)
   private String naziv;
	@Column(name = "adresa", unique = false, nullable = false)
   private String adresa;
	@Column(name = "opis", unique = false, nullable = false)
   private String opis;
	@OneToOne
	public Cenovnik cenovnik;
	@OneToOne
	public TipKlinike tipKlinike;
   
	@OneToMany
	@JoinColumn(name = "id")
	public java.util.Collection<Lekar> lekari;
	@OneToMany
	@JoinColumn(name = "id")
	public java.util.Collection<MedicinskaSestra> sestre;
	/*@OneToMany
	@JoinColumn(name = "id")
	public java.util.Collection<MedicniskoOsoblje> medicniskoOsoblje;*/
	@OneToMany
	@JoinColumn(name = "id")
	public java.util.Collection<Sala> sala;
	@OneToMany
	@JoinColumn(name = "id")
   public java.util.Collection<Pacijent> pacijent;
	@OneToMany
	@JoinColumn(name = "id")
   public java.util.Collection<Poseta> poseta;
	@OneToOne
	@JoinColumn(name = "id")
   public java.util.Collection<TipPosete> tipPosete;
	@OneToMany
	@JoinColumn(name = "id")
   public java.util.Collection<Ocena> ocena;
	@OneToMany
	@JoinColumn(name = "id")
   public java.util.Collection<AdministratorKlinike> administratorKlinike;
/*
	@OneToOne
	@JoinColumn(name = "id")
   public TipKlinike tipKlinike;
*/
   
   
   public Klinika() {
		super();
	}

  /* public java.util.Collection<MedicniskoOsoblje> getMedicniskoOsoblje() {
      if (medicniskoOsoblje == null)
         medicniskoOsoblje = new java.util.HashSet<MedicniskoOsoblje>();
      return medicniskoOsoblje;
   }
   
   public java.util.Iterator getIteratorMedicniskoOsoblje() {
      if (medicniskoOsoblje == null)
         medicniskoOsoblje = new java.util.HashSet<MedicniskoOsoblje>();
      return medicniskoOsoblje.iterator();
   }
   
   public void setMedicniskoOsoblje(java.util.Collection<MedicniskoOsoblje> newMedicniskoOsoblje) {
      removeAllMedicniskoOsoblje();
      for (java.util.Iterator iter = newMedicniskoOsoblje.iterator(); iter.hasNext();)
         addMedicniskoOsoblje((MedicniskoOsoblje)iter.next());
   }
   
   
   public void addMedicniskoOsoblje(MedicniskoOsoblje newMedicniskoOsoblje) {
      if (newMedicniskoOsoblje == null)
         return;
      if (this.medicniskoOsoblje == null)
         this.medicniskoOsoblje = new java.util.HashSet<MedicniskoOsoblje>();
      if (!this.medicniskoOsoblje.contains(newMedicniskoOsoblje))
         this.medicniskoOsoblje.add(newMedicniskoOsoblje);
   }
   
   
   public void removeMedicniskoOsoblje(MedicniskoOsoblje oldMedicniskoOsoblje) {
      if (oldMedicniskoOsoblje == null)
         return;
      if (this.medicniskoOsoblje != null)
         if (this.medicniskoOsoblje.contains(oldMedicniskoOsoblje))
            this.medicniskoOsoblje.remove(oldMedicniskoOsoblje);
   }
   
   public void removeAllMedicniskoOsoblje() {
      if (medicniskoOsoblje != null)
         medicniskoOsoblje.clear();
   }*/
   
   /** @pdGenerated default getter */
   public java.util.Collection<Sala> getSala() {
      if (sala == null)
         sala = new java.util.HashSet<Sala>();
      return sala;
   }
   
   /** @pdGenerated default iterator getter */
   public java.util.Iterator getIteratorSala() {
      if (sala == null)
         sala = new java.util.HashSet<Sala>();
      return sala.iterator();
   }
   
   /** @pdGenerated default setter
     * @param newSala */
   public void setSala(java.util.Collection<Sala> newSala) {
      removeAllSala();
      for (java.util.Iterator iter = newSala.iterator(); iter.hasNext();)
         addSala((Sala)iter.next());
   }
   
   /** @pdGenerated default add
     * @param newSala */
   public void addSala(Sala newSala) {
      if (newSala == null)
         return;
      if (this.sala == null)
         this.sala = new java.util.HashSet<Sala>();
      if (!this.sala.contains(newSala))
         this.sala.add(newSala);
   }
   
   /** @pdGenerated default remove
     * @param oldSala */
   public void removeSala(Sala oldSala) {
      if (oldSala == null)
         return;
      if (this.sala != null)
         if (this.sala.contains(oldSala))
            this.sala.remove(oldSala);
   }
   
   /** @pdGenerated default removeAll */
   public void removeAllSala() {
      if (sala != null)
         sala.clear();
   }
   /** @pdGenerated default getter */
   public java.util.Collection<Pacijent> getPacijent() {
      if (pacijent == null)
         pacijent = new java.util.HashSet<Pacijent>();
      return pacijent;
   }
   
   /** @pdGenerated default iterator getter */
   public java.util.Iterator getIteratorPacijent() {
      if (pacijent == null)
         pacijent = new java.util.HashSet<Pacijent>();
      return pacijent.iterator();
   }
   
   /** @pdGenerated default setter
     * @param newPacijent */
   public void setPacijent(java.util.Collection<Pacijent> newPacijent) {
      removeAllPacijent();
      for (java.util.Iterator iter = newPacijent.iterator(); iter.hasNext();)
         addPacijent((Pacijent)iter.next());
   }
   
   /** @pdGenerated default add
     * @param newPacijent */
   public void addPacijent(Pacijent newPacijent) {
      if (newPacijent == null)
         return;
      if (this.pacijent == null)
         this.pacijent = new java.util.HashSet<Pacijent>();
      if (!this.pacijent.contains(newPacijent))
         this.pacijent.add(newPacijent);
   }
   
   /** @pdGenerated default remove
     * @param oldPacijent */
   public void removePacijent(Pacijent oldPacijent) {
      if (oldPacijent == null)
         return;
      if (this.pacijent != null)
         if (this.pacijent.contains(oldPacijent))
            this.pacijent.remove(oldPacijent);
   }
   
   /** @pdGenerated default removeAll */
   public void removeAllPacijent() {
      if (pacijent != null)
         pacijent.clear();
   }
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
         this.poseta.add(newPoseta);
   }
   
   /** @pdGenerated default remove
     * @param oldPoseta */
   public void removePoseta(Poseta oldPoseta) {
      if (oldPoseta == null)
         return;
      if (this.poseta != null)
         if (this.poseta.contains(oldPoseta))
            this.poseta.remove(oldPoseta);
   }
   
   /** @pdGenerated default removeAll */
   public void removeAllPoseta() {
      if (poseta != null)
         poseta.clear();
   }
   /** @pdGenerated default parent getter */
   public Cenovnik getCenovnik() {
      return cenovnik;
   }
   
   /** @pdGenerated default parent setter
     * @param newCenovnik */
   public void setCenovnik(Cenovnik newCenovnik) {
      this.cenovnik = newCenovnik;
   }
   /** @pdGenerated default getter */
   public java.util.Collection<TipPosete> getTipPosete() {
      if (tipPosete == null)
         tipPosete = new java.util.HashSet<TipPosete>();
      return tipPosete;
   }
   
   /** @pdGenerated default iterator getter */
   public java.util.Iterator getIteratorTipPosete() {
      if (tipPosete == null)
         tipPosete = new java.util.HashSet<TipPosete>();
      return tipPosete.iterator();
   }
   
   /** @pdGenerated default setter
     * @param newTipPosete */
   public void setTipPosete(java.util.Collection<TipPosete> newTipPosete) {
      removeAllTipPosete();
      for (java.util.Iterator iter = newTipPosete.iterator(); iter.hasNext();)
         addTipPosete((TipPosete)iter.next());
   }
   
   /** @pdGenerated default add
     * @param newTipPosete */
   public void addTipPosete(TipPosete newTipPosete) {
      if (newTipPosete == null)
         return;
      if (this.tipPosete == null)
         this.tipPosete = new java.util.HashSet<TipPosete>();
      if (!this.tipPosete.contains(newTipPosete))
         this.tipPosete.add(newTipPosete);
   }
   
   /** @pdGenerated default remove
     * @param oldTipPosete */
   public void removeTipPosete(TipPosete oldTipPosete) {
      if (oldTipPosete == null)
         return;
      if (this.tipPosete != null)
         if (this.tipPosete.contains(oldTipPosete))
            this.tipPosete.remove(oldTipPosete);
   }
   
   /** @pdGenerated default removeAll */
   public void removeAllTipPosete() {
      if (tipPosete != null)
         tipPosete.clear();
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
   public java.util.Collection<AdministratorKlinike> getAdministratorKlinike() {
      if (administratorKlinike == null)
         administratorKlinike = new java.util.HashSet<AdministratorKlinike>();
      return administratorKlinike;
   }
   
   /** @pdGenerated default iterator getter */
   public java.util.Iterator getIteratorAdministratorKlinike() {
      if (administratorKlinike == null)
         administratorKlinike = new java.util.HashSet<AdministratorKlinike>();
      return administratorKlinike.iterator();
   }
   
   /** @pdGenerated default setter
     * @param newAdministratorKlinike */
   public void setAdministratorKlinike(java.util.Collection<AdministratorKlinike> newAdministratorKlinike) {
      removeAllAdministratorKlinike();
      for (java.util.Iterator iter = newAdministratorKlinike.iterator(); iter.hasNext();)
         addAdministratorKlinike((AdministratorKlinike)iter.next());
   }
   
   /** @pdGenerated default add
     * @param newAdministratorKlinike */
   public void addAdministratorKlinike(AdministratorKlinike newAdministratorKlinike) {
      if (newAdministratorKlinike == null)
         return;
      if (this.administratorKlinike == null)
         this.administratorKlinike = new java.util.HashSet<AdministratorKlinike>();
      if (!this.administratorKlinike.contains(newAdministratorKlinike))
         this.administratorKlinike.add(newAdministratorKlinike);
   }
   
   /** @pdGenerated default remove
     * @param oldAdministratorKlinike */
   public void removeAdministratorKlinike(AdministratorKlinike oldAdministratorKlinike) {
      if (oldAdministratorKlinike == null)
         return;
      if (this.administratorKlinike != null)
         if (this.administratorKlinike.contains(oldAdministratorKlinike))
            this.administratorKlinike.remove(oldAdministratorKlinike);
   }
   
   /** @pdGenerated default removeAll */
   public void removeAllAdministratorKlinike() {
      if (administratorKlinike != null)
         administratorKlinike.clear();
   }
   /** @pdGenerated default parent getter */
   public TipKlinike getTipKlinike() {
      return tipKlinike;
   }
   
   /** @pdGenerated default parent setter
     * @param newTipKlinike */
   public void setTipKlinike(TipKlinike newTipKlinike) {
      this.tipKlinike = newTipKlinike;
   }

}