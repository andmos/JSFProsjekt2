
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
      
      public  int getOktnr(){
        return eiOkt.getOktnr(); 
      }
      public void setOktnr(int nyOktnr){
        eiOkt.setOktnr(nyOktnr);
      }
      public int getDato(){
      return eiOkt.getDato(); 
    }
    
    public int getVarighet(){
      return eiOkt.getVarighet(); 
    }
    
    public ArrayList<String> getKategori(){
      return eiOkt.getKategori(); 
    }
    
    public String getKategoriStreng(){
        return eiOkt.getKategoriStreng(); 
    }
    
    public String getTekst(){
      return eiOkt.getTekst(); 
    }
    
    
    
    public void setDato(int enDato){
      eiOkt.setDato(enDato);
    }
    
    public void setVarighet(int enVarighet){
      eiOkt.setVarighet(enVarighet);
    }
   
    public void setKategori(String enKategori){
      eiOkt.setKategori(enKategori);
    }
    
    public void setTekst(String enTekst){
      eiOkt.setTekst(enTekst);
    }
}


