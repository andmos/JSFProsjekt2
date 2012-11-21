
package Beans;


import Trening.Bruker;
import java.io.Serializable;
import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.sql.DataSource;

@Named("pwd")
@SessionScoped
public class PassordBean implements Serializable { 
    
    @Resource(name = "jdbc/waplj_prosjekt")
    DataSource ds;
    
    Bruker bruker = new Bruker();
    
    public PassordBean(){
        
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
    
    /**
     * Bytter passordet i databasen. 
     * 
     */
    public String byttPassord(){
        return bruker.byttPassord();
    }

 
        
    
    
}
