package Trening;

public class TreningsOktStatus {

    private TreningsOkt treningsokt;
    private boolean skalSlettes;

    public TreningsOktStatus() {
        treningsokt = new TreningsOkt();
        skalSlettes = false;
    }

    /**
     * Henter inn ei treningsøkt og setter sletting - statusen til false. Denne
     * endrer vi senere om objektet skal slettes fra databasen og tabellen.
     */
    public TreningsOktStatus(TreningsOkt treningsokt) {
        this.treningsokt = treningsokt;
        skalSlettes = false;
    }

    /**
     * Sjekker om ei økt skal slettes eller ikke.
     */
    public boolean getSkalSlettes() {
        return skalSlettes;
    }

    /**
     * Bestemmer om ei økt skal slettes eller ikke.
     */
    public void setSkalSlettes(boolean nySkalSlettes) {
        skalSlettes = nySkalSlettes;
    }

    /**
     * Henter ut treningsøkt-objektet som er lagret i statusen.
     */
    public TreningsOkt getTreningsOkt() {
        return treningsokt;
    }

    /**
     * Tar inn ei ny treningsøkt og setter objekt lik denne.
     */
    public void setTreningsOkt(TreningsOkt nyTreningsOkt) {
        treningsokt = nyTreningsOkt;
    }
}
