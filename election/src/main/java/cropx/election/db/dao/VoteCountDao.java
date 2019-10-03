package cropx.election.db.dao;

import java.util.List;

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
import cropx.election.db.entity.VoteCount;
import cropx.util.Constants;

@Service
public class VoteCountDao {
	

	private static final Logger log = LogManager.getLogger();
	
	@Autowired
	protected JdbcTemplate jdbcTemplate;
	
	@Value("${cropx.query.insert_table}")
	private String insertTable;
	
	private String insertStr;
	
	@PostConstruct
	public void init() {
		log.debug("Init VoteCountDao");
		insertStr = insertTable.replace("<table>", Constants.VOTECOUNT_TABLE_NAME).replace("<col1>", Constants.USERID_COLUMN_NAME).replace("<col2>", Constants.VOTESCOUNT_COLUMN_NAME);
		String currentInsertStr= insertStr.replace("<val2>", "0");
		for (int i =1; i<=Constants.NUM_OF_USERS;i++) {
			jdbcTemplate.update(currentInsertStr.replace("<val1>", String.valueOf(i)));
		}
	}
	
	private static final RowMapper<Integer> COUNT_MAPPER = (rs, rowNum) -> {
		Integer result = rs.getInt(Constants.VOTESCOUNT_COLUMN_NAME);
		return result;
	};
	
	public int getVoteCountForUser(int userId) {
		return jdbcTemplate.query(Constants.VOTE_COUNT_QUERY.replace("<userId>", String.valueOf(userId)), COUNT_MAPPER).get(0);
	}
	

	
	@Value("${cropx.query.update_table}")
	private String updateTable;

	
	public void insertVoteCount(VoteCount voteCount) {
		log.debug("Updating user "+voteCount.getUserId()+" vote count:"+voteCount.getVotesCount());
		String updateQuery= updateTable.replace("<table>", Constants.VOTECOUNT_TABLE_NAME).replace("<col1>", Constants.VOTESCOUNT_COLUMN_NAME).replace("<val1>", String.valueOf(voteCount.getVotesCount()));
		String whereClause = Constants.USERID_COLUMN_NAME +"='"+String.valueOf(voteCount.getUserId())+"'";
		updateQuery = updateQuery.replace("<condition>", whereClause);
		jdbcTemplate.update(updateQuery);
		
	}
	
	
	private static final RowMapper<VoteCount> TOP10_VOTE_MAPPER = (rs, rowNum) -> {
		VoteCount voteCount = new VoteCount(rs.getInt(Constants.USERID_COLUMN_NAME), rs.getInt(Constants.VOTESCOUNT_COLUMN_NAME));
		return voteCount;
	};
	
	public List<VoteCount> getTop10Candidates() {
		return jdbcTemplate.query(Constants.TOP10_CANDIDATES_QUERY, TOP10_VOTE_MAPPER);
	}
	
	@Value("${cropx.query.drop_table}")
	private String dropVoteCount;
	
	@PreDestroy
	public void destroy() {
		log.debug("destroy VoteCountDao");
		jdbcTemplate.update(dropVoteCount.replace("<table>", Constants.VOTECOUNT_TABLE_NAME));
	}



	


}
