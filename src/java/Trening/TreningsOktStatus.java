
package Trening;

import java.io.Serializable;


public class TreningsOktStatus implements Serializable{
    private TreningsOkt treningsokt;
    private boolean skalSlettes;
    
    public TreningsOktStatus(){
        treningsokt = new TreningsOkt();
        skalSlettes = false;
    }
    
    public TreningsOktStatus(TreningsOkt treningsokt){
        this.treningsokt = treningsokt;
        skalSlettes = false;
    }
    
    public synchronized boolean getSkalSlettes(){
       System.out.println(this.skalSlettes + "ER VERDIEN JEG LETER ETTER");  
      return skalSlettes;
    }
    
    public void setSkalSlettes(boolean nySkalSlettes){
        System.out.println(nySkalSlettes + "ER VERDIEN JEG LETER ETTER");
        skalSlettes = nySkalSlettes;
    }
    
    public TreningsOkt getTreningsOkt(){
        return treningsokt;
    }
    
    public void setTreningsOkt(TreningsOkt nyTreningsOkt){
        treningsokt = nyTreningsOkt;
    }
}
