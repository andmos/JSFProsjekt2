package Trening;

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
public class regtreningBean implements Serializable {

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

    public regtreningBean() {
    }

    public String getGammeltPassord() {
        return gammeltPassord;
    }

    public void setGammeltPassord(String gammeltPassord) {
        this.gammeltPassord = gammeltPassord;
    }

    public String getNyttPassord() {
        return nyttPassord;
    }

    public void setNyttPassord(String nyttPassord) {
        this.nyttPassord = nyttPassord;
    }

    public List<TreningsOktStatus> getTabelldata() {
        return tabelldata;
    }

    public boolean getDataFins() {
        return (tabelldata.size() > 0);
    }

    public String getBruker() {
        return oversikt.getBruker();
    }

    public double getSum() {
        return oversikt.getSum();
    }

    public int getAntallOkter() {
        return oversikt.getAntallOkter();
    }

    public TreningsOkt getTempOkt() {
        return tempOkt;
    }

    public void setTempOkt(TreningsOkt nyTempOkt) {
        tempOkt = nyTempOkt;
    }

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
                System.out.println("indeks =" + indeks);
                oversikt.slettOkt(tos.getTreningsOkt());
                tabelldata.remove(indeks);
            } else {
                oversikt.oppdaterOkt(tos.getTreningsOkt());
            }
            indeks--;
        }

    }

    public Date getDato() {
        return tempOkt.getDato();
    }

    public int getVarighet() {
        return tempOkt.getVarighet();
    }

    public String getTekst() {
        return tempOkt.getTekst();
    }

    public void setDato(Date nyDato) {
        tempOkt.setDato(nyDato);
    }

    public void setVarighet(int enVarighet) {
        tempOkt.setVarighet(enVarighet);
    }

    public void setKategori(String enkategori) {
        tempOkt.setKategori(enkategori);
    }

    public void setTekst(String enTekst) {
        tempOkt.setTekst(enTekst);
    }

    public ArrayList<String> getKategorier() {
        return oversikt.Kategorier();
    }

    public String englishAction() {
        System.out.println("her er jeg");
        FacesContext context = FacesContext.getCurrentInstance();
        context.getViewRoot().setLocale(new Locale("en"));
        return null;
    }

    public String norwegianAction() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getViewRoot().setLocale(new Locale("no"));
        return null;
    }

    @PostConstruct
    public void setDatatable() {
        List<TreningsOktStatus> temp = Collections.synchronizedList(new ArrayList<TreningsOktStatus>());
        for (TreningsOkt t : oversikt.getAlleOkter()) {
            temp.add(new TreningsOktStatus(t));

        }
        tabelldata = temp;
    }

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

    public String getGjentattPassord() {
        return gjentattPassord;
    }

    public void setGjentattPassord(String nyttGjentattPassord) {
        gjentattPassord = nyttGjentattPassord;
    }

    public boolean sjekkPassordMotDb() {

        boolean t = false;
        try {
            apneForbindelse();
            forbindelse.setAutoCommit(false);
            setning = forbindelse.prepareStatement("select passord from bruker where brukernavn=?");
            setning.setString(1, getBruker());
            res = setning.executeQuery();
            String fraDb = "";
            System.out.println("Jeg er inne i SjekkPassord");
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

    public boolean sjekkNyttPassord() {

        String pattern = "^(?=.*[0-9])(?=.*[`~!@#$%^&*()_+,./{}|:\"<>?])[a-zA-Z0-9].{6,10}$";
        if (nyttPassord.matches(pattern)) {
            System.out.println("TRUE");
            return true;
        }
        System.out.println("FALSE");
        return false;
    }

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
