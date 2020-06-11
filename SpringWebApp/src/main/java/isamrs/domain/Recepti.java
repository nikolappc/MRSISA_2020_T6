package isamrs.domain;

import java.util.*;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="recepti")
public class Recepti {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Version
	private Integer version;

	@Column(name = "overen", nullable = false)
	private boolean overen;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_recepta")
	public Lek lek;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_pregleda")
	@JsonBackReference(value = "pregledReference")
	private Pregled pregled;

	public Lek getLek() {
		return lek;
	}

	public void setLek(Lek newLek) {
		this.lek = newLek;
	}
	
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public boolean isOveren() {
		return overen;
	}

	public void setOveren(boolean overen) {
		this.overen = overen;
	}

	public Pregled getPregled() {
		return pregled;
	}

	public void setPregled(Pregled pregled) {
		this.pregled = pregled;
	}

	public Recepti(Integer id, boolean overen, Lek lek) {
		super();
		this.id = id;
		this.overen = overen;
		this.lek = lek;
	}
	
	public Recepti() {}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
}