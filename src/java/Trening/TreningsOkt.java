package Trening;

import java.io.Serializable; 
import javax.inject.Inject; 
import javax.enterprise.context.SessionScoped; 
import java.util.ArrayList; 
import javax.inject.Named;
import java.util.Date; 
import java.text.*;


public class TreningsOkt implements Serializable {
    private int oktnr = 0; 
    private Date dato; 
    private int varighet; 
    private String kategori; 
    private String tekst; 
    
//    public TreningsOkt(Date dato, String kategori, String tekst, int varighet, boolean skalSlettes){
//      this.dato=dato;
//      this.kategori=kategori;
//      this.oktnr=oktnr;
//      this.varighet = varighet;
//      oktnr++;
//    }
    
    public TreningsOkt(){
      oktnr ++; 
    }
    
   
    
    public int getOktnr(){
      return oktnr; 
    }
    
    public Date getDato(){
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
    
    public void setOktnr(){
      // oktnr ++;  
      
    }
    
    public void setDato(Date enDato){
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
