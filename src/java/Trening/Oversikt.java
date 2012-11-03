package Trening;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection; 
import java.util.List; 



public class Oversikt implements Serializable{
    
    private String bruker = "";
    private ArrayList<TreningsOkt> alleOkt = new ArrayList<TreningsOkt>();
    private int oktnummer = 0; 
    
    public String getBruker() {
        return bruker;
    }
    public int getOktnummer(){
        return oktnummer;
    }
    public void setOktnummer(int nummer){
        oktnummer = nummer; 
    }
    
    
    public void setBruker(String nyBruker){
        bruker = nyBruker;
    }
   
    
    
    public ArrayList<TreningsOkt>getAlleOkter() {
        return alleOkt;
    }
    
    public double getSum(){
        double sum = 0.0;
        int antall = 0;
        for(TreningsOkt enOkt : alleOkt){
            sum += enOkt.getVarighet();
            antall++;
        }
        return sum/antall;
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
            oktnummer ++;  
        }
    }
     
    public void slettOkt(TreningsOkt valgt){
        System.out.println("hit kom jeg");
        alleOkt.remove(valgt);
        System.out.println("har sletta");
     }
      
    
   
      
    
    
}
