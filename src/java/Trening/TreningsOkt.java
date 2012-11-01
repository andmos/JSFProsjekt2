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
    boolean skalSlettes;
    boolean endres;
    
    public TreningsOkt(Date dato, String kategori, String tekst, int varighet, boolean skalSlettes, boolean endres){
      this.dato=dato;
      this.kategori=kategori;
      this.oktnr=oktnr;
      this.varighet = varighet;
      this.endres = endres;
      this.skalSlettes = skalSlettes;
      oktnr++;
    }
    
//    public void slettOkt(eiOkt.getSkalSlettes){
//      eiOkt.remoove(get()); 
//    }
    
   
    public synchronized void setSkalSlettes(boolean ny){
      skalSlettes = ny;
    }
    public synchronized boolean getSkalSlettes(){
      return skalSlettes;
    }
    public synchronized void setEndres(boolean ny){
      endres = ny;
    }
    
    public synchronized boolean getEndres(){
      return endres;
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
    
    public synchronized void setTekst(String enTekst){
      tekst = enTekst; 
    }
}
