/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.metropolitan.cs230.projekat.auth;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpSession;
import rs.ac.metropolitan.cs230.projekat.entity.User;

/**
 *
 * @author Mario
 */
@Named
@ApplicationScoped
public class Login {
    @PersistenceContext(unitName = "CS230-PZPU")
    private EntityManager em;

    private static final long serialVersionUID = 1L;

    private String username;
    private String password;

    private String rola = "none";
    private boolean loggedIn;

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRola() {
        return rola;
    }

    public void setRola(String rola) {
        this.rola = rola;
    }
    
    public String login() {
        User user = null;
        boolean valid = false;

        System.out.println("USAO SAM U LOGIN METODU");

        try {
            user = (User) em.createNamedQuery("User.findByUsername").setParameter("username", username).getSingleResult();
            System.out.println("POSTOJI KORISNIK SA USERNAME-om > " + username);
            valid = validateUser(user);
        } catch (NoResultException e) {
            System.out.println("NE POSTOJI KORISNIK SA USERNAME-om > " + username);
            valid = false;
        }

        if (valid) {
            HttpSession session = SessionUtils.getSession();
            session.setMaxInactiveInterval(100 * 9999);

            System.out.println("KREIRAMO SESIJU");
            System.out.println("ID SESIJE :" + session.getId());
            session.setAttribute("username", user.getUsername());
            System.out.println("SETUJEMO ATRIBUT SESIJE -user- u " + user.getUsername());
            System.out.println("ATRIBUT SESIJE -user- : " + session.getAttribute("username"));

          //  session.setAttribute("role", user.getUserRole());
       //     System.out.println("SETUJEMO ATRIBUT SESIJE -role- u " + user.getUserRole());
            System.out.println("ATRIBUT SESIJE -role- : " + session.getAttribute("role"));

           // session.setAttribute("token", user.getToken());
         //   System.out.println("SETUJEMO ATRIBUT SESIJE -token- u " + user.getToken());
         //   System.out.println("ATRIBUT SESIJE -token- : " + session.getAttribute("token"));

            loggedIn = true;
            System.out.println("KORISNIK JE LOGOVAN");

//            if (user.getUserRole().equalsIgnoreCase("admin")) {
//                System.out.println("AKO JE ADMIN IDEMO NA ADMIN/DASHBOARD");
//                return "admin/dashboard";
//            } else {
//                System.out.println("AKO JE USER IDEMO NA INDEX");
//                return "index";
//            }
//        } else {
//            System.out.println("USER NIJE VALIDAN, VRATI GA NA LOGIN");
//            FacesContext context = FacesContext.getCurrentInstance();
//            context.addMessage(null, new FacesMessage("Bad request", "Molimo Vas da proverite podatke i pokušate ponovo."
//            ));
//            return "login";
//        }
return "LOGOVAN";
    }
        else{
    return "LOGOVAN";
        }
}
    private boolean validateUser(User user) {
        if (user.getPassword().equals(password)) {
            System.out.println("USEROV PASSWORD JE DOBAR");
            return true;
        } else {
            System.out.println("USEROV PASSWORD NIJE DOBAR");
            return false;
        }
    }
}
