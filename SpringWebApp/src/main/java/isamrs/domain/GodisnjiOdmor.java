/***********************************************************************
 * Module:  GodisnjiOdmor.java
 * Author:  Dusan
 * Purpose: Defines the Class GodisnjiOdmor
 ***********************************************************************/

package isamrs.domain;

import isamrs.dto.GodisnjiOdmorContainerDTO;
import isamrs.dto.GodisnjiOdmorDTO;

import java.util.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "godisnji_odmori")
public class GodisnjiOdmor {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;
	@Column(name = "pocetak", unique = false, nullable = false)
	private Date pocetak;
	@Column(name = "kraj", unique = false, nullable = false)
	private Date kraj;
	
	// Ukoliko nije odobren treba admin da pogleda,
	// ukoliko jeste lekar je na godisnjem,
	// ukoliko admin odbije objekat se brise
	@Column(name = "odobren", unique = false, nullable = false)
	private boolean odobren;

	public GodisnjiOdmor() {
		super();
	}



	public GodisnjiOdmor(GodisnjiOdmorDTO go) {
		this.id = go.getId();
		this.kraj = go.getKraj();
		this.pocetak = go.getPocetak();
		this.odobren = go.isOdobren();
	}


	public Integer getId() {
		return id;
	}



	public void setId(Integer id) {
		this.id = id;
	}



	public boolean isOdobren() {
		return odobren;
	}



	public void setOdobren(boolean odobren) {
		this.odobren = odobren;
	}



	public Date getPocetak() {
		return pocetak;
	}

	public void setPocetak(Date pocetak) {
		this.pocetak = pocetak;
	}

	public Date getKraj() {
		return kraj;
	}

	public void setKraj(Date kraj) {
		this.kraj = kraj;
	}

}