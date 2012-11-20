package Trening;

import java.io.Serializable;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.annotation.Resource;
import javax.faces.context.FacesContext;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class Oversikt implements Serializable {

    @Resource(name = "jdbc/waplj_prosjekt")
    private DataSource ds;
    private Connection forbindelse = null;
    private PreparedStatement setning = null;
    private ResultSet res = null;
    private String bruker = getBrukerNavn();
    private ArrayList<TreningsOkt> alleOkt = new ArrayList<TreningsOkt>();

    /*
     * Konstruktøren kobler seg opp mot datasourcen og henter ut alle øktene til 
     * den innloggede brukeren. Disse øktene legges så i arraylisten ved konstruksjon
     * av objektet. 
     */
    public Oversikt() {
        try {
            ds = (DataSource) new InitialContext().lookup("jdbc/waplj_prosjekt");
            forbindelse = ds.getConnection();
            setning = forbindelse.prepareStatement("select * from trening where brukernavn=?");
            setning.setString(1, bruker);
            res = setning.executeQuery();
            while (res.next()) {
                Date enDato = res.getDate("Dato");
                int etOktnr = res.getInt("oktnr");
                int enVarighet = res.getInt("varighet");
                String enKategori = res.getString("kategorinavn");
                String tekst = res.getString("tekst");
                TreningsOkt eiOkt = new TreningsOkt(enDato, enKategori, tekst, enVarighet);
                eiOkt.setOktNr(etOktnr);
                alleOkt.add(eiOkt);
            }

        } catch (SQLException e) {
            System.out.println(e + " Noe gikk galt i databaseforbindelsen");
        } catch (NamingException e) {
            System.out.println("Noe gikk galt med kastinga på NAVN " + e.getMessage());
        } finally {
            Opprydder.lukkResSet(res);
            Opprydder.lukkSetning(setning);
            Opprydder.lukkForbindelse(forbindelse);
        }
    }
    /*
     * Henter ut brukernavnet til den innloggede brukeren.
     */

    public String getBruker() {
        return bruker;
    }
    /*
     * Returnerer arraylisten 
     */

    public ArrayList<TreningsOkt> getAlleOkter() {
        return alleOkt;
    }
    /*
     * Henter ut total sum av varigheter samt antall økter, og regner snittet av 
     * disse. 
     */

    public double getSum() {

        double sum = 0;
        int varighet = 0;
        try {
            apneForbindelse();
            String settning = "select SUM(varighet), count(oktnr) FROM trening";
            setning = forbindelse.prepareStatement(settning);
            res = setning.executeQuery();
            while (res.next()) {
                varighet = res.getInt(1);
                sum = res.getDouble(2);
            }

        } catch (SQLException e) {
            System.out.println("Noe gikk galt med gjennomsnittet " + e.getMessage());
        } finally {
            Opprydder.lukkSetning(setning);
            Opprydder.settAutoCommit(forbindelse);
            Opprydder.lukkForbindelse(forbindelse);
        }
        return (double) varighet / sum;

    }
    /*
     * Henter ut antall økter basert på arraylisten. Denne er nødvendig for korrekt
     * liste over antall økter. 
     */

    public int getAntallOkter() {
        return alleOkt.size();
    }
    /*
     * Registrerer en ny treningsøkt i databasen og arraylisten basert på inputdata fra frontend. 
     */

    public void regNyOkt(TreningsOkt ny) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date dato = ny.getDato();
        String datodb = formatter.format(dato); //Formatering av dato pga Date - objekt
        int varighet = ny.getVarighet();
        String tekst = ny.getTekst();
        String kategori = ny.getKategori();
        System.out.println(kategori);
        try {
            apneForbindelse();
            forbindelse.setAutoCommit(false);
            setning = forbindelse.prepareStatement("insert into trening (dato, varighet, kategorinavn, tekst, brukernavn)" + "VALUES (DATE('" + datodb + "'),?,?,?,?)");
            setning.setInt(1, varighet);
            setning.setString(2, kategori);
            setning.setString(3, tekst);
            setning.setString(4, bruker);
            setning.executeUpdate();
            Opprydder.lukkSetning(setning);

            setning = forbindelse.prepareStatement("select max(oktnr)from trening");
            res = setning.executeQuery();
            int tall = 0;
            while (res.next()) {
                tall = res.getInt(1);
            }
            ny.setOktNr(tall);
            if (ny != null) {
                alleOkt.add(ny);
            }

        } catch (SQLException e) {
            System.out.println("noe gikk galt med REGISTRERINGEN. " + e.getMessage());
            Opprydder.skrivMelding(e, "RegOkt");
        } finally {
            Opprydder.lukkSetning(setning);
            Opprydder.settAutoCommit(forbindelse);
            Opprydder.lukkForbindelse(forbindelse);

        }
    }
    /*
     * Oppdaterer en allerede ekisterende treningsøkt i databasen, valgt fra listen
     * i frontenden. 
     */

    public void oppdaterOkt(TreningsOkt valgt) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String datodb = formatter.format(valgt.getDato());

        try {
            apneForbindelse();
            setning = forbindelse.prepareStatement("UPDATE trening SET dato=?, varighet=?, kategorinavn=?, tekst=? WHERE oktnr=?");
            setning.setString(1, datodb);
            setning.setInt(2, valgt.getVarighet());
            setning.setString(3, valgt.getKategori());
            setning.setString(4, valgt.getTekst());
            setning.setInt(5, valgt.getOktNr());
            setning.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Noe gikk galt med oppdateringen" + e.getMessage());

        } finally {
            Opprydder.lukkSetning(setning);
            Opprydder.lukkForbindelse(forbindelse);
        }
    }
    /*
     * Sletter en økt fra databasen og arraylisten basert på valgt øktnummer. 
     */

    public void slettOkt(TreningsOkt valgt) {

        try {
            apneForbindelse();
            forbindelse.setAutoCommit(false);
            String slett = "delete from trening WHERE oktnr = ?";
            setning = forbindelse.prepareStatement(slett);
            setning.setInt(1, valgt.getOktNr());
            alleOkt.remove(valgt);
            setning.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Noe gikk galt med slettingen " + e.getMessage());
        } finally {
            Opprydder.lukkSetning(setning);
            Opprydder.settAutoCommit(forbindelse);
            Opprydder.lukkForbindelse(forbindelse);
        }
    }
    /*
     * Henter ut kategoriene som finnes i databasen og lager arraylist av disse.
     * Denne brukes senere til registrering av økter. 
     */

    public ArrayList<String> Kategorier() {

        ArrayList<String> kategorier = new ArrayList<String>();
        try {
            apneForbindelse();
            setning = forbindelse.prepareStatement("select kategorinavn from kategori");
            res = setning.executeQuery();
            while (res.next()) {
                kategorier.add(res.getString(1));
            }
        } catch (SQLException e) {
            System.out.println("Noe gikk galt i kategorihentingen" + e.getMessage());
        } finally {
            Opprydder.lukkSetning(setning);
            Opprydder.lukkForbindelse(forbindelse);
        }
        return kategorier;
    }
    /*
     * Henter ut brukernavnet til den personen som er innlogget fra sesjonen.
     */

    public String getBrukerNavn() {
        bruker = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
        return bruker;
    }
    /*
     * Åpner forbindelse mot datastore gitt i objektvariablene. 
     */

    public void apneForbindelse() {
        try {
            if (ds == null) {
                throw new SQLException("ingen datasource funnet");
            }
            forbindelse = ds.getConnection();
            System.out.println("Vellykket forbindelse til datastore ");

        } catch (Exception e) {
            System.out.println("Feil med databaseforbindelse " + e.getMessage());
        }
    }
}
