package Beans;

import Hjelpeklasser.Opprydder;
import Trening.Oversikt;
import Trening.TreningsOkt;
import Trening.TreningsOktStatus;
import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.sql.DataSource;

@Named("reg")
@SessionScoped
public class RegtreningBean implements Serializable {

    @Resource(name = "jdbc/waplj_prosjekt")
    DataSource ds;
    private TreningsOkt tempOkt = new TreningsOkt();
    private Oversikt oversikt = new Oversikt();
    private List<TreningsOktStatus> tabelldata = Collections.synchronizedList(new ArrayList<TreningsOktStatus>());
    private Connection forbindelse;
    private PreparedStatement setning = null;
    private ResultSet res = null;
    private String gjentattPassord = "";
    private String gammeltPassord = "";
    private String nyttPassord = "";

    public RegtreningBean() {
    }

    /**
     * Henter ut gammelt passord for senere validering
     */
    public String getGammeltPassord() {
        return gammeltPassord;
    }

    /**
     * Tar imot gammelt passord for senere validering
     */
    public void setGammeltPassord(String gammeltPassord) {
        this.gammeltPassord = gammeltPassord;
    }

    /**
     * Henter ut nytt satt passord for senere validering.
     */
    public String getNyttPassord() {
        return nyttPassord;
    }

    /**
     * Setter nytt passord.
     */
    public void setNyttPassord(String nyttPassord) {
        this.nyttPassord = nyttPassord;
    }

    /**
     * Henter ut en liste av treningsøktstatus som brukes i forbindelse med
     * lagring av økter.
     */
    public List<TreningsOktStatus> getTabelldata() {
        return tabelldata;
    }

    /**
     * Sjekker om det er data i arraylisten over økter.
     */
    public boolean getDataFins() {
        return (tabelldata.size() > 0);
    }

    /**
     * Henter brukernavn fra oversikt.
     */
    public String getBruker() {
        return oversikt.getBruker();
    }

    /**
     * Henter ut gjennomsnittlig varighet fra oversikten.
     */
    public double getSum() {
        return oversikt.getSum();
    }

    /**
     * Henter ut antall økter registrert i oversikten.
     */
    public int getAntallOkter() {
        return oversikt.getAntallOkter();
    }

    /**
     * Henter ut den midlertidige økten som blir brukt til registrering mot
     * arraylisten.
     */
    public TreningsOkt getTempOkt() {
        return tempOkt;
    }

    /**
     * Oppretter en midlertidig økt som alle opperasjoner skjer mot.
     */
    public void setTempOkt(TreningsOkt nyTempOkt) {
        tempOkt = nyTempOkt;
    }

    /**
     * Oppdateringsmetode som registrerer endringer gjort på web, både i
     * databasen og arraylisten.
     */
    public void oppdater() {

        if (!tempOkt.getTekst().trim().equals("")) {
            TreningsOkt nyOkt = new TreningsOkt(tempOkt.getDato(), tempOkt.getKategori(), tempOkt.getTekst(), tempOkt.getVarighet());
            oversikt.regNyOkt(nyOkt);
            tabelldata.add(new TreningsOktStatus(nyOkt));
            tempOkt.nullstill();
        }

        int indeks = tabelldata.size() - 1;

        while (indeks >= 0) {
            TreningsOktStatus tos = tabelldata.get(indeks);
            if (tos.getSkalSlettes()) {
                oversikt.slettOkt(tos.getTreningsOkt());
                tabelldata.remove(indeks);
            } else {
                oversikt.oppdaterOkt(tos.getTreningsOkt());
            }
            indeks--;
        }

    }

    /**
     * Henter ut dato fra en bestemt økt.
     */
    public Date getDato() {
        return tempOkt.getDato();
    }

    /**
     * Henter ut varigheten til en betemt økt.
     */
    public int getVarighet() {
        return tempOkt.getVarighet();
    }

    /**
     * Henter ut teksten som tilhører en bestemt økt.
     */
    public String getTekst() {
        return tempOkt.getTekst();
    }

    /**
     * Setter dato for en bestemt økt.
     */
    public void setDato(Date nyDato) {
        tempOkt.setDato(nyDato);
    }

    /**
     * Setter varighet for en bestemt økt.
     */
    public void setVarighet(int enVarighet) {
        tempOkt.setVarighet(enVarighet);
    }

