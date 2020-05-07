/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vaalikone;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.logging.Logger;

/**
 *
 * @author Jonne
 */
public class Kayttaja implements Serializable {

    /**
	 * 
	 */
//	private static final long serialVersionUID = 3262098698622771486L;
	/**
	 * 
	 */

//	private final ArrayList<Integer> vastaus = new ArrayList<>(20);
	private ArrayList<Integer> vastaus = new ArrayList<>();
    ArrayList<Tuple<Integer, Integer>> pisteet = new ArrayList<>();
    private final static Logger logger = Logger.getLogger(Loki.class.getName());

    /**
     * Kayttaja-olioon tallennetaan vaalikoneen käyttäjän tietoja.
     */
//    public Kayttaja() {
//
//        //täytelläänhän listat valmiiksi
//        for (int i = 0; i < 20; i++) {
//            this.vastaus.add(0);
//            this.pisteet.add(new Tuple<>(0, 0));
//        }
//
//    }

    public void taytaVastauksetJaPisteet() {
        //täytelläänhän listat valmiiksi
        for (int i = 0; i < Vaalikone.ehdokkaatSize; i++) {
            this.pisteet.add(new Tuple<>(0, 0));
        }
        for (int i = 0; i < Vaalikone.kysymysSize; i++) {
        	this.vastaus.add(0);
        }
        
    }
    /**
     *
     * @return Integer-lista käyttäjän vastauksista
     */
    public ArrayList<Integer> getVastausLista() {
        return this.vastaus;
    }

    /**
     * Hae pisteet-listasta yksittäiset pisteet
     *
     * @param index
     * @return pisteet ehdokkaaseen nähden
     */
    public Integer getPisteet(int index) {
        if (this.pisteet.size() >= index) {
            return this.pisteet.get(index).pisteet;
        } else {
            return 0;
        }
    }

    /**
     * Aseta pisteet tiettyyn ehdokkaaseen nähden
     *
     * @param index
     * @param ehdokasId ehdokkaan id-numero
     * @param pisteet Arvo, mikä lisätään
     */
    public void addPisteet(Integer index, Integer ehdokasId, Integer pisteet) {
        this.pisteet.set(index, new Tuple<>(ehdokasId, pisteet));
    }

    /**
     * Hae yksittäinen käyttäjän vastaus kysymykseen
     *
     * @param index kysymyksen numero
     * @return Yksittäinen integer-muotoinen vastaus käyttäjän vastaus-listasta
     */
    public Integer getVastaus(int index) {
        return this.vastaus.get(index);
    }

    /**
     * Lisää vastaus
     *
     * @param index kysymyksen numero
     * @param vastaus vastauksen arvo
     */
    public void addVastaus(Integer index, Integer vastaus) {
    	if (this.vastaus.size()==0) {
    		taytaVastauksetJaPisteet();
    	}
    	this.vastaus.set(index, vastaus);
    }

    /**
     * Hae parhaat ehdokkaat pistemäärän mukaan järjesteltynä
     *
     * @return Tuple-lista, (ehdokkaan id, pisteet)
     */
    public ArrayList<Tuple<Integer, Integer>> haeParhaatEhdokkaat() {

        /* Järjestä pisteet sisältävä Tuple.
         *  Javan Collections.sort oletuksena järjestää listat pienimmästä suurimpaan
         *  Collections.reverseOrder kääntää järjestyksen toisin päin
         */
        Collections.sort(this.pisteet, Collections.reverseOrder(new ComparePisteet()));
        return this.pisteet;
    }

    static class ComparePisteet implements Comparator<Tuple<Integer, Integer>> {
    	@Override
        public int compare(Tuple<Integer, Integer> o1, Tuple<Integer, Integer> o2) {
    		return o1.pisteet.compareTo(o2.pisteet);
    	}
    };

}
