package catalogApp.server.dao.constants;

public class SQLQuery {
    //BOOKS
    public static String ALL_BOOKS() {
        return  "select O.idObject as idObject, O.name as objectName, O.isArchived as archived, AV2.idObject as authorId, AV2.name as authorName" +
                " from Object O join AttributeValue AV on (O.idObject = AV.id_object and id_attribute=" + Attribute.BOOK_AUTHOR_ID + ")" +
                " join (select idObject, name from Object where idType=" + Types.AUTHOR + ") AV2 on AV.value=AV2.idObject";
    }

    public static String BOOK_BY_ID(int id) {
        return  "select O.idObject as idObject, O.name as objectName, O.isArchived as archived, AV2.idObject as authorId," +
                " AV2.name as authorName from Object O " +
                "join AttributeValue AV on (O.idObject = AV.id_object and id_attribute=" + Attribute.BOOK_AUTHOR_ID + ")" +
                " join (select idObject, name from Object where idType=" + Types.AUTHOR + ") AV2 on AV.value=AV2.idObject" +
                " where O.idObject=" + id;
    }

    public static String ALL_AUTHORS_NAMES() {
        return "select name from Object O where idType=" + Types.AUTHOR;
    }

    public static String AUTHOR_ID_BY_NAME(String name) {
        return "select idObject from Object where name=\"" + name + "\" and idType=" + Types.AUTHOR;
    }

    public static String BOOKS_BY_IDS(String paramName) {
        return  "select O.idObject as idObject, O.name as objectName, O.isArchived as archived, AV2.idObject as authorId," +
                " AV2.name as authorName from Object O" +
                " join AttributeValue AV on (O.idObject = AV.id_object and id_attribute=" + Attribute.BOOK_AUTHOR_ID + ")" +
                " join (select idObject, name from Object where idType=" + Types.AUTHOR + ") AV2 on AV.value=AV2.idObject" +
                " where O.idObject in (:" + paramName + ")";
    }

    //SONGS
    public static String ALL_SONGS() {
        return  "select O.idObject as idObject, O.name as name, O.isArchived as archived, AV.value as duration, AV3.idObject as idGenre," +
                " AV3.name as genreName from Object O" +
                " join AttributeValue AV on (O.idObject = AV.id_object and AV.id_attribute=" + Attribute.SONG_DURATION + ")" +
                " join AttributeValue AV2 on (O.idObject=AV2.id_object and AV2.id_attribute=" + Attribute.SONG_GENRE_ID + ")" +
                " join (select idObject, name from Object where idType=" + Types.SONG_GENRE + ") AV3 on AV2.value=AV3.idObject";
    }

    public static String SONG_BY_ID(int id) {
        return "select O.idObject as idObject, O.name as name, O.isArchived as archived, AV.value as duration, AV3.idObject as idGenre," +
                " AV3.name as genreName from Object O" +
                "  join AttributeValue AV on (O.idObject = AV.id_object and AV.id_attribute=" + Attribute.SONG_DURATION + ")" +
                "  join AttributeValue AV2 on (O.idObject=AV2.id_object and AV2.id_attribute=" + Attribute.SONG_GENRE_ID + ")" +
                "  join (select idObject, name from Object where idType=" + Types.SONG_GENRE + ") AV3 on AV2.value=AV3.idObject" +
                "  where O.idObject=" + id;
    }

    public static String SONGS_BY_IDS(String paramName) {
        return "select O.idObject as idObject, O.name as name, O.isArchived as archived, AV.value as duration," +
                "  AV3.idObject as idGenre, AV3.name as genreName from Object O" +
                "  join AttributeValue AV on (O.idObject = AV.id_object and AV.id_attribute=" + Attribute.SONG_DURATION + ")" +
                "  join AttributeValue AV2 on (O.idObject=AV2.id_object and AV2.id_attribute=" + Attribute.SONG_GENRE_ID + ")" +
                "  join (select idObject, name from Object where idType=" + Types.SONG_GENRE + ") AV3 on AV2.value=AV3.idObject" +
                "  where O.idObject in (:" + paramName + ")";
    }

    public static String ALL_GENRES_NAMES() {
        return "select name from Object O where idType=" + Types.SONG_GENRE;
    }

    public static String GENRE_ID_BY_NAME(String name) {
        return "select idObject from Object where name=\"" + name + "\" and idType=" + Types.SONG_GENRE;
    }

    //GENERAL
    public static String UPDATE_OBJECT_NAME(int id, String name){
        return "update Object set name=\"" + name + "\" where idObject=" + id;
    }

    public static String UPDATE_OBJECTS_STATE(int state, String paramName){
        return "update Object set IsArchived=\"" + state + "\" where idObject in (:" + paramName + ")";
    }

    public static String CREATE_ATTRIBUTE_VALUE(String value, int objectId, int attrId) {
        return "insert into AttributeValue (value, id_object, id_attribute) values (\"" + value + "\", " + objectId + ", " + attrId + ")";
    }

    public static String UPDATE_ATTRIBUTE_VALUE(String value, int objectId, int attrId){
        return "update AttributeValue set value=\"" + value + "\" where id_object=" + objectId + " and id_attribute=" + attrId;
    }

    public static String DELETE_ATTRIBUTE_VALUE_BY_ALL_FIELDS(String value, int objectId, int attId){
        return "delete from AttributeValue where id_object=" + objectId + " and id_attribute=" + attId + " and value=\"" + value + "\"";
    }

    public static String DELETE_ATTRIBUTE_VALUE_BY_KEYS(int objectId, int attId){
        return "delete from AttributeValue where id_object=" + objectId + " and id_attribute=" + attId;
    }

    public static String ATTRIBUTE_VALUE_BY_ID_AND_ATTRIBUTES(int id, int idAttribute) {
        return "select AV.value as value from AttributeValue AV" +
                " where AV.id_object=" + id + " and AV.id_attribute=" + idAttribute;
    }

    //USERS
    public static String USER_BY_NAME(String name) {
        return "select O.idObject as idObject, O.name as objectName, O.isArchived as archived, AV.value as password" +
                " from Object O" +
                " join AttributeValue AV on O.idObject = AV.id_object and Av.id_attribute=" + Attribute.USER_PASSWORD +
                " where objectName = \"" + name + "\"";
    }

    public static String ALL_USERS() {
        return "select O.idObject as idObject, O.name as objectName, AV2.value as avatar, AV.value as description from Object O" +
                " left join AttributeValue AV on O.idObject = AV.id_object and Av.id_attribute=" + Attribute.USER_DESCRIPTION +
                " left join AttributeValue AV2 on O.idObject = AV2.id_object and AV2.id_attribute=" + Attribute.USER_AVATAR_URL +
                " where O.idType=" + Types.USER;
    }

    public static String USER_ID_BY_NAME(String name) {
        return "select O.idObject as id from Object O where O.name=\"" + name + "\" and O.idType=" + Types.USER;
    }

    public static String USER_ROLES_BY_ID(int id) {
        return "select AV.value as role from AttributeValue AV where AV.id_object=" + id + " and AV.id_attribute=" + Attribute.USER_ROLE;
    }
}
