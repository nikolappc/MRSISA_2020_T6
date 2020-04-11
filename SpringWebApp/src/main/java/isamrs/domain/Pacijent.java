/***********************************************************************
 * Module:  Pacijent.java
 * Author:  Dusan
 * Purpose: Defines the Class Pacijent
 ***********************************************************************/

package isamrs.domain;

import java.util.*;

public class Pacijent extends Osoba {
   public ZdravstveniKarton zdravstveniKarton;
   
   
   public Pacijent(long l, String string, String string2, String string3, String string4, String string5, String string6,
		String string7, String string8) {
	// TODO Auto-generated constructor stub
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
            this.zdravstveniKarton.setPacijent(null);
         this.zdravstveniKarton = newZdravstveniKarton;
         if (this.zdravstveniKarton != null)
            this.zdravstveniKarton.setPacijent(this);
      }
   }

}