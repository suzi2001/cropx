package cropx.util;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cropx.election.db.dao.UserVoteDao;
import cropx.election.db.dao.VoteCountDao;
import cropx.election.db.entity.UserVote;
import cropx.election.db.entity.VoteCount;

@Service
public class ElectionsServiceImpl implements ElectionsService {
	
	
	@Autowired
	private UserVoteDao userVoteDao;
	
	@Autowired
	private VoteCountDao voteCountDao;
	
	@Transactional
	@Override
	public void vote(UserVote currentUserVote) {
		UserVote prevVote = userVoteDao.getUserVote(currentUserVote.getVotingUser());
		if (prevVote.getSelectedUser()== currentUserVote.getSelectedUser()) {
			//user is trying to vote again for same candidate, so do nothing
			return;
		}
		int currentVoteCount = 0;
		if (prevVote.getSelectedUser()==0) {
			//user is voting for the first time
			currentVoteCount = voteCountDao.getVoteCountForUser(currentUserVote.getSelectedUser());
			currentVoteCount = currentVoteCount+1;
		}else {
			//user updating his vote
			currentVoteCount = voteCountDao.getVoteCountForUser(currentUserVote.getSelectedUser());
			int prevVoteCount = voteCountDao.getVoteCountForUser( prevVote.getSelectedUser());
			currentVoteCount = currentVoteCount+1;
			if (prevVoteCount>0) {
				prevVoteCount = prevVoteCount-1;
			}
			voteCountDao.insertVoteCount(new VoteCount(prevVote.getSelectedUser(), prevVoteCount));
		}
		userVoteDao.insertUserVote(currentUserVote);
		voteCountDao.insertVoteCount(new VoteCount(currentUserVote.getSelectedUser(), currentVoteCount));
	}

	

	

	@Override
	public List<VoteCount> getTop10Candidates() {
		return voteCountDao.getTop10Candidates();
		
	}

}
