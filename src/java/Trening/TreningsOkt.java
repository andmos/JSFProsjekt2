package Trening;

import java.util.Date; 



public class TreningsOkt {
    
    private Date dato; 
    private int varighet; 
    private String kategori; 
    private String tekst;
 
    
    public TreningsOkt(Date dato, String kategori, String tekst, int varighet){
      this.dato=dato;
      this.kategori=kategori; 
      this.varighet = varighet;
      this.tekst=tekst;
     
    }
    
    public TreningsOkt(){
        nullstill();
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
    
   
    
    public synchronized void setDato(Date enDato){
     dato = enDato; 
    }
    
    
    public synchronized void setVarighet(int enVarighet){    
        varighet = enVarighet;
    }
   
    public synchronized void setKategori(String enKategori){
      kategori = enKategori; 
      
    }
    
    public synchronized void setTekst(String enTekst){
      tekst = enTekst;
    }
    
    public final synchronized void nullstill() {
     
      dato = new Date();
      tekst = "";
      varighet = 0;
  }
}
