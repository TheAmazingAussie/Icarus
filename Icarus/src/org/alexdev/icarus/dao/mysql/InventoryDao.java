package org.alexdev.icarus.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.log.Log;

import com.google.common.collect.Lists;

public class InventoryDao {
    
	public static List<Item> getInventoryItems(int userId) {

		List<Item> items = Lists.newArrayList();
		
		Connection sqlConnection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			sqlConnection = Dao.getStorage().getConnection();
			
			preparedStatement = Dao.getStorage().prepare("SELECT id, user_id, item_id, room_id, x, y, z, rotation, extra_data FROM items WHERE room_id = 0 AND user_id = ?", sqlConnection);
			preparedStatement.setInt(1, userId);
			
			resultSet = preparedStatement.executeQuery();
	
			while (resultSet.next()) {
				items.add(fill(resultSet));
			}

		} catch (Exception e) {
			Log.exception(e);
		} finally {
			Storage.closeSilently(resultSet);
			Storage.closeSilently(preparedStatement);
			Storage.closeSilently(sqlConnection);
		}
		
		return items;
	}

	public static Item getItem(long id) {

		Connection sqlConnection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			sqlConnection = Dao.getStorage().getConnection();
			
			preparedStatement = Dao.getStorage().prepare("SELECT id, user_id, item_id, room_id, x, y, z, rotation, extra_data FROM items WHERE id = ? LIMIT 1", sqlConnection);
			preparedStatement.setLong(1, id);
			
			resultSet = preparedStatement.executeQuery();
				
			if (!resultSet.next()) {
				return null;
			}

			return fill(resultSet);
			
		} catch (Exception e) {
			Log.exception(e);
		} finally {
			Storage.closeSilently(resultSet);
			Storage.closeSilently(preparedStatement);
			Storage.closeSilently(sqlConnection);
		}

		return null;
	}

	public static Item newItem(int itemId, int ownerId, String extraData) {

		Connection sqlConnection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Item item = null;

		try {

			sqlConnection = Dao.getStorage().getConnection();
			preparedStatement = Dao.getStorage().prepare("INSERT INTO items (owner_id, user_id, item_id, extra_data) VALUES(?, ?, ?, ?)", sqlConnection);
			
			preparedStatement.setInt(1, ownerId);
			preparedStatement.setInt(2, ownerId);
			preparedStatement.setInt(3, itemId);
			preparedStatement.setString(4, extraData);
			preparedStatement.executeUpdate();

			resultSet = preparedStatement.getGeneratedKeys();

			if (resultSet != null && resultSet.next()) {
				item = getItem(resultSet.getLong(1));
			}

		} catch (SQLException e) {
			Log.exception(e);
		} finally {
			Storage.closeSilently(resultSet);
			Storage.closeSilently(preparedStatement);
			Storage.closeSilently(sqlConnection);
		}

		return item;
	}

	public static Item fill(ResultSet row) throws Exception {
		Item instance = new Item(row.getLong("id"), row.getInt("user_id"), row.getInt("item_id"), row.getInt("room_id"), row.getString("x"), row.getString("y"), row.getDouble("z"), row.getInt("rotation"), row.getString("extra_data"));
		return instance;
	}

}