    /**
     * Setter kategorien som blir valgt til en bestemt økt for registrering.
     */
    public void setKategori(String enkategori) {
        tempOkt.setKategori(enkategori);
    }

    /**
     * Setter teksten som tilhører en bestemt økt.
     */
    public void setTekst(String enTekst) {
        tempOkt.setTekst(enTekst);
    }

    /**
     * Henter ut kategori - arraylisten som brukes i registrering av økter.
     */
    public ArrayList<String> getKategorier() {
        return oversikt.Kategorier();
    }

    /**
     * Holder arraylisten synkronisert.
     */
    @PostConstruct
    public void setDatatable() {
        List<TreningsOktStatus> temp = Collections.synchronizedList(new ArrayList<TreningsOktStatus>());
        for (TreningsOkt t : oversikt.getAlleOkter()) {
            temp.add(new TreningsOktStatus(t));

        }
        tabelldata = temp;
    }

    /**
     * Åpner forbindelse mot datastore gitt i objektvariablene. OBS: Dette er
     * samme metode som i Oversikt-klassen, men må være her pga. Nullpointer -
     * problematikk. Egentlig unødvendig.
     */
    public void apneForbindelse() {
        try {
            if (ds == null) {
                throw new SQLException("ingen datasource funnet");
            }
            forbindelse = ds.getConnection();
            System.out.println("Vellykket forbindelse til datastore ");


        } catch (Exception e) {
            System.out.println("Feil med databaseforbindelse " + e);
        }
    }

    /**
     * Henter gjenntatt passord for validering.
     */
    public String getGjentattPassord() {
        return gjentattPassord;
    }

    /**
     * Setter gjenntatt passord for validering.
     */
    public void setGjentattPassord(String nyttGjentattPassord) {
        gjentattPassord = nyttGjentattPassord;
    }

    /**
     * Sjekker at gammelt passord finnes i databasen.
     */
    public boolean sjekkPassordMotDb() {

        boolean t = false;
        try {
            apneForbindelse();
            forbindelse.setAutoCommit(false);
            setning = forbindelse.prepareStatement("select passord from bruker where brukernavn=?");
            setning.setString(1, getBruker());
            res = setning.executeQuery();
            String fraDb = "";
            while (res.next()) {
                fraDb = res.getString(1);
            }
            if (gammeltPassord.equalsIgnoreCase(fraDb)) {
                t = true;
            }

        } catch (SQLException e) {
            System.out.println("Passordene stemmte ikke overens " + e.getMessage());
        } finally {
            Opprydder.lukkResSet(res);
            Opprydder.lukkSetning(setning);
            Opprydder.lukkForbindelse(forbindelse);
        }
        return t;

    }

    /**
     * Sjekker at nytt passord tilfredstiller krav til lengde og tegn.
     */
    public boolean sjekkNyttPassord() {

        String pattern = "^(?=.*[0-9])(?=.*[`~!@#$%^&*()_+,./{}|:\"<>?])[a-zA-Z0-9].{6,10}$";
        if (nyttPassord.matches(pattern)) {
            System.out.println("TRUE");
            return true;
        }
        System.out.println("FALSE");
        return false;
    }

    /**
     * Bytter passord for innlogget bruker i databasen om alle kritierier er
     * møtt. Kriteriene er gitt som egne metoder.
     */
    public String byttPassord() {

        String svar = null;
        if (sjekkPassordMotDb() && sjekkNyttPassord() && nyttPassord.equals(gjentattPassord) && !(nyttPassord.equals(gammeltPassord))) {

            try {
                apneForbindelse();
                forbindelse.setAutoCommit(false);
                setning = forbindelse.prepareStatement("UPDATE bruker SET passord=? WHERE brukernavn=?");
                setning.setString(1, nyttPassord);
                setning.setString(2, getBruker());
                setning.executeUpdate();
                svar = "passordOk";
            } catch (SQLException e) {
                System.out.println("Feil i byttPassord" + e.getMessage());
            } finally {
                Opprydder.settAutoCommit(forbindelse);
                Opprydder.lukkSetning(setning);
                Opprydder.lukkForbindelse(forbindelse);
            }

        } else {
            svar = "passordFeil";
        }
        return svar;
    }
}
