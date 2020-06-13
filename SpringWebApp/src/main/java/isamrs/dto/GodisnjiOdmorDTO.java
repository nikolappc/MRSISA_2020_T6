
package isamrs.dto;

import isamrs.domain.GodisnjiOdmor;

import javax.persistence.Column;
import java.util.Date;

public class GodisnjiOdmorDTO {

    private Integer id;
    private Date pocetak;
    private Date kraj;

    private boolean odobren;

    public GodisnjiOdmorDTO() {
    }

    public GodisnjiOdmorDTO(GodisnjiOdmor go) {
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

    public boolean isOdobren() {
        return odobren;
    }

    public void setOdobren(boolean odobren) {
        this.odobren = odobren;
    }
}