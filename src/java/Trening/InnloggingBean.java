/**
 * Jobber med det - MARIA
 * 
 * /innloggingbean - bruker
 * 
 */
package Trening;


import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.sql.DataSource;

@Named("login")
@RequestScoped
public class InnloggingBean { 
    
    @Resource(name = "jdbc/waplj_prosjekt")
    DataSource ds;
    
    Bruker bruker = new Bruker();
    
    public InnloggingBean(){
        
    }
    
    /**
     * Henter ut gammelt passord for senere validering
     */
    public String getGammeltPassord() {
        return bruker.getGammeltPassord();
    }

    /**
     * Tar imot gammelt passord for senere validering
     */
    public void setGammeltPassord(String gammeltPassord) {
        bruker.setGammeltPassord(gammeltPassord);
    }

    /**
     * Henter ut nytt satt passord for senere validering.
     */
    public String getNyttPassord() {
        return bruker.getNyttPassord();
    }

    /**
     * Setter nytt passord.
     */
    public void setNyttPassord(String nyttPassord) {
        bruker.setNyttPassord(nyttPassord);
    }
    
       /**
     * Henter gjenntatt passord for validering.
     */
    public String getGjentattPassord() {
        return bruker.getGjentattPassord();
    }

    /**
     * Setter gjenntatt passord for validering.
     */
    public void setGjentattPassord(String nyttGjentattPassord) {
        bruker.setGjentattPassord(nyttGjentattPassord);
    }
    
    
    public String byttPassord(){
        return bruker.byttPassord();
    }

 
        
    
    
}
