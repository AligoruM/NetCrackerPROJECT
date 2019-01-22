package catalogApp.server.dao.constants;

import org.w3c.dom.Attr;

import java.sql.PreparedStatement;

public class SQLQuery {
    public static String ALL_BOOKS() {
        return "select O.idObject as idObject, O.name as objectName, AV2.idObject as authorId, AV2.name as authorName " +
                "from Object O join AttributeValue AV on (O.idObject = AV.id_object and id_attribute=" + Attribure.BOOK_AUTHOR_ID + ")" +
                "join (select idObject, name from Object where idType=" + Types.AUTHOR + ") AV2 on AV.value=AV2.idObject";
    }

    public static String BOOK_BY_ID(int id) {
        return "select O.idObject as idObject, O.name as objectName, AV2.idObject as authorId, AV2.name as authorName " +
                "from Object O join AttributeValue AV on (O.idObject = AV.id_object and id_attribute=" + Attribure.BOOK_AUTHOR_ID + ")" +
                "join (select idObject, name from Object where idType=" + Types.AUTHOR + ") AV2 on AV.value=AV2.idObject" +
                " where O.idObject=" + id;
    }

    public static String ALL_AUTHORS_NAMES() {
        return "select name from Object O where idType=" + Types.AUTHOR;
    }

    public static String AUTHOR_ID_BY_NAME(String name) {
        return "select idObject from Object where name=\"" + name + "\" and idType=" + Types.AUTHOR;
    }

    public static String CREATE_OBJECT() {
        return "insert into Object (name, idType) values (?,?)";
    }

    public static String CREATE_ATTRIBUTE_VALUE(String value, int objectId, int attrId) {
        return "insert into AttributeValue (value, id_object, id_attribute) values (\"" + value + "\", " + objectId + ", " + attrId + ")";
    }

    public static String ALL_SONGS() {
        return "select O.idObject as idObject, O.name as name, AV.value as duration, AV3.idObject as idGenre, AV3.name as genreName" +
                "  from Object O  join AttributeValue AV on (O.idObject = AV.id_object and AV.id_attribute=" + Attribure.SONG_DURATION + ")" +
                "  join AttributeValue AV2 on (O.idObject=AV2.id_object and AV2.id_attribute=" + Attribure.SONG_GENRE_ID + ")" +
                "  join (select idObject, name from Object where idType=" + Types.SONG_GENRE + ") AV3 on AV2.value=AV3.idObject";
    }

    public static String USER_BY_NAME(String name) {
        return "select O.idObject as idObject, O.name as objectName, AV.value as password, AV2.value as role, AV3.value as active from Object O" +
                " join AttributeValue AV on O.idObject = AV.id_object and Av.id_attribute=" + Attribure.USER_PASSWORD +
                " join AttributeValue AV2 on O.idObject = AV2.id_object and AV2.id_attribute=" + Attribure.USER_ROLE +
                " join AttributeValue AV3 on O.idObject = AV3.id_object and AV3.id_attribute=" + Attribure.USER_ACTIVE +
                " where objectName = \"" + name + "\"";
    }

    public static String USER_ID_BY_NAME(String name){
        return "select O.idObject as id from Object O where O.name=\"" + name + "\" and O.idType=" + Types.USER;
    }

    public static String USER_ROLE_BY_ID(int id){
        return "select AV.value as role from AttributeValue AV where AV.id_object=" + id + " and AV.id_attribute=" + Attribure.USER_ROLE;
    }

    public static String LIKED_BOOK_BY_USER_ID(int id){
        return "select AV.value as id from AttributeValue AV where AV.id_object=" + id + "and AV.id_attribute=" + Attribure.LIKED_BOOK_ID;
    }
}
