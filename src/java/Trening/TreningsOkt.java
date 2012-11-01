package Trening;

import java.io.Serializable; 
import java.util.Date; 



public class TreningsOkt implements Serializable {
    private int oktnr = 0; 
    private Date dato; 
    private int varighet; 
    private String kategori; 
    private String tekst;
    
    public TreningsOkt(Date dato, String kategori, String tekst, int varighet){
      this.dato=dato;
      this.kategori=kategori;
      this.oktnr=oktnr;
      this.varighet = varighet;
      this.tekst=tekst;
      oktnr++;
    }
    
    public TreningsOkt(){
        nullstill();
    }
    
    public synchronized int getOktnr(){
      return oktnr; 
    }
    
    public synchronized Date getDato(){
      return dato; 
    }
    
    public synchronized int getVarighet(){
      return varighet; 
    }
    
    public synchronized String getKategori(){
      return kategori; 
    }
    
 
    
    public synchronized String getTekst(){
      return tekst; 
    }
    
    public synchronized void setOktnr(){
      // oktnr ++;  
      
    }
    
    public synchronized void setDato(Date enDato){
     dato = enDato; 
    }
    
    
    public synchronized void setVarighet(int enVarighet){    
        varighet = enVarighet;
    }
   
    public synchronized void setKategori(String enKategori){
      kategori = enKategori; 
      
    }
    
    public synchronized String setTekst(String enTekst){
      tekst = enTekst;
      return tekst;
    }
    
    public final synchronized void nullstill() {
      oktnr = 0;
      dato = new Date();
      tekst = "";
      varighet = 0;
  }
}
