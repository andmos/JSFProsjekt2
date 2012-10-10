package Trening;

import java.io.Serializable; 
import javax.inject.Inject; 
import javax.enterprise.context.SessionScoped; 
import java.util.ArrayList; 



public class TreningsOkt implements Serializable {
    private int oktnr; 
    private int dato; 
    private int varighet; 
    private String kategori; 
    private String tekst; 
    
    
    
    public TreningsOkt(){
      
    }
    
    public int getOktnr(){
      return oktnr; 
    }
    
    public int getDato(){
      return dato; 
    }
    
    public int getVarighet(){
      return varighet; 
    }
    
    public String getKategori(){
      return kategori; 
    }
    
 
    
    public String getTekst(){
      return tekst; 
    }
    
    public void setOktnr(int etOktnr){
      oktnr = etOktnr; 
    }
    
    public void setDato(int enDato){
      dato = enDato; 
    }
    
    public void setVarighet(int enVarighet){
      varighet = enVarighet; 
    }
   
    public void setKategori(String enKategori){
      kategori = enKategori; 
      
    }
    
    public void setTekst(String enTekst){
      tekst = enTekst; 
    }
    
  
    
}
