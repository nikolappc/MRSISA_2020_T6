/***********************************************************************
 * Module:  Lek.java
 * Author:  Dusan
 * Purpose: Defines the Class Lek
 ***********************************************************************/

package isamrs.domain;

import isamrs.dto.LekDTO;

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
	@Column(unique = true, nullable = false, name = "id")
	Long id;

	@Column(name = "sifra_leka", unique = true, nullable = false)
	private Long sifraLeka;

	@Column(name = "naziv_leka", unique = true, nullable = false)
	private String nazivLeka;

	@Column(name = "opis_leka", unique=false, nullable=true)
	private String opisLeka;

	public Lek() {
		super();
	}

	public Lek(LekDTO l) {
		this.id = l.getId();
		this.sifraLeka = l.getSifraLeka();
		this.nazivLeka = l.getNazivLeka();
		this.opisLeka = l.getOpisLeka();
	}

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSifraLeka() {
		return sifraLeka;
	}

	public void setSifraLeka(Long sifraLeka) {
		this.sifraLeka = sifraLeka;
	}

	public String getNazivLeka() {
		return nazivLeka;
	}

	public void setNazivLeka(String nazivLeka) {
		this.nazivLeka = nazivLeka;
	}

	public String getOpisLeka() {
		return opisLeka;
	}

	public void setOpisLeka(String opisLeka) {
		this.opisLeka = opisLeka;
	}
	
}