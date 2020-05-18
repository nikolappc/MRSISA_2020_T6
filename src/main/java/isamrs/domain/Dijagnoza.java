/***********************************************************************
 * Module:  Dijagnoza.java
 * Author:  Dusan
 * Purpose: Defines the Class Dijagnoza
 ***********************************************************************/

package isamrs.domain;

import java.util.*;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "dijagnoze")
public class Dijagnoza {

	@Id
	@Column(name = "sifra", unique = true, nullable = false)
	private Long sifraDijagnoze;

	@Column(name = "naziv", unique = false, nullable = false)
	private String nazivDijagnoze;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_pregleda")
	@JsonBackReference
	private Collection<Pregled> pregled;

	public Dijagnoza() {
		super();
	}

	public Long getSifraDijagnoze() {
		return sifraDijagnoze;
	}

	public void setSifraDijagnoze(Long sifraDijagnoze) {
		this.sifraDijagnoze = sifraDijagnoze;
	}

	public String getNazivDijagnoze() {
		return nazivDijagnoze;
	}

	public void setNazivDijagnoze(String nazivDijagnoze) {
		this.nazivDijagnoze = nazivDijagnoze;
	}

}