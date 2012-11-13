/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Trening;

/**
 *
 * @author Marius
 */
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
import javax.annotation.PostConstruct;
import javax.sql.DataSource; 
import javax.annotation.Resource; 
import javax.faces.component.*;
import javax.faces.application.*;
        
@Named ("passord") 
@SessionScoped
public class PasswordValidationBean implements Serializable {
  private String input1;
  private String input2;
  private boolean input1Set;
  
  public boolean sjekkPw(String pw){
    int uni;
    int spescount=0;
    int tallcount =0;
    if(!(pw.length()>=6)) return true;
    
    for (int i = 0; i < pw.length(); i++) {
      uni = pw.charAt(i);
      if(uni>= 33 && uni<=48) spescount ++; 
      if(uni>=49 && uni<58) tallcount++;
      }
    if(!(spescount >= 1)) return true;
    if(!(tallcount>=1)) return true;
    return false;
  }
  
  
  public void validateField(FacesContext context, UIComponent component,
      Object value) {
    if (input1Set) {
      input2 = (String) value;
      if (input1 == null || sjekkPw(input1) || (!input1.equals(input2))) {
        ((EditableValueHolder) component).setValid(false);
        context.addMessage(component.getClientId(context), new FacesMessage(
            "Passordet må være minst 6 tegn lang, inneholde minst ett tall og minst ett spesialtegn!"));
      }
    } else {
      input1Set = true;
      input1 = (String) value;
    }
  }
}

