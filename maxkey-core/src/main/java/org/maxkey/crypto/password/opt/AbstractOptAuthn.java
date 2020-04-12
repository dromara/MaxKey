package org.maxkey.crypto.password.opt;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.format.DateTimeFormat;
import org.maxkey.constants.STATUS;
import org.maxkey.domain.UserInfo;
import org.maxkey.util.StringGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

/**
 * AbstractOTPAuthn.
 * @author Administrator
 *
 */
public abstract class AbstractOptAuthn {
    private static final  Logger logger = LoggerFactory.getLogger(AbstractOptAuthn.class);

    protected int interval = 30;

    protected int digits = 6;

    protected String crypto = "HmacSHA1";

    StringGenerator stringGenerator;

    private final JdbcTemplate jdbcTemplate;

    public static final class OptTypes {
        // 手机
        public static int MOBILE = 2;
        // 短信
        public static int SMS = 3;
        // 邮箱
        public static int EMAIL = 4;

        public static int TIMEBASED_OPT = 5;

        public static int COUNTERBASED_OPT = 6;

        public static int HOTP_OPT = 7;

        public static int RSA_OPT = 8;

    }

    private static final String DEFAULT_DEFAULT_INSERT_STATEMENT = 
            "INSERT INTO ONE_TIME_PASSWORD(ID ,OPTTYPE,USERNAME,TOKEN,RECEIVER,CREATETIME,STATUS)" 
                    + " VALUES(?,?,?,?,?,?," + STATUS.ACTIVE + ")";

    private static final String DEFAULT_DEFAULT_SELECT_STATEMENT = 
            "SELECT ID ,OPTTYPE,USERNAME,TOKEN,RECEIVER,CREATETIME FROM ONE_TIME_PASSWORD"
            +   " WHERE STATUS =" + STATUS.ACTIVE 
            +   " AND  USERNAME = ? AND TOKEN = ? AND OPTTYPE = ?";

    private static final String DEFAULT_DEFAULT_DELETE_STATEMENT = 
            "UPDATE ONE_TIME_PASSWORD SET  STATUS ="
            + STATUS.DELETE + " WHERE USERNAME = ? AND TOKEN = ? AND OPTTYPE = ?";

    public abstract boolean produce(UserInfo userInfo);

    public abstract boolean validate(UserInfo userInfo, String token);

    protected String defaultProduce(UserInfo userInfo) {
        return genToken(userInfo);
    }

    /**
     * genToken.
     * @param userInfo UserInfo
     * @return
     */
    public String genToken(UserInfo userInfo) {
        if (stringGenerator == null) {
            stringGenerator = new StringGenerator(StringGenerator.DEFAULT_CODE_NUMBER, digits);
        }
        return stringGenerator.randomGenerate();
    }

    public AbstractOptAuthn(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    protected void insertDataBase(UserInfo userInfo, String token, String receiver, int type) {
        jdbcTemplate.update(DEFAULT_DEFAULT_INSERT_STATEMENT,
                new Object[] { 
                        java.util.UUID.randomUUID(), 
                        type, 
                        userInfo.getUsername(),
                        token, 
                        receiver, 
                        new Date() 
                },
                new int[] { Types.VARCHAR, Types.INTEGER, 
                        Types.VARCHAR, Types.VARCHAR, 
                        Types.VARCHAR,Types.TIMESTAMP 
                }
        );
    }

    /**
     * validateDataBase.
     * @param userInfo UserInfo
     * @param token String
     * @param type int
     * @return
     */
    public boolean validateDataBase(UserInfo userInfo, String token, int type) {
        OneTimePassword oneTimePassword = jdbcTemplate.queryForObject(
                DEFAULT_DEFAULT_SELECT_STATEMENT,
                new OneTimePasswordRowMapper(), userInfo.getUsername(), token, type);

        if (oneTimePassword != null) {

            jdbcTemplate.update(
                    DEFAULT_DEFAULT_DELETE_STATEMENT, 
                    new Object[] { userInfo.getUsername(), token, type },
                    new int[] { Types.VARCHAR, Types.VARCHAR, Types.INTEGER }
            );
            DateTime currentdateTime = new DateTime();
            DateTime oneTimePwdData = DateTime.parse(oneTimePassword.getCreateTime(),
                    DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss"));
            Duration duration = new Duration(oneTimePwdData, currentdateTime);
            int intDuration = Integer.parseInt(duration.getStandardSeconds() + "");
            logger.debug("validate duration " + intDuration);
            logger.debug("validate result " + (intDuration <= interval));
            if (intDuration <= interval) {
                return true;
            }
        }
        return false;

    }

    /**
     *  the interval.
     * @return the interval
     */
    public int getInterval() {
        return interval;
    }

    /**
     * interval the interval to set.
     * @param interval the interval to set
     */
    public void setInterval(int interval) {
        this.interval = interval;
    }

    /**
     * digits.
     * @return the digits
     */
    public int getDigits() {
        return digits;
    }

    /**
     * digits the digits to set.
     * @param digits the digits to set
     */
    public void setDigits(int digits) {
        this.digits = digits;
    }

    /**
     * crypto.
     * @return the crypto
     */
    public String getCrypto() {
        return crypto;
    }

    /**
     * crypto the crypto to set.
     * @param crypto the crypto to set
     */
    public void setCrypto(String crypto) {
        this.crypto = crypto;
    }

    public class OneTimePasswordRowMapper implements RowMapper<OneTimePassword> {

        /**
         *ResultSet.
         */
        public OneTimePassword mapRow(ResultSet rs, int rowNum) throws SQLException {
            OneTimePassword oneTimePassword = new OneTimePassword();
            oneTimePassword.setId(rs.getString("ID"));
            oneTimePassword.setType(rs.getInt("OPTTYPE"));
            oneTimePassword.setUsername(rs.getString("USERNAME"));
            oneTimePassword.setToken(rs.getString("TOKEN"));
            oneTimePassword.setUsername(rs.getString("USERNAME"));
            oneTimePassword.setReceiver(rs.getString("RECEIVER"));
            oneTimePassword.setCreateTime(rs.getString("CREATETIME"));
            return oneTimePassword;
        }
    }

}
