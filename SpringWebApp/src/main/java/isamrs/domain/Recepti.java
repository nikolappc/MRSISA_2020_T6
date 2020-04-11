/***********************************************************************
 * Module:  Recepti.java
 * Author:  Dusan
 * Purpose: Defines the Class Recepti
 ***********************************************************************/

package isamrs.domain;

import java.util.*;

public class Recepti {
   private Integer id;
   private boolean overen;
   
   public Lek lek;
   
   
   /** @pdGenerated default parent getter */
   public Lek getLek() {
      return lek;
   }
   
   /** @pdGenerated default parent setter
     * @param newLek */
   public void setLek(Lek newLek) {
      this.lek = newLek;
   }

}