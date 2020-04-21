package isamrs.domain;

import java.util.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="stavke_cenovnika")
public class StavkaCenovnika {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "cena", unique = true, nullable = false)
	private Double cena;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_cenovnik")
	private Cenovnik cenovnik;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getCena() {
		return cena;
	}

	public void setCena(Double cena) {
		this.cena = cena;
	}

	public StavkaCenovnika(Integer id, Double cena) {
		super();
		this.id = id;
		this.cena = cena;
	}
	
	public StavkaCenovnika() {}

}