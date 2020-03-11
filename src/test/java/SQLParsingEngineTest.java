import org.apache.shardingsphere.core.database.DatabaseTypes;
import org.apache.shardingsphere.core.parse.core.parser.SQLAST;
import org.apache.shardingsphere.core.parse.core.parser.SQLParserEngine;
import org.apache.shardingsphere.core.parse.core.rule.registry.ParseRuleRegistry;

public class SQLParsingEngineTest {
	public static void main(String[] args) {
		String testSQL = "SELECT sou.channel_code channelCode, count(res.id) reservationAmount, sum(IF(res.is_contact=0,1,0)) vaildReservationAmount, pos.school_name schoolName, pos.id schoolId FROM plat_reservation res INNER JOIN plat_source_channel sou ON sou.id=res.source INNER JOIN plat_org_school pos ON pos.id=res.school_id WHERE sou.channel_code is not null AND sou.school_id = ? AND DATE(res.create_time) >= DATE(?) AND DATE(res.create_time) <= DATE(?) AND res.school_id=? AND res.source IS NOT NULL GROUP BY sou.channel_code ";
		System.out.println(testSQL);
		SQLParserEngine statementParser = new SQLParserEngine(ParseRuleRegistry.getInstance(), DatabaseTypes.getTrunkDatabaseType("MySQL"), testSQL);
		SQLAST sqlast = statementParser.parse();
	}
}
