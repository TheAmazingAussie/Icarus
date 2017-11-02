package org.alexdev.icarus.web.mysql.dao;

import org.alexdev.duckhttpd.util.WebUtilities;
import org.alexdev.icarus.web.game.player.Player;
import org.alexdev.icarus.web.mysql.Storage;
import org.alexdev.icarus.web.util.config.Configuration;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PlayerDao {

    public static boolean exists(String email) {

        boolean success = false;

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            sqlConnection = Storage.get().getConnection();
            preparedStatement = Storage.get().prepare("SELECT * FROM users WHERE email = ? LIMIT 1", sqlConnection);
            preparedStatement.setString(1, email);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                success = true;
            }

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

        return success;
    }

    public static int create(String email, String password) {

        int userId = 0;

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            sqlConnection = Storage.get().getConnection();
            preparedStatement = Storage.get().prepare("INSERT INTO `users` (username, password, email, mission, figure, credits, duckets, last_online, join_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)", sqlConnection);
            preparedStatement.setString(1, "");
            preparedStatement.setString(2, BCrypt.hashpw(password, BCrypt.gensalt()));
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, Configuration.REGISTER_MOTTO);
            preparedStatement.setString(5, Configuration.REGISTER_FIGURE);
            preparedStatement.setInt(6, Configuration.REGISTER_CREDITS);
            preparedStatement.setInt(7, Configuration.REGISTER_DUCKETS);
            preparedStatement.setLong(8, WebUtilities.currentTimeSeconds());
            preparedStatement.setLong(9, WebUtilities.currentTimeSeconds());
            preparedStatement.execute();
            resultSet = preparedStatement.getGeneratedKeys();

            while (resultSet.next()) {
                userId = resultSet.getInt(1);
            }

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

        return userId;
    }

    public static int valid(String email, String password) {

        int userId = 0;

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            sqlConnection = Storage.get().getConnection();
            preparedStatement = Storage.get().prepare("SELECT id, password FROM users WHERE email = ? LIMIT 1", sqlConnection);
            preparedStatement.setString(1, email);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                if (BCrypt.checkpw(password, resultSet.getString("password"))) {
                    userId = resultSet.getInt("id");
                }
            }

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

        return userId;
    }

    public static Player get(int userId) {

        Player player = null;

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            sqlConnection = Storage.get().getConnection();
            preparedStatement = Storage.get().prepare("SELECT * FROM users WHERE id = ? LIMIT 1", sqlConnection);
            preparedStatement.setInt(1, userId);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                player = new Player(resultSet.getInt("id"), resultSet.getString("username"), resultSet.getString("figure"), resultSet.getInt("credits"), resultSet.getInt("duckets"), resultSet.getString("email"), resultSet.getString("mission"), resultSet.getLong("last_online"));
            }

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

        return player;
    }
}
