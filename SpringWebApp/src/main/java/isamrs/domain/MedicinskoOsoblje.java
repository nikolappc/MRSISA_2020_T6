package isamrs.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class MedicinskoOsoblje extends Osoba {

    @ManyToOne
    @JoinColumn(name = "id_klinike", referencedColumnName = "id")
    @JsonBackReference
    Klinika klinika;


    public Klinika getKlinika() {
        return klinika;
    }

    public void setKlinika(Klinika klinika) {
        this.klinika = klinika;
    }
}
