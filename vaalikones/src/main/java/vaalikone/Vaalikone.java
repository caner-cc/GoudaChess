 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vaalikone;

import java.io.IOException;
import static java.lang.Integer.parseInt;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import persist.Ehdokkaat;
import persist.Kysymykset;
import persist.Vastaukset;

/**
 * Vaalikone-servlet, vastaa vaalikoneen varsinaisesta toiminnallisuudesta
 *
 * @author Jonne
 */
public class Vaalikone extends HttpServlet {
	static int kysymysSize;
	static int ehdokkaatSize;
	
    //hae java logger-instanssi
    private final static Logger logger = Logger.getLogger(Loki.class.getName());

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int kysymys_id;

        // hae http-sessio ja luo uusi jos vanhaa ei ole vielä olemassa
        HttpSession session = request.getSession(true);

        //hae käyttäjä-olio http-sessiosta
        Kayttaja usr = (Kayttaja) session.getAttribute("usrobj");

        //jos käyttäjä-oliota ei löydy sessiosta, luodaan sinne sellainen
        if (usr == null) {
            usr = new Kayttaja();
            logger.log(Level.FINE, "Luotu uusi käyttäjäolio");
            session.setAttribute("usrobj", usr);
        }
        EntityManagerFactory emf=null;
        EntityManager em = null;
        try {
  	      emf=Persistence.createEntityManagerFactory("vaalikones");
  	      em = emf.createEntityManager();
        }
        catch(Exception e) {
          	response.getWriter().println("EMF+EM EI Onnistu");
          	
          	e.printStackTrace(response.getWriter());
          	
          	return;
        }
        
        // Hae lista ehdokkaista
        Query qE = em.createQuery("SELECT e.ehdokasId FROM Ehdokkaat e");
        List<Integer> ehdokasList = qE.getResultList();
        ehdokkaatSize = ehdokasList.size();
        
        // Hae lista kysymyksistä
        Query qki = em.createQuery("SELECT k.kysymysId FROM Kysymykset k");
        List<Integer> kysymyksetID = qki.getResultList();
        kysymysSize = kysymyksetID.size();

        Query qkk = em.createQuery("SELECT k FROM Kysymykset k");
        List<Kysymykset> kaikkiKysymykset = qkk.getResultList();

        //hae url-parametri func joka määrittää toiminnon mitä halutaan tehdä.
        //func=haeEhdokas: hae tietyn ehdokkaan tiedot ja vertaile niitä käyttäjän vastauksiin
        //Jos ei määritelty, esitetään kysymyksiä.
        String strFunc = request.getParameter("func");

        if (strFunc == null) {

            //hae parametrinä tuotu edellisen kysymyksen nro
            String strKysymys_id = request.getParameter("q");

            //hae parametrina tuotu edellisen kysymyksen vastaus
            String strVastaus = request.getParameter("vastaus");

            // Jos kysymyksen numero (kysId) on asetettu, haetaan tuo kysymys
            // muuten haetaan ensimmäisen kysymyksen id.
            if (strKysymys_id == null) {
            	kysymys_id = kysymyksetID.get(0);
            } else {
                kysymys_id = parseInt(strKysymys_id);
                System.out.println("strKysymys_id was: " + strKysymys_id);
                //jos vastaus on asetettu, tallenna se session käyttäjä-olioon
                if (strVastaus != null) {
                	System.out.println("addVastaus called");
                    usr.addVastaus(kysymyksetID.indexOf(kysymys_id), parseInt(strVastaus));
                }

                //määritä seuraavaksi haettava kysymys
                try {
                	kysymys_id = kysymyksetID.indexOf(kysymys_id) + 1;
                }
                catch(IndexOutOfBoundsException e) {
                	// If we're encountering an error, that means that we've run out of questions.
                	// Get the last kysymys_id and add 1 to it, so it will become an id which doesn't
                	// actually exist in the kysymyksetID ArrayList.
                	kysymys_id = kysymyksetID.size() - 1 + 1;
                }
            }

            //jos kysymyksiä on vielä jäljellä, hae seuraava
            if (kysymyksetID.contains(kysymys_id)) {
                try {
                    //Hae haluttu kysymys tietokannasta
                    Query q = em.createQuery("SELECT k FROM Kysymykset k WHERE k.kysymysId=?1");
                    q.setParameter(1, kysymys_id);
                    //Lue haluttu kysymys listaan
                    List<Kysymykset> kysymysList = q.getResultList();     
                    request.setAttribute("kysymykset", kysymysList);
                    request.setAttribute("idList", kysymyksetID);
                    request.getRequestDispatcher("/vastaus.jsp")
                            .forward(request, response);
                } finally {
                    // Sulje tietokantayhteys
                    if (em.getTransaction().isActive()) {
                        em.getTransaction().rollback();
                    }
                    em.close();
                }

                //jos kysymykset loppuvat, lasketaan tulos!
            } else {

                //Tyhjennetään piste-array jotta pisteet eivät tuplaannu mahdollisen refreshin tapahtuessa
            	for (int i = 0; i < kysymyksetID.size(); i++) {
                    usr.pisteet.set(i, new Tuple<>(0, 0)); 
                }

                //iteroi ehdokaslista läpi
                int ehdokas_id;
                for (int i = 0; i < ehdokasList.size(); i++) {
                	ehdokas_id = ehdokasList.get(i);
                    //Hae lista ehdokkaiden vastauksista
                    Query qV = em.createQuery("SELECT v FROM Vastaukset v WHERE v.vastauksetPK.ehdokasId=?1");
                    qV.setParameter(1, ehdokas_id);
                    List<Vastaukset> vastausList = qV.getResultList();

                    //iteroi vastauslista läpi
                    for (Vastaukset eVastaus : vastausList) {
                        int pisteet; 
                        int vastaus_kysymys_id = eVastaus.getVastauksetPK().getKysymysId();

                        //hae käyttäjän ehdokaskohtaiset pisteet
                        pisteet = usr.getPisteet(ehdokasList.indexOf(ehdokas_id));
                        
                        //laske oman ja ehdokkaan vastauksen perusteella pisteet
                        pisteet += laskePisteet(usr.getVastaus(kysymyksetID.indexOf(vastaus_kysymys_id)), eVastaus.getVastaus());

                        logger.log(Level.INFO, "eID: {0} / k: {1} / kV: {2} / eV: {3} / p: {4}", new Object[]{ehdokas_id, vastaus_kysymys_id, usr.getVastaus(kysymyksetID.indexOf(vastaus_kysymys_id)), eVastaus.getVastaus(), pisteet});
                        usr.addPisteet(ehdokasList.indexOf(ehdokas_id), pisteet);
                    }

                }

                //siirrytään hakemaan paras ehdokas
                strFunc = "haeEhdokas";
            }

        }

