
package Trening;

// FIRST MAKE IT WORK, THAN MAKE IT PRETTY.


import java.io.Serializable; 
import javax.inject.Inject; 
import javax.enterprise.context.SessionScoped; 
import java.util.ArrayList; 
import javax.inject.Named;
import java.util.Date; 
import java.text.*;
        
        
@Named ("reg") 
@SessionScoped

public class regtreningBean implements Serializable {
      TreningsOkt eiOkt = new TreningsOkt(); 
      
      public regtreningBean(){
        
      }
      
      public synchronized  int getOktnr(){
        return eiOkt.getOktnr(); 
      }
      
      public synchronized void setOktnr(int nyOktnr){
        eiOkt.setOktnr(nyOktnr);
      }
      
      public synchronized Date getDato(){
      return eiOkt.getDato(); 
    }
    
    public synchronized int getVarighet(){
      return eiOkt.getVarighet(); 
    }
    
    public synchronized String getKategori(){
      return eiOkt.getKategori(); 
    }
    
    
    
    public synchronized String getTekst(){
      return eiOkt.getTekst(); 
    }
    
    
    public synchronized void setDato(Date nyDato){
      eiOkt.setDato(nyDato);
    }
    
    public synchronized void setVarighet(int enVarighet){
      eiOkt.setVarighet(enVarighet);
    }
   
    public synchronized void setKategori(String enkategori){
      eiOkt.setKategori(enkategori);
      
      
    }
    
    public synchronized void setTekst(String enTekst){
      eiOkt.setTekst(enTekst);
    }
}


