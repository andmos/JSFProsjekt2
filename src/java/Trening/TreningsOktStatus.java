
package Trening;


public class TreningsOktStatus {
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
        return skalSlettes;
    }
    
    public void setSkalSlettes(boolean nySkalSlettes){
        skalSlettes = nySkalSlettes;
    }
    
    public TreningsOkt getTreningsOkt(){
        return treningsokt;
    }
    
    public void setTreningsOkt(TreningsOkt nyTreningsOkt){
        treningsokt = nyTreningsOkt;
    }
}
