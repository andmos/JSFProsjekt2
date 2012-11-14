package Trening;

import java.io.Serializable;
import java.util.ArrayList;
import java.sql.*; 
import java.util.Date; 
import java.text.SimpleDateFormat; 
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

public class Oversikt implements Serializable{
  
    @Resource(name = "jdbc/waplj_prosjekt")
    private DataSource ds;
   
    // Databasevariabler  
  
    private Connection forbindelse = null; 
    PreparedStatement setning = null; 
    ResultSet res = null; 
   
    // Klassevariabler
    private String bruker = "";
    private boolean loggetInn;    
    private String gammelPassord=""; 
    private String nytt = ""; 
    
    private ArrayList<TreningsOkt> alleOkt = new ArrayList<TreningsOkt>();
    
    private static Logger logger = Logger.getLogger("trening");
    
    public Oversikt(){
      try{
        ds = (DataSource) new InitialContext().lookup("jdbc/waplj_prosjekt");
        forbindelse = ds.getConnection(); 
        setning = forbindelse.prepareStatement("select * from trening"); 
        res = setning.executeQuery(); 
         while(res.next()){
           Date enDato = res.getDate("Dato");
           int etOktnr = res.getInt("oktnr"); 
           int enVarighet = res.getInt("varighet"); 
           String enKategori = res.getString("kategorinavn"); 
           String tekst = res.getString("tekst"); 
           TreningsOkt eiOkt = new TreningsOkt(enDato, enKategori, tekst, enVarighet); 
           eiOkt.setOktNr(etOktnr);
           alleOkt.add(eiOkt);
         }
     
      }catch(SQLException e){
           System.out.println(e + " Noe gikk galt i databaseforbindelsen");
      }catch(NamingException k){
          System.out.println("Noe gikk galt med kastinga på NAVN" + k);
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
       double sum = 0; 
       int varighet = 0; 
       try{
         apneForbindelse(); 
         String settning = "select SUM(varighet), count(oktnr) FROM trening"; 
         setning = forbindelse.prepareStatement(settning); 
         res = setning.executeQuery(); 
         while(res.next()){
           varighet = res.getInt(1); 
           sum = res.getDouble(2); 
         }
         
       }catch(SQLException e){
         System.out.println("Noe gikk galt med gjennomsnittet " + e);
       }finally{
        Opprydder.lukkSetning(setning);
        Opprydder.settAutoCommit(forbindelse);
        lukkForbindelse(); 
       }
       return (double) varighet / sum; 
    
    }
    
    
    public int getAntallOkter(){
        return alleOkt.size();
    }
    
    public void regNyOkt(TreningsOkt ny){
    
      SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");      
      Date dato = ny.getDato(); 
      String datodb  = formatter.format(dato);
      int varighet = ny.getVarighet();
      String tekst = ny.getTekst(); 
      String kategori = ny.getKategori(); 
      System.out.println(kategori);
      try{
          apneForbindelse();
          forbindelse.setAutoCommit(false);
          setning = forbindelse.prepareStatement("insert into trening (dato, varighet, kategorinavn, tekst, brukernavn)" + "VALUES (DATE('"+datodb+"'),?,?,?,?)");
          setning.setInt(1, varighet);
          setning.setString(2, kategori);
          setning.setString(3, tekst);
          setning.setString(4, bruker);
          setning.executeUpdate();
          Opprydder.lukkSetning(setning); 
          
          setning = forbindelse.prepareStatement("select max(oktnr)from trening"); 
          res = setning.executeQuery();
          int tall = 0;
          while(res.next()){
            tall = res.getInt(1);
          }
          ny.setOktNr(tall);
          if( ny != null){
            alleOkt.add(ny);       
        }
          
        }catch(SQLException e){
          System.out.println("noe gikk galt med REGISTRERINGEN. " + e);
          Opprydder.skrivMelding(e, "RegOkt");
        }finally{
          Opprydder.lukkSetning(setning);
          Opprydder.settAutoCommit(forbindelse);
          lukkForbindelse();
          
        }
    }
     
    public void oppdaterOkt(TreningsOkt valgt){
        
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String datodb  = formatter.format(valgt.getDato());
    
      try{
        apneForbindelse();
        setning = forbindelse.prepareStatement("UPDATE trening SET dato=?, varighet=?, kategorinavn=?, tekst=? WHERE oktnr=?");
        setning.setString(1, datodb); 
        setning.setInt(2, valgt.getVarighet());
        setning.setString(3, valgt.getKategori());
        setning.setString(4, valgt.getTekst());
        setning.setInt(5, valgt.getOktNr());
        setning.executeUpdate(); 
   
      }catch(SQLException e){
        System.out.println("Noe gikk galt med oppdateringen" + e);
      
      }finally{
        Opprydder.lukkSetning(setning);
        lukkForbindelse();
      }        
    }
    
    
    public void slettOkt(TreningsOkt valgt){
      
      try{
          apneForbindelse(); 
          forbindelse.setAutoCommit(false);
          String slett = "delete from trening WHERE oktnr = ?";
          setning = forbindelse.prepareStatement(slett);
          setning.setInt(1, valgt.getOktNr());
          alleOkt.remove(valgt);
          setning.executeUpdate();
      
      }catch(SQLException e){
          System.out.println("Noe gikk galt med slettingen " + e);
        }finally{
        Opprydder.lukkSetning(setning);
        Opprydder.settAutoCommit(forbindelse);
        lukkForbindelse(); 
    }
  }
      
     
     
     private void lukkForbindelse(){
       System.out.println("lukker");
       Opprydder.lukkForbindelse(forbindelse);
       
     }
   
     public ArrayList<String> Kategorier(){
       ArrayList<String> kategorier = new ArrayList<String>(); 
       try{
         apneForbindelse(); 
         setning = forbindelse.prepareStatement("select kategorinavn from kategori"); 
         res = setning.executeQuery();
         while(res.next()){
           kategorier.add(res.getString(1));
         }
       }catch(SQLException e){
         System.out.println("Noe gikk galt i kategorihentingen" + e);
       }finally{
         Opprydder.lukkSetning(setning);
         lukkForbindelse(); 
       }
       return kategorier; 
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
     
   
     
    
        public String getName() { 
      if (bruker == null) getUserData(); 
      return bruker == null ? "" : bruker; 
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
      bruker = request.getRemoteUser();
   }
     public String getGammel(){
         return gammelPassord; 
     }
     public void setGammel(String gammel){
         gammelPassord = gammel; 
     }
     public String getNytt(){
         return nytt; 
     }
     public void setNytt(String ny){
         nytt = ny; 
     }
     
     
     public String setPassord(){
        
         try{
             apneForbindelse(); 
             forbindelse.setAutoCommit(false);
             setning = forbindelse.prepareStatement("select passord from bruker where brukernavn = ?"); 
             setning.setString(1, getName());
             setning.executeQuery();
             String fraDb = "";  
            while(res.next()){
               fraDb = res.getString(1); 
            }
             Opprydder.lukkSetning(setning);
             if(fraDb.equals(gammelPassord)){
                  setning = forbindelse.prepareStatement("update passord from bruker where brukernavn = ? and passord = ?"); 
                 setning.setString(1, getName()); 
                 setning.setString(2, nytt);
                 setning.executeUpdate();
                 return "PassordOK"; 
             }else{
                 return "PassordFeil"; 
             }
         }catch(SQLException e){
             System.out.println("Feil ved bytting av passord " + e.getMessage());
         }finally{
             Opprydder.lukkSetning(setning);
             lukkForbindelse(); 
         }
         return "PassordFeil"; 
     }
    
    
}
