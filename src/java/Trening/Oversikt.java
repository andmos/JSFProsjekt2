package Trening;

import java.util.ArrayList;


public class Oversikt {
    
    private String bruker = "";
    private ArrayList<TreningsOkt> alleOkt = new ArrayList<TreningsOkt>();
    
    public Oversikt(){
        //bruker - innlogging
    }

    public String getBruker() {
        return bruker;
    }
    public void setBruker(String nyBruker){
        bruker = nyBruker;
    }
    
    public ArrayList<TreningsOkt>getAlleOkter() {
        return alleOkt;
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
