package isamrs.dto;

public class SetOcenaDTO {
	private int idPacijenta;
	private int id;
	private double ocjena;
	public int getIdPacijenta() {
		return idPacijenta;
	}
	public void setIdPacijenta(int idPacijenta) {
		this.idPacijenta = idPacijenta;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getOcjena() {
		return ocjena;
	}
	public void setOcjena(double ocjena) {
		this.ocjena = ocjena;
	}
	public SetOcenaDTO(int idPacijenta, int id, double ocjena) {
		super();
		this.idPacijenta = idPacijenta;
		this.id = id;
		this.ocjena = ocjena;
	}
	
	public SetOcenaDTO() {}

}
