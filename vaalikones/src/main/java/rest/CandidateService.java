package rest;


import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import dao.CandidateDao;
import persist.Ehdokkaat;

@Path("CandidateService")
public class CandidateService {

	/**
	 *
	 * @param v is a multidimension array which contains ehdokkaasId, kysymysId, vastaus in it's arrays, 
	 * it will be recieved from the editC.jsp form as a JSON string
	 */
    @PUT
    @Path("/setV")
	@Consumes(MediaType.APPLICATION_JSON)
	public void putVastaukset(ArrayList<List<Integer>> v) {
    	System.out.println("Hello from postVastaukset");
    	CandidateDao.updateVastaukset(v);
	}

    /**
	 *
	 * @param ehdokkaat is an Ehdokkat object, 
	 * it will be recieved from the editC.jsp form as a JSON string
	 */
    @PUT
    @Path("/setC")
	@Consumes(MediaType.APPLICATION_JSON)
	public void putCandidates(Ehdokkaat ehdokkaat) {
    	System.out.println("Hello from postCandidates");
    	CandidateDao.updateCandidate(ehdokkaat);
	}

}