package dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import persist.Ehdokkaat;
import testpack.CandidateMethods;

public class CandidateDao {
	
	private static EntityManagerFactory emf;
	static EntityManager em = getEM();
	
	public static EntityManager getEM() {
		if (emf == null) {
			emf = Persistence.createEntityManagerFactory("vaalikones");
		}
		return emf.createEntityManager();
	}

	/** 
	 * 
	 * The method called by this method saves the lists of answer lists 
	 * given as parameter to the database 
	 * @param v is the list of the multidimension array,
	 *          which will be received from editC.jsp, 
	 *          these arrays contain the ehdokasId, kysymysId and the vastaus
	 */
	public static void updateVastaukset(ArrayList<List<Integer>> v) {
		CandidateMethods.updateV(v);
	}
	
	/**
	 * 
	 * The method called by this method saves the object given as parameter into the database
	 * @param e is an Ehdokkaat object, what will be received from editC.jsp 
	 */
	public static void updateCandidate(Ehdokkaat e) {
		CandidateMethods.update(e);	
	}

}
	
