package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import persist.Ehdokkaat;
import persist.Kysymykset;
import persist.Vastaukset;
import testpack.CandidateMethods;

public class QuestionDao {
	private static EntityManagerFactory emf;
	
	public static EntityManager getEM() {
		if(emf == null) {
			emf = Persistence.createEntityManagerFactory("vaalikones");
		}
		return emf.createEntityManager();
	}
	
	/**
	 * 
	 * @return List<Kysymykset> 
	 * This method is called in QuestionService getAll() function
	 */
	public static List<Kysymykset> getAll(){
		EntityManager em = getEM();
		List<Kysymykset> kysymykset = em.createQuery("SELECT k FROM Kysymykset k").getResultList();
		em.close();

		return kysymykset;
	}
	
	/**
	 * 
	 * @param k is a Kysymykset object given by user, what will be recorded in database
	 */
	public static void saveQ(Kysymykset k){
		EntityManager em = getEM();
		em.getTransaction().begin();
		em.persist(k);
		em.getTransaction().commit();

		// The result of getting all candidates is used as a parameter in the method below the mysql command
		List<Ehdokkaat> e = em.createQuery("SELECT e FROM Ehdokkaat e").getResultList();
		
		/* After saving the question given by user in the database, 
		 * a default answer (3) will be set for each candidate to this question 
		 * (this is why we need the mysql command above)
		*/
		CandidateMethods.saveDefaultV(k, e);
		System.out.println("this is done");
		
		em.close();
	}

	/**
	 * 
	 * @param id, its value will be the value of the id PathParam in QuestionService
	 * @return boolean 
	 * 		   depending on the success of the deletion
	 */
	public static boolean deleteQ(int id){
		EntityManager em = getEM();
		Kysymykset k = (Kysymykset) em.createQuery("SELECT k FROM Kysymykset k where id='"+id+"'").getSingleResult();
		if (k != null) {
			em.getTransaction().begin();
			em.remove(k);
			em.getTransaction().commit();
			
			System.out.println("Deleted question from KYSYMYKSET table");
		}
		else {
			return false;
		}
		
		List<Vastaukset> v = em.createQuery("SELECT v FROM Vastaukset v where kysymys_id='"+id+"'").getResultList();
		if (v != null) {
			for (int i = 0; i < v.size(); i++) {
				em.getTransaction().begin();
				em.remove(v.get(i));
				em.getTransaction().commit();
			}
			em.close();
			System.out.println("Deleted question from VASTAUKSET table");
			return true;
		}
		else {
			return false;
		}

	}
	
	/**
	 * 
	 * @param k is a question list
	 * The method calls another method what makes the updates 
	 * (which uses the parameter of the saveAll() function itself) 
	 */
	public static void saveAll(List<Kysymykset> k){
		CandidateMethods.updateQ(k);
	}
}
