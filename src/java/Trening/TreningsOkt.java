package Trening;

import java.util.Date;

public class TreningsOkt {

    private int oktNr;
    private Date dato;
    private int varighet;
    private String kategori;
    private String tekst;

    public TreningsOkt(Date dato, String kategori, String tekst, int varighet) {
        this.dato = dato;
        this.kategori = kategori;
        this.varighet = varighet;
        this.tekst = tekst;
    }

    /**
     * Tom konstruktør som nullstiller et objekt.
     */
    public TreningsOkt() {
        nullstill();
    }

    /**
     * Henter ut øktnummeret til ei økt.
     */
    public int getOktNr() {
        return oktNr;
    }
    /**
     * Setter øktnummer for økta.
     */

    public void setOktNr(int nyttNr) {
        oktNr = nyttNr;
    }

    /**
     * Henter ut dato for ei økt. OBS: date - objekt, omformes der det trengs.
     */
    public Date getDato() {
        return dato;
    }
    /**
     * Henter ut varigheten til ei økt. 
     */

    public int getVarighet() {
        return varighet;
    }
    /**
     * Henter ut kategorien for ei økt.
     */

    public String getKategori() {
        return kategori;
    }

    /**
     * henter ut lagret tekst på ei økt.
     */
    public String getTekst() {
        return tekst;
    }

    /**
     * setter dato på økten. OBS: bruker Date - objekt, omforming skjer 
     * i metodene hvor det er nødvendig. 
     */
    public void setDato(Date enDato) {
        dato = enDato;
    }

    /**
     * Setter varigheten på økten
     */
    public void setVarighet(int enVarighet) {
        varighet = enVarighet;
    }

    /**
     * Lagrer kategorien treningen er basert på
     */
    public void setKategori(String enKategori) {
        kategori = enKategori;

    }
    /**
     * Setter teksten som lagres for ei økt
     */

    public void setTekst(String enTekst) {

        tekst = enTekst;

    }
    /**
     * Nullstiller hele objektet 
     */

    public final void nullstill() {

        dato = new Date();
        tekst = "";
        varighet = 0;
    }
}
