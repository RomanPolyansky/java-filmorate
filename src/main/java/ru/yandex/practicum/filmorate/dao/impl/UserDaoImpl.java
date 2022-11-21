package ru.yandex.practicum.filmorate.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.UserDao;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.*;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class UserDaoImpl implements UserDao {

    private final Logger log = LoggerFactory.getLogger(UserDaoImpl.class);

    private final JdbcTemplate jdbcTemplate;

    public UserDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<User> getUsers() throws DataAccessException {
        String sql = "select * from users";
        List<User> users = jdbcTemplate.query(sql, (rs, rowNum) -> makeUser(rs));
        if (users.isEmpty()) {
            return Collections.emptyList();
        }
        return users;
    }

    @Override
    public User addUser(User user) throws SQLException {

        /*
                String sql = "INSERT INTO users(name, marital_status) VALUES (?, ?)";

        var decParams = List.of(new SqlParameter(Types.VARCHAR, "name"),
                new SqlParameter(Types.INTEGER, "marital_status"));

        var pscf = new PreparedStatementCreatorFactory(sql, decParams) {
            {
                setReturnGeneratedKeys(true);
                setGeneratedKeysColumnNames("id");
            }
        };

        var psc = pscf.newPreparedStatementCreator(List.of(name, status.ordinal()));

        var keyHolder = new GeneratedKeyHolder();
        jtm.update(psc, keyHolder);

        var uid = Objects.requireNonNull(keyHolder.getKey()).longValue();

        return findById(uid);
         */


        String user_id = jdbcTemplate.update("INSERT INTO users (email, login, name, birthday)" +
                        "VALUES (?, ?, ?, ?)",
                new PreparedStatementCreator() {
                    @Override
                    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                        con.prepareStatement("INSERT INTO users (email, login, name, birthday)" +
                                "VALUES (?, ?, ?, ?)", user.getEmail(), user.getLogin(), user.getName(), user.getBirthday())
                        return null;
                    }

                    public void setValues(PreparedStatement preparedStatement) throws SQLException {
                        preparedStatement.setString(1, user.getEmail());
                        preparedStatement.setString(2, user.getLogin());
                        preparedStatement.setString(3, user.getName());
                        preparedStatement.setDate(4, java.sql.Date.valueOf(user.getBirthday().toString()));
                    }
                });


        String user_id = jdbcTemplate.update("INSERT INTO users (email, login, name, birthday)" +
                        "VALUES (?, ?, ?, ?)",
                new PreparedStatementSetter() {
                    public void setValues(PreparedStatement preparedStatement) throws SQLException {
                        preparedStatement.setString(1, user.getEmail());
                        preparedStatement.setString(2, user.getLogin());
                        preparedStatement.setString(3, user.getName());
                        preparedStatement.setDate(4, java.sql.Date.valueOf(user.getBirthday().toString()));
                    }
                });


                /*,
                new ResultSetExtractor<String>() {
                    public String extractData(ResultSet rs) throws SQLException {
                        return rs.getString("user_id");
                    }
                });

                 */

        //обрабатываем результат выполнения запроса
        log.info("Добавлен пользователь: {}", user_id);

        return makeUser(jdbcTemplate.queryForRowSet("select * from users where user_id = ?", user_id));
    }

    @Override
    public User changeUser(User user) throws SQLException {
        SqlRowSet userRows = jdbcTemplate.queryForRowSet("UPDATE users " +
                "SET email = ?, login = ?, name = ?, birthday = ? " +
                "where user_id = ?", user.getEmail(), user.getLogin(), user.getName(), user.getBirthday(), user.getId());
        return makeUser(userRows);
    }

    @Override
    public Optional<User> getUserById(int id) {
        // выполняем запрос к базе данных.
        SqlRowSet userRows = jdbcTemplate.queryForRowSet("select * from users where user_id = ?", id);

        // обрабатываем результат выполнения запроса
        if (userRows.next()) {
            User user = User.builder()
                    .id(userRows.getInt("user_id"))
                    .email(userRows.getString("email"))
                    .login(userRows.getString("login"))
                    .name(userRows.getString("name"))
                    .birthday(userRows.getDate("birthday").toLocalDate())
                    .build();

            log.info("Найден пользователь: {} {}", user.getId(), user.getLogin());

            return Optional.of(user);
        } else {
            log.info("Пользователь с идентификатором {} не найден.", id);
            return Optional.empty();
        }
    }

    private User makeUser(ResultSet rs) throws SQLException {
        return User.builder()
                .id(rs.getInt("user_id"))
                .email(rs.getString("email"))
                .login(rs.getString("login"))
                .name(rs.getString("name"))
                .birthday(rs.getDate("birthday").toLocalDate())
                .build();
    }

    private User makeUser(SqlRowSet rowSet) throws SQLException {
        return User.builder()
                .id(rowSet.getInt("user_id"))
                .email(rowSet.getString("email"))
                .login(rowSet.getString("login"))
                .name(rowSet.getString("name"))
                .birthday(rowSet.getDate("birthday").toLocalDate())
                .build();
    }
}
