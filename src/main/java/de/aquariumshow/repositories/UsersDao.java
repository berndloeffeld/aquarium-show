package de.aquariumshow.repositories;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import de.aquariumshow.model.ASUser;
import de.aquariumshow.model.UserConnection;

import javax.sql.DataSource;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by magnus on 18/08/14.
 */
@Repository
public class UsersDao {

    private static final Logger LOG = LoggerFactory.getLogger(UsersDao.class);

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public UsersDao(DataSource dataSource)
    {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public ASUser getUserProfile(final String email) {
        LOG.debug("SQL SELECT ON UserProfile: {}", email);

        return jdbcTemplate.queryForObject("select * from users where email = ?",
            new RowMapper<ASUser>() {
                public ASUser mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new ASUser(
                    rs.getLong("id"),
                    rs.getString("email"),
                    rs.getString("username"));
                }
            }, email);
    }

    public UserConnection getUserConnection(final String userId) {
        LOG.debug("SQL SELECT ON UserConnection: {}", userId);

        return jdbcTemplate.queryForObject("select * from UserConnection where userId = ?",
            new RowMapper<UserConnection>() {
                public UserConnection mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new UserConnection(
                    userId,
                    rs.getString("providerId"),
                    rs.getString("providerUserId"),
                    rs.getInt("rank"),
                    rs.getString("displayName"),
                    rs.getString("profileUrl"),
                    rs.getString("imageUrl"),
                    rs.getString("accessToken"),
                    rs.getString("secret"),
                    rs.getString("refreshToken"),
                    rs.getLong("expireTime"));
                }
            }, userId);
    }

    public void createUser(String userId, ASUser profile) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("SQL INSERT ON users, authorities and userProfile: " + userId + " with profile: " +
                profile.getEmail() + ", " +
                profile.getUsername());
        }

//        jdbcTemplate.update("INSERT into users(username,password,enabled) values(?,?,true)",userId, RandomStringUtils.randomAlphanumeric(8));
//        jdbcTemplate.update("INSERT into authorities(username,authority) values(?,?)",userId,"USER");
//        jdbcTemplate.update("INSERT into userProfile(userId, email, firstName, lastName, name, username) values(?,?,?,?,?,?)",
//            userId,
//            profile.getEmail(),
//            profile.getFirstName(),
//            profile.getLastName(),
//            profile.getName(),
//            profile.getUsername());
    }
}
