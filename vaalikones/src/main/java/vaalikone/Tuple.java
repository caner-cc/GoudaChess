/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vaalikone;

import java.io.Serializable;
import java.util.Comparator;

/**
 *
 * @author Jonne
 * @param <E>
 * @param <F>
 */
public class Tuple<E,F> implements Serializable, Comparator<Tuple<E,F>> {

    /**
     *
     */
    public E ehdokasId;

    /**
     *
     */
    public F pisteet;

    /**
     *
     * @param e
     * @param f
     */
//    public Tuple() {
//    	//Do nothing....
//    }
    public Tuple(E e, F f) {
        this.ehdokasId = e;
        this.pisteet = f;
    }

	@Override
	public int compare(Tuple<E, F> arg0, Tuple<E, F> arg1) {
		// TODO Auto-generated method stub
		return 0;
	}
   
}
