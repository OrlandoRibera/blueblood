/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sd.blueblood;

import java.net.URL;
import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

/**
 *
 * @author zaccarellir
 */
public class LDAP 
{

    private String login;
    private String nombreCompleto;
    private String correoElectronico;
    private String telefono;
    private String ehumano;
    

    public LDAP() 
    {
        
    }

    
    
    public boolean loginLDAP(String username, String password) {
        boolean valido = false;

        if (password == null || password.isEmpty()) {
            return false;
        }

        Hashtable<String, String> env = new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, "ldap://73.20.0.6:389/DC=tigo,DC=net,DC=bo");
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, username.toUpperCase() + "@example.com");
        //env.put(Context.SECURITY_PRINCIPAL, "TIGOBO\\" + usuario);
        env.put(Context.SECURITY_CREDENTIALS, password);

        try {
            InitialDirContext ctx = new InitialDirContext(env);
            valido = true;
            ctx.close();
        } catch (NamingException ne) {

        }
        return valido;
    }
    
    public boolean ldapAuthentication_test(String usuario, String pass) 
    {
        setLogin(usuario.toLowerCase());
        setCorreoElectronico("orlando@example.com");
        setTelefono("7871828283");
        setNombreCompleto("Alemania Bruno");
        setEhumano("36760");
        return true;
    }
    
    public boolean ldapAuthentication(String usuario, String pass) {
        //log.info("---------------------------------ENTRO A VALIDAR LDAP: " + usuario);
        InitialDirContext ctx = null;
        Hashtable env = new Hashtable();
        env.put("java.naming.factory.initial", "com.sun.jndi.ldap.LdapCtxFactory");
        env.put("java.naming.provider.url", "ldap://73.20.0.6:389");
        env.put("java.naming.security.principal", String.valueOf(usuario) + "@example.com");
        env.put("java.naming.security.authentication", "simple");
        env.put("java.naming.security.credentials", pass);
        try {
            ctx = new InitialDirContext(env);

            String nombreCompleto = "";
            SearchControls constraints = new SearchControls();
            constraints.setSearchScope(2);
            String[] attrIDs = {"distinguishedName",
                "sn",
                "givenname",
                "mail",
                "telephonenumber", "displayName", "Initials"};

            constraints.setReturningAttributes(attrIDs);
            String searchBase = "DC=tigo,DC=net,DC=bo";

            NamingEnumeration answer = ctx.search(searchBase, "sAMAccountName=" + usuario, constraints);
            if (answer.hasMore()) 
            {
                Attributes attrs = ((SearchResult) answer.next()).getAttributes();

                //log.info("++++++++++++++++ ATRIBUTOS COMPLETO LDAP: " + attrs.toString());

                if (attrs.get("givenname") != null) 
                    setLogin(usuario.toLowerCase());
                if (attrs.get("mail") != null) 
                    setCorreoElectronico(attrs.get("mail").toString().substring(6, attrs.get("mail").toString().length()));
                if (attrs.get("telephonenumber") != null) 
                    setTelefono(attrs.get("telephonenumber").toString().substring(17, attrs.get("telephonenumber").toString().length()));                
                if (attrs.get("displayName") != null) 
                    setNombreCompleto(attrs.get("displayName").toString().substring(13, attrs.get("displayName").toString().length()));
                if (attrs.get("Initials") != null) 
                    setEhumano(attrs.get("Initials").toString().substring(10, attrs.get("Initials").toString().length()));
                
            }

            return true;
        } catch (Exception ex) {
            //log.error("LDAPAccion Authentication exception : ");
            ex.printStackTrace();
        } finally {
            if (ctx != null) {
                try {
                    ctx.close();
                } catch (NamingException e) {
                    //log.error("LDAPAccion Naming exception : ");
                    e.printStackTrace();
                }
            }

        }
        return false;
    }

    /**
     * @return the login
     */
    public String getLogin() {
        return login;
    }

    /**
     * @param login the login to set
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * @return the nombreCompleto
     */
    public String getNombreCompleto() {
        return nombreCompleto;
    }

    /**
     * @param nombreCompleto the nombreCompleto to set
     */
    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    /**
     * @return the correoElectronico
     */
    public String getCorreoElectronico() {
        return correoElectronico;
    }

    /**
     * @param correoElectronico the correoElectronico to set
     */
    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    /**
     * @return the telefono
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * @param telefono the telefono to set
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * @return the ehumano
     */
    public String getEhumano() {
        return ehumano;
    }

    /**
     * @param ehumano the ehumano to set
     */
    public void setEhumano(String ehumano) {
        this.ehumano = ehumano;
    }

}
