package isamrs.dto;


public class LekDTO {
    private Long sifraLeka;

    private String nazivLeka;

    private String opisLeka;

    private boolean koristen;


    public LekDTO(Long sifraLeka, String nazivLeka, String opisLeka, boolean koristen) {
        this.sifraLeka = sifraLeka;
        this.nazivLeka = nazivLeka;
        this.opisLeka = opisLeka;
        this.koristen = koristen;
    }

    public Long getSifraLeka() {
        return sifraLeka;
    }

    public void setSifraLeka(Long sifraLeka) {
        this.sifraLeka = sifraLeka;
    }

    public String getNazivLeka() {
        return nazivLeka;
    }

    public void setNazivLeka(String nazivLeka) {
        this.nazivLeka = nazivLeka;
    }

    public String getOpisLeka() {
        return opisLeka;
    }

    public void setOpisLeka(String opisLeka) {
        this.opisLeka = opisLeka;
    }

    public boolean isKoristen() {
        return koristen;
    }

    public void setKoristen(boolean koristen) {
        this.koristen = koristen;
    }
}
