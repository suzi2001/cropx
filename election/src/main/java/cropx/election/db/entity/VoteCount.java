package cropx.election.db.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import cropx.util.Constants;

@Entity
@Table(name = Constants.VOTECOUNT_TABLE_NAME)
public class VoteCount {
	
	@Id
	@Column(nullable = false)
	private int userId;
	
	@Column(nullable = false)
	private int votesCount;

	
	public VoteCount(int user, int count) {
		this.userId = user;
		this.votesCount = count;
	}


	public int getUserId() {
		return userId;
	}


	public void setUserId(int user) {
		this.userId = user;
	}


	public int getVotesCount() {
		return votesCount;
	}


	@Override
	public String toString() {
		return "VoteCount [userId=" + userId + ", votesCount=" + votesCount + "]";
	}


	public void setVotesCount(int count) {
		this.votesCount = count;
	}
}
