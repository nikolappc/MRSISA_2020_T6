package isamrs.exceptions;

public abstract class Error extends Throwable {
    private String naslov;
    private String opis;
    private Integer kod;

    public Error(String naslov, String opis, Integer kod) {
        this.naslov = naslov;
        this.opis = opis;
        this.kod = kod;
    }

    public Error() {
    }

    public String getNaslov() {
        return naslov;
    }

    public void setNaslov(String naslov) {
        this.naslov = naslov;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public Integer getKod() {
        return kod;
    }

    public void setKod(Integer kod) {
        this.kod = kod;
    }

    @Override
    public String toString() {
        return "Error{" +
                "naslov='" + naslov + '\'' +
                ", opis='" + opis + '\'' +
                ", kod=" + kod +
                '}';
    }
}
