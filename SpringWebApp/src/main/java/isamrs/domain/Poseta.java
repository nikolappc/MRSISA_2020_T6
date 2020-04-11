/***********************************************************************
 * Module:  Poseta.java
 * Author:  Dusan
 * Purpose: Defines the Class Poseta
 ***********************************************************************/

package isamrs.domain;

import java.util.*;

public class Poseta {
   private String opis;
   private Integer id;
   
   public Termin termin;
   public TipPosete tipPosete;
   public ZdravstveniKarton zdravstveniKarton;
   public Sala sala;
   
   
   /** @pdGenerated default parent getter */
   public Termin getTermin() {
      return termin;
   }
   
   /** @pdGenerated default parent setter
     * @param newTermin */
   public void setTermin(Termin newTermin) {
      this.termin = newTermin;
   }
   /** @pdGenerated default parent getter */
   public TipPosete getTipPosete() {
      return tipPosete;
   }
   
   /** @pdGenerated default parent setter
     * @param newTipPosete */
   public void setTipPosete(TipPosete newTipPosete) {
      this.tipPosete = newTipPosete;
   }
   /** @pdGenerated default parent getter */
   public ZdravstveniKarton getZdravstveniKarton() {
      return zdravstveniKarton;
   }
   
   /** @pdGenerated default parent setter
     * @param newZdravstveniKarton */
   public void setZdravstveniKarton(ZdravstveniKarton newZdravstveniKarton) {
      if (this.zdravstveniKarton == null || !this.zdravstveniKarton.equals(newZdravstveniKarton))
      {
         if (this.zdravstveniKarton != null)
         {
            ZdravstveniKarton oldZdravstveniKarton = this.zdravstveniKarton;
            this.zdravstveniKarton = null;
            oldZdravstveniKarton.removePoseta(this);
         }
         if (newZdravstveniKarton != null)
         {
            this.zdravstveniKarton = newZdravstveniKarton;
            this.zdravstveniKarton.addPoseta(this);
         }
      }
   }
   /** @pdGenerated default parent getter */
   public Sala getSala() {
      return sala;
   }
   
   /** @pdGenerated default parent setter
     * @param newSala */
   public void setSala(Sala newSala) {
      this.sala = newSala;
   }

}