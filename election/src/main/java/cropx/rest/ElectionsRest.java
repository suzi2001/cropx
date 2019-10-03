package cropx.rest;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import cropx.election.db.entity.UserVote;
import cropx.election.db.entity.VoteCount;
import cropx.util.ElectionsService;

@Service
public class ElectionsRest {
	
	private static final Logger log = LogManager.getLogger();
	
	@Autowired
	private ElectionsService electionsService;
	
	public String getTop10Candidates() {
		try {			
			List<VoteCount> result = electionsService.getTop10Candidates();
			return new Gson().toJson(result );
		}
		catch (Exception e) {
			log.error("Got error while geting top10 candidates "+ e.getMessage());
			return new Gson().toJson("Got error while geting top10 candidates");		}
	}

	public String vote(int votingUser, int selectedUser) {
		try {
			electionsService.vote(new UserVote(votingUser, selectedUser));
			return new Gson().toJson("Success");
		}
		catch (Exception e) {
			log.error("Got error while voting "+ e.getMessage());
			return new Gson().toJson("Got error while voting");
		}

		
	}
	
	

}
