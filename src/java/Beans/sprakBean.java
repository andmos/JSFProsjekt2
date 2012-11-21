
package Beans;

import java.io.Serializable;
import java.util.Locale;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named("sprak")
@SessionScoped
public class SprakBean implements Serializable {
    
    
    /**
     * Setter lokale instillinger for språk, engelsk.
     */
    public String englishAction() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getViewRoot().setLocale(new Locale("en"));
        return null;
    }
    
    /**
     * Setter lokale instillinger for språk, norsk.
     */
    public String norwegianAction() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getViewRoot().setLocale(new Locale("no"));
        return null;
    }
}
