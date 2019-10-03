package cropx.election.db.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import cropx.util.Constants;

@Entity
@Table(name = Constants.USERVOTES_TABLE_NAME)
public class UserVote {
	
	@Id
	@Column(nullable = false)
	private int votingUser;
	
	@Column(nullable = false)
	private int selectedUser;
	
	public UserVote(int votingUser, int selectedUser ) {
		this.votingUser = votingUser;
		this.selectedUser = selectedUser;
	}

	public int getVotingUser() {
		return votingUser;
	}

	public void setVotingUser(int votingUser) {
		this.votingUser = votingUser;
	}

	public int getSelectedUser() {
		return selectedUser;
	}

	public void setSelectedUser(int selectedUser) {
		this.selectedUser = selectedUser;
	}
	
	

}
