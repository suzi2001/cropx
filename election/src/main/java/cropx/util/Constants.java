package cropx.util;

public class Constants {
	
	public static final int NUM_OF_USERS = 100;
	public static final String USERVOTES_TABLE_NAME = "uservotes";
	public static final String VOTECOUNT_TABLE_NAME = "votecount";
	
	public static final String VOTINGUSER_COLUMN_NAME = "voting_user";
	public static final String SELECTEDUSER_COLUMN_NAME = "selected_user";
	public static final String USERID_COLUMN_NAME = "user_id";
	public static final String VOTESCOUNT_COLUMN_NAME = "votes_count";
	
	public static final String USER_VOTE_QUERY = "select * from "+ USERVOTES_TABLE_NAME+" where "+ VOTINGUSER_COLUMN_NAME+" = '<userId>'";
	public static final String VOTE_COUNT_QUERY = "select "+VOTESCOUNT_COLUMN_NAME+" from "+ VOTECOUNT_TABLE_NAME+" where "+ USERID_COLUMN_NAME+" = '<userId>'";
	public static final String TOP10_CANDIDATES_QUERY = "SELECT * FROM "+VOTECOUNT_TABLE_NAME+" ORDER BY "+VOTESCOUNT_COLUMN_NAME+" DESC LIMIT 10";
	
	


	

	
	
	



}
