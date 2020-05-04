package rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import dao.QuestionDao;
import persist.Kysymykset;

@Path("/QuestionService")
public class QuestionService {
	
	/**
	 * 
	 * @return Question.Dao.getAll() 
	 * 	  	   this method is called to get and return all the questions can be found in the database, 
	 * 		   to provide them in the editQ.jsp file by the EditQ.java servlet (doGet())
	 */
	@GET
	@Path("/getall")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Kysymykset> getAll(){
		return QuestionDao.getAll();
		
	}
	
	/**
	 * 
	 * @param k this method receives a question from editQ.jsp,
	 *   	  	what forwarded here from EditQ.java servlet (doPost()) by client the kysymys_id will be automatically generated, 
	 * 		  	by means of specific annotations set in Kysymykset.java, also, auto_increment needs to be set for kysymys_id
	 */
	@POST
	@Path("/saveQ")
	@Produces(MediaType.APPLICATION_JSON)
	public void saveQuestion(Kysymykset k){
		QuestionDao.saveQ(k);
		System.out.println("hello from Qservice");
	}
	
	/**
	 * 
	 * @param id this is a pathparam which indicates the id of the question,
	 * 		  	 what has to be deleted from the database, 
	 *        	 this id comes from EditQ.java servlet (doGet()) by client
	 * 
	 * @return boolean 
	 * 		   depending on the success of the deletion
	 */
	@DELETE
	@Path("/delete/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean deleteQ(@PathParam("id") int id){
		return QuestionDao.deleteQ(id);
	}
	
	/**
	 * 
	 * @param k this is a Kysymykset list which is provided by AJAX in editQ.jsp
	 * 		  	this list contains every questions which will be processes here
	 *		  	after clicking 'Save edited questions' button in editQ.jsp
	 */
	@PUT
	@Path("/setAll")
	@Consumes(MediaType.APPLICATION_JSON)
	public void setAll(List<Kysymykset> k){
		QuestionDao.saveAll(k);
	}
}
