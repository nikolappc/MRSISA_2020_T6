package isamrs.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "adrese")
public class Adresa {


    @Id
    @Column(unique = true, nullable = false, name = "id")
    private Long id;

    @Column(unique = false, nullable = false, name = "drzava")
    private String drzava;

    @Column(unique = false, nullable = false, name = "grad")
    private String grad;

    @Column(unique = false, nullable = false, name = "adresa")
    private String adresa;

    public Adresa() {
    }

    public Adresa(Long id, String drzava, String grad, String adresa) {
        this.id = id;
        this.drzava = drzava;
        this.grad = grad;
        this.adresa = adresa;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDrzava() {
        return drzava;
    }

    public void setDrzava(String drzava) {
        this.drzava = drzava;
    }

    public String getGrad() {
        return grad;
    }

    public void setGrad(String grad) {
        this.grad = grad;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }
}
