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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="stavke_cenovnika")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class StavkaCenovnika {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "cena", unique = true, nullable = false)
	private Double cena;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_cenovnik")
	@JsonIgnore
	private Cenovnik cenovnik;
	
	
//	@OneToOne(mappedBy = "stavkaCenovnika",fetch = FetchType.LAZY)
//	private TipPosete tip;
//	
//	
//	
//	
//	
//	public TipPosete getTip() {
//		return tip;
//	}
//
//	public void setTip(TipPosete tip) {
//		if (tip == null) {
//            if (this.tip != null) {
//                this.tip.setStavkaCenovnika(null);
//            }
//        }
//        else {
//            tip.setStavkaCenovnika(this);
//        }
//        this.tip = tip;
//	}

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