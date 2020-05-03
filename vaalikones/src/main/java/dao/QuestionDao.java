package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import persist.Kysymykset;
import testpack.CandidateMethods;

public class QuestionDao {
	private static EntityManagerFactory emf;
	
	public static EntityManager getEM() {
		if(emf == null) {
			emf = Persistence.createEntityManagerFactory("vaalikones");
		}
		return emf.createEntityManager();
	}
	
	public static List<Kysymykset> getAll(){
		EntityManager em = getEM();
		List<Kysymykset> kysymykset = em.createQuery("SELECT k FROM Kysymykset k").getResultList();
		em.close();

		return kysymykset;
	}
	
	
	public static void saveQ(Kysymykset k){
		EntityManager em = getEM();
		em.getTransaction().begin();
		em.persist(k);
		em.getTransaction().commit();
		em.close();
	}
	
	public static boolean deleteQ(int id){
		EntityManager em = getEM();
		Kysymykset k = (Kysymykset) em.createQuery("SELECT k FROM Kysymykset k where id='"+id+"'").getSingleResult();
		if (k != null) {
			em.getTransaction().begin();
			em.remove(k);
			em.getTransaction().commit();
			em.close();
			System.out.println("DELETE is committed");
			return true;
		}else { return false; }

	}
	
	public static void saveAll(List<Kysymykset> k){
		CandidateMethods.updateQ(k);
	}
}
