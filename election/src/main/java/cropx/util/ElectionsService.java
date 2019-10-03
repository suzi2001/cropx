package cropx.util;

import java.util.List;

import org.springframework.stereotype.Service;

import cropx.election.db.entity.UserVote;
import cropx.election.db.entity.VoteCount;


@Service
public interface ElectionsService {
	
	public void vote(UserVote userVote);
	
	public List<VoteCount> getTop10Candidates();

}
