package cropx.election.controller;

import java.util.Optional;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cropx.election.db.entity.Campaign;
import cropx.election.db.repository.CampaignRepository;
import cropx.rest.ElectionsRest;

@RestController
@RequestMapping(value = "/election", consumes = "application/json", produces = "application/json")
public class ElectionController {

	@Autowired
	private CampaignRepository campaignRepository;
	
	@Autowired
	private ElectionsRest electionsRest;

	@RequestMapping(value = "/campaign", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<Optional<Campaign>> getCampaigns() {
		return ResponseEntity.ok(campaignRepository.findAll().stream().findAny());
	}
	
	
	@RequestMapping(value = "/getTop10", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<String> getTop10() {
		return ResponseEntity.ok(electionsRest.getTop10Candidates());
	}
	

	@RequestMapping(value = "/vote/{votingUser}/{selectedUser}", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<String> vote(@PathVariable Integer votingUser, @PathVariable Integer selectedUser) {
		return ResponseEntity.ok(electionsRest.vote(votingUser, selectedUser));
	}
	


}
