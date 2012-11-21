
package Trening;

import Hjelpeklasser.Opprydder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.annotation.Resource;
import javax.sql.DataSource;

public class Bruker {
    
    @Resource(name = "jdbc/waplj_prosjekt")
    DataSource ds;
    private Connection forbindelse;
    private PreparedStatement setning = null;
    private ResultSet res = null;
    
    private String gjentattPassord = "";
    private String gammeltPassord = "";
    private String nyttPassord = "";
    private String bruker;
    
    
    public Bruker(){
        
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

    public String getBruker() {
        return bruker;
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
     * Sjekker at gammelt passord finnes i databasen.
     */
    public boolean sjekkPassordMotDb() {
        apneForbindelse();
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
            return true;
        }
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
