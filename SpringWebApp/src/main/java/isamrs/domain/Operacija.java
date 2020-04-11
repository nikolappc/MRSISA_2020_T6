/***********************************************************************
 * Module:  Operacija.java
 * Author:  Dusan
 * Purpose: Defines the Class Operacija
 ***********************************************************************/

package isamrs.domain;

import java.util.*;

public class Operacija extends Poseta {
   public java.util.Collection<Lekar> lekar;
   
   
   /** @pdGenerated default getter */
   public java.util.Collection<Lekar> getLekar() {
      if (lekar == null)
         lekar = new java.util.HashSet<Lekar>();
      return lekar;
   }
   
   /** @pdGenerated default iterator getter */
   public java.util.Iterator getIteratorLekar() {
      if (lekar == null)
         lekar = new java.util.HashSet<Lekar>();
      return lekar.iterator();
   }
   
   /** @pdGenerated default setter
     * @param newLekar */
   public void setLekar(java.util.Collection<Lekar> newLekar) {
      removeAllLekar();
      for (java.util.Iterator iter = newLekar.iterator(); iter.hasNext();)
         addLekar((Lekar)iter.next());
   }
   
   /** @pdGenerated default add
     * @param newLekar */
   public void addLekar(Lekar newLekar) {
      if (newLekar == null)
         return;
      if (this.lekar == null)
         this.lekar = new java.util.HashSet<Lekar>();
      if (!this.lekar.contains(newLekar))
         this.lekar.add(newLekar);
   }
   
   /** @pdGenerated default remove
     * @param oldLekar */
   public void removeLekar(Lekar oldLekar) {
      if (oldLekar == null)
         return;
      if (this.lekar != null)
         if (this.lekar.contains(oldLekar))
            this.lekar.remove(oldLekar);
   }
   
   /** @pdGenerated default removeAll */
   public void removeAllLekar() {
      if (lekar != null)
         lekar.clear();
   }

}