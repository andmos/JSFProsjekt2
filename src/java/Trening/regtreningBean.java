
package Trening;

// FIRST MAKE IT WORK, THAN MAKE IT PRETTY.


import java.io.Serializable; 
import javax.inject.Inject; 
import javax.enterprise.context.SessionScoped; 
import java.util.ArrayList; 
import javax.inject.Named;
import java.util.Date; 
import java.text.*;
import java.util.Collections;
import java.util.Locale;
import javax.faces.context.FacesContext;
import java.util.List;
import javax.annotation.PostConstruct;
        
        
@Named ("reg") 
@SessionScoped

public class regtreningBean implements Serializable {
     private TreningsOkt tempOkt = new TreningsOkt(); 
     private Oversikt oversikt = new Oversikt();
     private List<TreningsOktStatus> tabelldata = Collections.synchronizedList(new ArrayList<TreningsOktStatus>());
     
      public regtreningBean(){
        
      }

    public List<TreningsOktStatus> getTabelldata() {
        return tabelldata;
    }
 
      public synchronized boolean getDataFins(){
          return(tabelldata.size() > 0);
      }
      
      public synchronized String getBruker(){
          return oversikt.getBruker();
      }
      
      public synchronized void setBruker(String nyBruker){
          oversikt.setBruker(nyBruker);
      }
      
      public synchronized double getSum(){
          return oversikt.getSum();
      }
      
      public synchronized int getAntallOkter(){
          return oversikt.getAntallOkter();
      }
      
      public synchronized TreningsOkt getTempOkt(){
          return tempOkt;
      }
      
      public synchronized void setTempOkt(TreningsOkt nyTempOkt){
          tempOkt = nyTempOkt;
      }
      
      
      public synchronized void oppdater(){
          if(!tempOkt.getTekst().trim().equals("")){
              TreningsOkt nyOkt = new TreningsOkt(tempOkt.getDato(), tempOkt.getKategori(), tempOkt.getTekst(), tempOkt.getVarighet());
              nyOkt.setOktNr(TreningsOkt.setLopenummer());
              oversikt.regNyOkt(nyOkt);
              tabelldata.add(new TreningsOktStatus(nyOkt));
              tempOkt.nullstill();
          }
          
          int indeks = tabelldata.size()-1;
                  
          while(indeks >= 0){
              TreningsOktStatus tos = tabelldata.get(indeks);
              if(tos.getSkalSlettes()){
                 System.out.println("indeks =" + indeks);
                oversikt.slettOkt(tos.getTreningsOkt());
                  tabelldata.remove(indeks);
              }
              indeks--;
          }
      }
    
      public synchronized Date getDato(){
      return tempOkt.getDato(); 
    }
    
    public synchronized int getVarighet(){
      return tempOkt.getVarighet(); 
    }
    
  

    public synchronized String getTekst(){
      return tempOkt.getTekst(); 
    }
    
    public synchronized void setDato(Date nyDato){
      tempOkt.setDato(nyDato);
    }
    
    public synchronized void setVarighet(int enVarighet){
      tempOkt.setVarighet(enVarighet);
    }
   
    public synchronized void setKategori(String enkategori){
      tempOkt.setKategori(enkategori);    
    }
    
    public synchronized void setTekst(String enTekst){
      tempOkt.setTekst(enTekst);
    }
    
    public synchronized ArrayList<String> getKategorier(){
        return oversikt.Kategorier(); 
    }
    
    
    public String englishAction(){
        System.out.println("her er jeg");
        FacesContext context = FacesContext.getCurrentInstance();
        context.getViewRoot().setLocale(new Locale("en"));
        return null;
    }
    
    public String norwegianAction(){
        FacesContext context = FacesContext.getCurrentInstance();
        context.getViewRoot().setLocale(new Locale("no"));
        return null;
    }
    
    @PostConstruct 
    public synchronized void setDatatable(){
      List<TreningsOktStatus> temp = Collections.synchronizedList(new ArrayList<TreningsOktStatus>()); 
     for(TreningsOkt t : oversikt.getAlleOkter()){
       temp.add(new TreningsOktStatus(t));
     
     }
     tabelldata = temp; 
    }
    
    
    
    
    
}


