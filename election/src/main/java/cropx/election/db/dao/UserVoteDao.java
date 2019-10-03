package cropx.election.db.dao;



import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import cropx.election.db.entity.UserVote;
import cropx.util.Constants;

@Service
public class UserVoteDao {
	

	private static final Logger log = LogManager.getLogger();
	
	@Autowired
	protected JdbcTemplate jdbcTemplate;
	
	@Value("${cropx.query.insert_table}")
	private String insertTable;
	
	private String insertStr; 
	
	@PostConstruct
	public void init() {
		log.debug("Init UserVoteDao");
		//TODO: remove this later
	//	jdbcTemplate.update(droptUserVote.replace("<table>", Constants.USERVOTES_TABLE_NAME));

		insertStr = insertTable.replace("<table>", Constants.USERVOTES_TABLE_NAME).replace("<col1>", Constants.VOTINGUSER_COLUMN_NAME).replace("<col2>", Constants.SELECTEDUSER_COLUMN_NAME);
		String currentInsertStr = insertStr.replace("<val2>", "0");
		for (int i =1; i<=Constants.NUM_OF_USERS;i++) {
			jdbcTemplate.update(currentInsertStr.replace("<val1>", String.valueOf(i)));
		}
	}
	
	
	private static final RowMapper<UserVote> USER_VOTE_MAPPER = (rs, rowNum) -> {
		UserVote userVote = new UserVote(rs.getInt(Constants.VOTINGUSER_COLUMN_NAME), rs.getInt(Constants.SELECTEDUSER_COLUMN_NAME));
		return userVote;
	};
	
	
	//UPDATE <table> SET <col1> = <val1> WHERE <condition>

	
	@Value("${cropx.query.update_table}")
	private String updateTable;
	
	public void insertUserVote(UserVote currentUserVote) {
		log.debug("Updating user "+currentUserVote.getVotingUser() +" is voting for user "+currentUserVote.getSelectedUser());
		String updateQuery = updateTable.replace("<table>", Constants.USERVOTES_TABLE_NAME).replace("<col1>", Constants.SELECTEDUSER_COLUMN_NAME).replace("<val1>", String.valueOf(currentUserVote.getSelectedUser()));
		String whereClause = Constants.VOTINGUSER_COLUMN_NAME +"='"+String.valueOf(currentUserVote.getVotingUser())+"'";
		updateQuery = updateQuery.replace("<condition>", whereClause);
		jdbcTemplate.update(updateQuery);
		
	}
	
	
	
	public UserVote getUserVote(int userId) {	
		return jdbcTemplate.query(Constants.USER_VOTE_QUERY.replace("<userId>", String.valueOf(userId)), USER_VOTE_MAPPER).get(0);
	}
	
	
	@Value("${cropx.query.drop_table}")
	private String droptUserVote;
	
	@PreDestroy
	public void destroy() {
		log.debug("destroy UserVoteDao");
		jdbcTemplate.update(droptUserVote.replace("<table>", Constants.USERVOTES_TABLE_NAME));
	}


}
