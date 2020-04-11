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
import javax.persistence.ManyToOne;
import javax.persistence.Table;
@Entity
@Table(name="dijagnoze")
public class Dijagnoza {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="sifra", unique=true, nullable=false)
	private Integer sifraDijagnoze;
	
	
	@Column(name="naziv", unique=false, nullable=false)
	private String nazivDijagnoze;
	
	public Dijagnoza() {
		super();
	}

	public Integer getSifraDijagnoze() {
		return sifraDijagnoze;
	}

	public void setSifraDijagnoze(Integer sifraDijagnoze) {
		this.sifraDijagnoze = sifraDijagnoze;
	}

	public String getNazivDijagnoze() {
		return nazivDijagnoze;
	}

	public void setNazivDijagnoze(String nazivDijagnoze) {
		this.nazivDijagnoze = nazivDijagnoze;
	}
	
	
	

}