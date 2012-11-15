
package Trening;

import java.io.Serializable; 
import java.sql.Connection;
import java.sql.*; 
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
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.sql.DataSource; 
import javax.annotation.Resource; 
import javax.faces.context.ExternalContext;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
        
@Named ("reg") 
@SessionScoped

public class regtreningBean implements Serializable {
     
    @Resource(name= "jdbc/waplj_prosjekt") DataSource ds;   
     
     private TreningsOkt tempOkt = new TreningsOkt(); 
     private Oversikt oversikt = new Oversikt();
     private List<TreningsOktStatus> tabelldata = Collections.synchronizedList(new ArrayList<TreningsOktStatus>());
     private Connection forbindelse; 
     private PreparedStatement setning = null; 
    private ResultSet res = null;
     
             
     
     private String gjentattPassord = "";
     private String gammeltPassord="";
     private String nyttPassord = "";
     private String navn = "";
     private static Logger logger = Logger.getLogger("com.corejsf");
     
     
      public regtreningBean(){
        
      }

    public String getGammeltPassord() {
        return gammeltPassord;
    }

    public String getNyttPassord() {
        return nyttPassord;
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
              }else{
                oversikt.oppdaterOkt(tos.getTreningsOkt());
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
    
    public void apneForbindelse(){
        try{ if(ds == null){
            throw new SQLException("ingen datasource funnet"); 
        }
            forbindelse = ds.getConnection(); 
            System.out.println("Vellykket forbindelse til datastore ");
            
        
        }catch(Exception e) {
            System.out.println("Feil med databaseforbindelse " + e);
        }
    }
    
    public synchronized String getGjentattPassord() {
    return gjentattPassord;
  }

  public synchronized void setGjentattPassord(String nyttGjentattPassord) {
    gjentattPassord = nyttGjentattPassord;
  }
    
    public synchronized String getPassord(){
        return oversikt.getPassord();
    }
    
    public synchronized void setPassord(String etPassord){
        oversikt.setPassord(etPassord);
    }
            
     private void getUserData() {
      ExternalContext context 
         = FacesContext.getCurrentInstance().getExternalContext();
      Object requestObject =  context.getRequest();
      if (!(requestObject instanceof HttpServletRequest)) {
         logger.severe("request object has type " + requestObject.getClass());
         return;
      }
      HttpServletRequest request = (HttpServletRequest) requestObject;
      navn = request.getRemoteUser();
   }
     
     public String getNavn() { 
      if (navn == null) getUserData(); 
      return navn == null ? "" : navn; 
      
   }
     
     private void getBrukerNavn(){
         navn = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
     }
      
 
     public boolean sjekkPassord(String gammeltPassord){
        boolean t = false;
         try{
             apneForbindelse();
             forbindelse.setAutoCommit(false);
             setning = forbindelse.prepareStatement("select passord from bruker where brukernavn=?");
             setning.setString(1, navn);
            res = setning.executeQuery();
             String fraDb = "";  
            while(res.next()){
               fraDb = res.getString(1);
            }if(gammeltPassord.equalsIgnoreCase(fraDb)){
                t = true;
            }
             
         }catch(SQLException e){
             System.out.println("Passordene stemmte ikke overens " + e.getMessage());
         }finally{
             Opprydder.lukkResSet(res);
             Opprydder.lukkSetning(setning);
             Opprydder.lukkForbindelse(forbindelse);
         }
         return t;
     
     }
     
     public String byttPassord(){
         String svar = "";
         getBrukerNavn();
         if(sjekkPassord(gammeltPassord)){
             try{
                 apneForbindelse();
                 forbindelse.setAutoCommit(false);
                 setning = forbindelse.prepareStatement("UPDATE bruker SET passord=? WHERE brukernavn=?");
                 setning.setString(1, nyttPassord);
                 setning.setString(2, navn);
                 setning.executeUpdate(); 
                 svar = "PassordOk";
             }catch(SQLException e){
                 svar = ("Noe gikk galt med bytte av passord " + e.getMessage());
             }finally{
                 Opprydder.settAutoCommit(forbindelse);
                 Opprydder.lukkSetning(setning);
                 Opprydder.lukkForbindelse(forbindelse);
             }
         }  
         
         return svar;
     }
     
     
     
       
}


