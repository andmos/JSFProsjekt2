package Trening;

import java.util.ArrayList;


public class Oversikt {
    
    private String bruker = "";
    private ArrayList<TreningsOkt> alleOkt = new ArrayList<TreningsOkt>();
    
    public String getBruker() {
        return bruker;
    }
    public void setBruker(String nyBruker){
        bruker = nyBruker;
    }
    
    public ArrayList<TreningsOkt>getAlleOkter() {
        return alleOkt;
    }
    
    public double getSum(){
        double sum = 0.0;
        for(TreningsOkt enOkt : alleOkt){
            sum += enOkt.getVarighet();
        }
        return sum;
    }
    
    public int getAntallOkter(){
        return alleOkt.size();
    }

//    public int getAlleOkterEnMnd() {
//        return alleOkterEnMnd;
//    }
    
    public void regNyOkt(TreningsOkt ny){
        if( ny != null){
            alleOkt.add(ny);
        }
    }
     
    public void slettOkt(TreningsOkt valgt){
        alleOkt.remove(valgt);
     }
      
    /*
    public void endreOkt(TreningsOkt ){
        
    }*/
     
    
}
