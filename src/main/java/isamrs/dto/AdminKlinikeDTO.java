package isamrs.dto;

import isamrs.domain.Adresa;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

public class AdminKlinikeDTO {
    private String ime;

    private String prezime;

    private String password;

    private String brojTelefona;

    private String jbo;

    private String email;

    private Adresa adresa;

    private Integer klinika;

    public AdminKlinikeDTO(String ime, String prezime, String password, String brojTelefona, String jbo, String email, Adresa adresa) {
        this.ime = ime;
        this.prezime = prezime;
        this.password = password;
        this.brojTelefona = brojTelefona;
        this.jbo = jbo;
        this.email = email;
        this.adresa = adresa;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBrojTelefona() {
        return brojTelefona;
    }

    public void setBrojTelefona(String brojTelefona) {
        this.brojTelefona = brojTelefona;
    }

    public String getJbo() {
        return jbo;
    }

    public void setJbo(String jbo) {
        this.jbo = jbo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Adresa getAdresa() {
        return adresa;
    }

    public void setAdresa(Adresa adresa) {
        this.adresa = adresa;
    }

    public Integer getKlinika() {
        return klinika;
    }

    public void setKlinika(Integer klinika) {
        this.klinika = klinika;
    }
}
