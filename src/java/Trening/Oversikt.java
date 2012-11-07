package Trening;

import java.io.Serializable;
import java.util.ArrayList;
import java.sql.*; 
import java.util.Date; 

public class Oversikt implements Serializable{
    
    private String dbdriver = "org.apache.derby.jdbc.ClientDriver";
    private String dbnavn = "jdbc:derby://localhost:1527/waplj_prosjekt;user=waplj;password=waplj";
    private Connection forbindelse = null; 
    Statement setning = null; 
    ResultSet res = null; 
    
    private String bruker = "";
    private ArrayList<TreningsOkt> alleOkt = new ArrayList<TreningsOkt>();
    
    
    public Oversikt(){
      try{
        Class.forName(dbdriver); 
        forbindelse = DriverManager.getConnection(dbnavn); 
        setning = forbindelse.createStatement(); 
        res = setning.executeQuery("select * from trening"); 
         while(res.next()){
           Date enDato = res.getDate("Dato");
           int etOktnr = res.getInt("oktnr"); 
           int enVarighet = res.getInt("varighet"); 
           String enKategori = res.getString("kategorinavn"); 
           String tekst = res.getString("tekst"); 
           TreningsOkt eiOkt = new TreningsOkt(enDato, enKategori, tekst, enVarighet); 
           alleOkt.add(eiOkt);
         }
     
      }catch(SQLException e ){
           System.out.println(e + " Noe gikk galt i databaseforbindelsen");
      }catch(ClassNotFoundException k){
          System.out.println("noe gikk galt med driveren");
      }
      
        finally{
          Opprydder.lukkResSet(res); 
          Opprydder.lukkSetning(setning);
          Opprydder.lukkForbindelse(forbindelse);
      }
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
    
    public void regNyOkt(TreningsOkt ny){
        if( ny != null){
            alleOkt.add(ny);
           
        }
    }
     
    public void slettOkt(TreningsOkt valgt){
        System.out.println("hit kom jeg");
        alleOkt.remove(valgt);
        System.out.println("har sletta");
     }
      
     private void apneForbindelse(){
       try{
         forbindelse = DriverManager.getConnection(dbnavn); 
         System.out.println("Databaseforbindelse oppretta");
       }catch(SQLException e){
         Opprydder.skrivMelding(e, "Konstruktøren");
         Opprydder.lukkForbindelse(forbindelse);
       }
     }
     
     private void lukkForbindelse(){
       System.out.println("lukker");
       Opprydder.lukkForbindelse(forbindelse);
       
     }
   
      
    
    
}
