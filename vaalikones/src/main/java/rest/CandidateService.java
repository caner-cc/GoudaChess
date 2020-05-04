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
	 * @param v 
	 * 		  is a multidimension array which contains ehdokkaasId, kysymysId, vastaus in it's arrays, 
	 *   	  which will be received from the editC.jsp form
	 */
    @PUT
    @Path("/setV")
	@Consumes(MediaType.APPLICATION_JSON)
	public void putVastaukset(ArrayList<List<Integer>> v) {
    	CandidateDao.updateVastaukset(v);
	}

    /**
	 *
	 * @param ehdokkaat 
	 * 		  is an Ehdokkaat object, 
	 *    	  which will be received from the editC.jsp form
	 */
    @PUT
    @Path("/setC")
	@Consumes(MediaType.APPLICATION_JSON)
	public void putCandidates(Ehdokkaat ehdokkaat) {
    	CandidateDao.updateCandidate(ehdokkaat);
	}

}