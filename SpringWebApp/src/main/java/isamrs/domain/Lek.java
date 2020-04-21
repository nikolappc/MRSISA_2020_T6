/***********************************************************************
 * Module:  Lek.java
 * Author:  Dusan
 * Purpose: Defines the Class Lek
 ***********************************************************************/

package isamrs.domain;

import java.util.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "lekovi")
public class Lek {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "sifra_leka", unique = true, nullable = false)
	private Integer sifraLeka;

	public Lek() {
		super();
	}

	public Integer getSifraLeka() {
		return sifraLeka;
	}

	public void setSifraLeka(Integer sifraLeka) {
		this.sifraLeka = sifraLeka;
	}

}