        //jos func-arvo on haeEhdokas, haetaan haluttu henkilö käyttäjälle sopivimmista ehdokkaista
        if ("haeEhdokas".equals(strFunc)) {
            //luetaan url-parametristä "top-listan järjestysnumero". Jos ei määritelty, haetaan PARAS vaihtoehto.
            String strJarjestysnumero = request.getParameter("numero");
            Integer jarjestysnumero = 0;
            if (strJarjestysnumero != null) {
                jarjestysnumero = Integer.parseInt(strJarjestysnumero);
            }

            //Lue käyttäjälle sopivimmat ehdokkaat väliaikaiseen Tuple-listaan.
            List<Tuple<Integer, Integer>> tpl = usr.haeParhaatEhdokkaat();

            //hae määritetyn ehdokkaan tiedot
            Query q = em.createQuery(
                    "SELECT e FROM Ehdokkaat e WHERE e.ehdokasId=?1");
            q.setParameter(1, tpl.get(jarjestysnumero).ehdokasId);
            List<Ehdokkaat> parasEhdokas = q.getResultList();

            //hae ko. ehdokkaan vastaukset
            q = em.createQuery(
                    "SELECT v FROM Vastaukset v WHERE v.vastauksetPK.ehdokasId=?1");
            q.setParameter(1, tpl.get(jarjestysnumero).ehdokasId);
            List<Vastaukset> parhaanEhdokkaanVastaukset = q.getResultList();

            //ohjaa tiedot tulosten esityssivulle
            request.setAttribute("kaikkiKysymykset", kaikkiKysymykset);
            request.setAttribute("kayttajanVastaukset", usr.getVastausLista());
            request.setAttribute("parhaanEhdokkaanVastaukset", parhaanEhdokkaanVastaukset);
            request.setAttribute("parasEhdokas", parasEhdokas);
            request.setAttribute("pisteet", tpl.get(jarjestysnumero).pisteet);
            request.setAttribute("jarjestysnumero", jarjestysnumero);
            request.getRequestDispatcher("/tulokset.jsp")
                    .forward(request, response);

            // Sulje tietokantayhteys
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.close();

        }

    }

    private Integer laskePisteet(Integer kVastaus, Integer eVastaus) {
        int pisteet = 0;
        if (kVastaus - eVastaus == 0) {
            pisteet = 3;
        }
        if (kVastaus - eVastaus == 1 || kVastaus - eVastaus == -1) {
            pisteet = 2;
        }
        if (kVastaus - eVastaus == 2 || kVastaus - eVastaus == -2 || kVastaus - eVastaus == 3 || kVastaus - eVastaus == -3) {
            pisteet = 1;
        }
        
        //if (kVastaus - eVastaus == 4 || kVastaus - eVastaus == -4) pisteet = 0;
        return pisteet;

    }

    //<editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
