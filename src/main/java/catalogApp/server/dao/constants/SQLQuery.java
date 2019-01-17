package catalogApp.server.dao.constants;

public class SQLQuery {
    public static String ALL_BOOKS() {
        return "select O.idObject as idObject, O.name as objectName, AV2.idObject as authorId, AV2.name as authorName " +
                "from Object O join AttributeValue AV on (O.idObject = AV.id_object and id_attribute=" + Attribure.BOOK_AUTHOR_ID + ")" +
                "join (select idObject, name from Object where idType=" + Types.AUTHOR + ") AV2 on AV.value=AV2.idObject";
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

    public static String ALL_SONGS(){
        return "select O.idObject as idObject, O.name as name, AV.value as duration, AV3.idObject as idGenre, AV3.name as genreName" +
                "  from Object O  join AttributeValue AV on (O.idObject = AV.id_object and AV.id_attribute=" + Attribure.SONG_DURATION + ")" +
                "  join AttributeValue AV2 on (O.idObject=AV2.id_object and AV2.id_attribute=" + Attribure.SONG_GENRE_ID + ")" +
                "  join (select idObject, name from Object where idType=" + Types.GENRE +") AV3 on AV2.value=AV3.idObject";
    }
}
