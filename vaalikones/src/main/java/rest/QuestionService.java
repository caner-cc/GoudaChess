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
	@GET
	@Path("/getall")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Kysymykset> getAll(){
		return QuestionDao.getAll();
		
	}
	
	@POST
	@Path("/saveQ")
	@Produces(MediaType.APPLICATION_JSON)
	public void saveQuestion(Kysymykset k){
		QuestionDao.saveQ(k);
	}
	
	@DELETE
	@Path("/delete/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean deleteQ(@PathParam("id") int id){
		return QuestionDao.deleteQ(id);
	}
	
	@PUT
	@Path("/setAll")
	@Consumes(MediaType.APPLICATION_JSON)
	public void setAll(List<Kysymykset> k){
		QuestionDao.saveAll(k);
	}
}
