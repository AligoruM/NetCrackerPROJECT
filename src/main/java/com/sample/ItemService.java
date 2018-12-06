package com.sample;

import com.sample.database.connection.DbUtil;
import com.sample.model.Book;
import com.sample.model.Song;
import com.sample.model.Type;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ItemService {
    public List getAvailableItems(Type type){
        List<String> items = new ArrayList<>();
        String state;
        switch (type) {
            case SONG:
                state = "SELECT * FROM Song";
                break;
            case BOOK:
                state = "SELECT * FROM Book";
                break;
            default:
                items.add("None");
                return items;
        }
        Connection conn = DbUtil.getConnection();
        try (Statement statement = conn.createStatement()) {
            ResultSet resultSet = statement.executeQuery(state);

            switch (type) {
                case BOOK:
                    while (resultSet.next()) {
                        items.add(new Book(resultSet.getInt("idBook"),
                                resultSet.getString("Name"),
                                resultSet.getInt("Types_idTypes"),
                                resultSet.getInt("Author_idAuthor")).toString());
                    }
                    System.out.println(items);
                    return items;
                case SONG:
                    while (resultSet.next()) {
                        items.add(new Song(resultSet.getInt("idItem"),
                                resultSet.getString("Name"),
                                resultSet.getInt("Duration"),
                                resultSet.getString("Comment"),
                                resultSet.getInt("SongGenres_idGenre"),
                                resultSet.getInt("Album_idAlbum"),
                                resultSet.getInt("Types_idTypes")).toString());
                    }
                    System.out.println(items);
                    return items;
            }
            // Возвращаем наш список
            return items;

        } catch (SQLException e) {
            e.printStackTrace();
            // Если произошла ошибка - возвращаем пустую коллекцию
            return Collections.emptyList();
        }
    }
}
