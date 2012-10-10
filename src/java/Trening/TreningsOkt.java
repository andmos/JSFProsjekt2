package Trening;

import java.io.Serializable; 
import javax.inject.Inject; 
import javax.enterprise.context.SessionScoped; 
import java.util.ArrayList; 



public class TreningsOkt implements Serializable {
    private int oktnr; 
    private int dato; 
    private int varighet; 
    private ArrayList<String> kategori = new ArrayList<String>();
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
    
    public ArrayList<String> getKategori(){
      return kategori; 
    }
    
    public String getKategoriStreng(){
      String ut = ""; 
      for (int i = 0; i < kategori.size(); i++) {
        ut += kategori.get(i) + "\n"; 
      }
      return ut; 
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
      kategori.add(enKategori);
    }
    
    public void setTekst(String enTekst){
      tekst = enTekst; 
    }

}
