package Trening;

import java.util.Date; 



public class TreningsOkt {
    private int oktNr;
    private Date dato; 
    private int varighet; 
    private String kategori; 
    private String tekst;
    private static int lopenummer = 0; 
    
    public TreningsOkt(Date dato, String kategori, String tekst, int varighet){
      this.dato=dato;
      this.kategori=kategori; 
      this.varighet = varighet;
      this.tekst=tekst;
    }
    
    
    public TreningsOkt(){
        nullstill();
    }
    
    public static int setLopenummer(){
      lopenummer++;
      return lopenummer; 
      
    }
    
    public int getOktNr(){
      return oktNr;
    }
    
    public void setOktNr(int nyttNr){
      oktNr = nyttNr;
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
    
    public final void nullstill() {
     
      dato = new Date(); 
      tekst = "";
      varighet = 0;
  }
}
