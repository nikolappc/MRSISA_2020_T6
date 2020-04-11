package isamrs.domain;

import java.util.*;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "pacijent")
public class Pacijent extends Osoba {
<<<<<<< HEAD

	@OneToOne
	@JoinColumn(name = "id")
	public ZdravstveniKarton zdravstveniKarton;

	public Pacijent() {
		super();
	}

	public Pacijent(String ime, String prezime, String password, String brojTelefona, String jbo, String adresa,
			String email, Integer id, ZdravstveniKarton zdravstveniKarton) {
		super(ime, prezime, password, brojTelefona, jbo, adresa, email, id);
		this.zdravstveniKarton = zdravstveniKarton;
	}
	
	public Pacijent(String ime, String prezime, String password, String brojTelefona, String jbo, String adresa,
			String email, Integer id) {
		super(ime, prezime, password, brojTelefona, jbo, adresa, email, id);
	}

	public ZdravstveniKarton getZdravstveniKarton() {
		return zdravstveniKarton;
	}

	public void setZdravstveniKarton(ZdravstveniKarton newZdravstveniKarton) {
		if (this.zdravstveniKarton == null || !this.zdravstveniKarton.equals(newZdravstveniKarton)) {
			if (this.zdravstveniKarton != null)
				this.zdravstveniKarton.setPacijent(null);
			this.zdravstveniKarton = newZdravstveniKarton;
			if (this.zdravstveniKarton != null)
				this.zdravstveniKarton.setPacijent(this);
		}
	}
=======
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
>>>>>>> 357e5956b688ff3f5f1fdbb2086433aeffaa2a7c

}