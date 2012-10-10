
package Trening;

import java.io.Serializable; 
import javax.inject.Inject; 
import javax.enterprise.context.SessionScoped; 
import java.util.ArrayList; 
import javax.inject.Named;

        
        
@Named ("reg") 

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
      public synchronized int getDato(){
      return eiOkt.getDato(); 
    }
    
    public synchronized int getVarighet(){
      return eiOkt.getVarighet(); 
    }
    
    public synchronized ArrayList<String> getKategori(){
      return eiOkt.getKategori(); 
    }
    
    public synchronized String getKategoriStreng(){
        return eiOkt.getKategoriStreng(); 
    }
    
    public synchronized String getTekst(){
      return eiOkt.getTekst(); 
    }
    
    
    
    public synchronized void setDato(int enDato){
      eiOkt.setDato(enDato);
    }
    
    public synchronized void setVarighet(int enVarighet){
      eiOkt.setVarighet(enVarighet);
    }
   
    public synchronized void setKategori(String enKategori){
      eiOkt.setKategori(enKategori);
    }
    
    public synchronized void setTekst(String enTekst){
      eiOkt.setTekst(enTekst);
    }
}


