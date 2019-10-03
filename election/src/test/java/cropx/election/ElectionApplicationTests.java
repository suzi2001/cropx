package cropx.election;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cropx.election.db.dao.CampaignDao;
import cropx.election.db.dao.UserVoteDao;
import cropx.election.db.dao.VoteCountDao;
import cropx.election.db.entity.Campaign;
import cropx.election.db.entity.UserVote;
import cropx.election.db.entity.VoteCount;
import cropx.election.db.repository.CampaignRepository;
import cropx.util.ElectionsService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ElectionApplicationTests {

	private static final Logger log = LogManager.getLogger();

	@Autowired
	private CampaignRepository campaignRepository;
	
	@Autowired
	private UserVoteDao userVoteDao;
	
	@Autowired
	private VoteCountDao voteCountDao;

	@Autowired
	private CampaignDao campaignDao;
	
	@Autowired
	private ElectionsService electionsService;
	
	
	

	@Test
	public void testDb() {
		//List<Campaign> campaigns = campaignRepository.findAll();
		List<Campaign> campaigns = campaignDao.getAllCampaigns();
		log.debug("Found {} campaigns", campaigns.size());

		campaigns = campaignDao.getSpecialCampaigns();
		log.debug("Found {} special campaigns", campaigns.size());
	}
	
	@Test
	public void testInitUserVotes() {
		log.debug("checking user one vote: ");
		assertEquals(userVoteDao.getUserVote(1).getSelectedUser(), 0);
	}
	
	@Test
	public void testInitVoteCount() {
		log.debug("checking user one vote count: ");
	    assertEquals(voteCountDao.getVoteCountForUser(1), 0);
	}
	
	
	//user1 select user3
	@Test
	public void testVoteFirstTime() {
		electionsService.vote(new UserVote(1, 3));
		assertEquals(userVoteDao.getUserVote(1).getSelectedUser(), 3);
		assertEquals(voteCountDao.getVoteCountForUser(3), 1);
	}
	
	//user2 select user4 and then select user5
	@Test
	public void testUpdateSelection() {
		electionsService.vote(new UserVote(2, 4));
		electionsService.vote(new UserVote(2, 5));
		assertEquals(userVoteDao.getUserVote(2).getSelectedUser(), 5);
		assertEquals(voteCountDao.getVoteCountForUser(4), 0);
		assertEquals(voteCountDao.getVoteCountForUser(5), 1);
	}
	
	
	//user 3 voting for user 6
	//user 4 voting for user 6
	//user 3 voting for user 7
	@Test
	public void test2UsersVotingAndChanging() {
		electionsService.vote(new UserVote(3, 6));
		electionsService.vote(new UserVote(4, 6));
		electionsService.vote(new UserVote(3, 7));
		assertEquals(userVoteDao.getUserVote(3).getSelectedUser(), 7);
		assertEquals(voteCountDao.getVoteCountForUser(6), 1);
		assertEquals(voteCountDao.getVoteCountForUser(7), 1);
	}
	
	
	//user 4 got 2 votes, so he should be the first in the top10 list
	@Test 
	public void testTop10Candidates() {
		electionsService.vote(new UserVote(9, 4));
		for (int i=1; i<4; i++) {
			electionsService.vote(new UserVote(i, i+2));
		}
		List<VoteCount> result = electionsService.getTop10Candidates();
		assertTrue( "Got Empty top10  result", !result.isEmpty());
		for (VoteCount voteCount:result) {
			log.debug(voteCount);
		}
		assertEquals(result.get(0).getUserId(), 4);
	}
}
