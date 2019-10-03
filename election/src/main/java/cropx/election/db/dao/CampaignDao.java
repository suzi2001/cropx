package cropx.election.db.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import cropx.election.db.entity.Campaign;

@Service
public class CampaignDao {

	private static final RowMapper<Campaign> CAMPAIGN_MAPPER = (rs, rowNum) -> {
		Campaign campaign = new Campaign(rs.getLong("id"));
		return campaign;
	};

	@Value("${cropx.query.select_special_campaigns}")
	private String selectSpecialCampaigns;
	
	
	@Value("${cropx.query.select_all_campaigns}")
	private String selectAllCampaigns;

	@Autowired
	protected JdbcTemplate jdbcTemplate;

	public List<Campaign> getSpecialCampaigns() {
		return jdbcTemplate.query(selectSpecialCampaigns, CAMPAIGN_MAPPER);
	}
	
	public List<Campaign> getAllCampaigns() {
		return jdbcTemplate.query(selectAllCampaigns, CAMPAIGN_MAPPER);
	}
}
