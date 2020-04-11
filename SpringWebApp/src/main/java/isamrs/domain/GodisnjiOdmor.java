/***********************************************************************
 * Module:  GodisnjiOdmor.java
 * Author:  Dusan
 * Purpose: Defines the Class GodisnjiOdmor
 ***********************************************************************/

package isamrs.domain;

import java.util.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="godisnji_odmori")
public class GodisnjiOdmor {
	@Id
	@Column(name="id", unique=true, nullable=false)
	private Integer id;
	@Column(name = "pocetak", unique = false,nullable = false)
	private Date pocetak;
	@Column(name = "kraj", unique = false,nullable = false)
	private Date kraj;
   
public GodisnjiOdmor() {
	super();
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