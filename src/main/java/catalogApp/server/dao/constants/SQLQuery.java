package catalogApp.server.dao.constants;

import static catalogApp.server.dao.constants.Tables.*;

public class SQLQuery {
    //BOOKS
    public static String ALL_BOOKS() {
        return "select O." + ID_OBJ + " as " + ID_OBJ_ALIAS + ", O." + NAME_OBJ + " " + NAME_OBJ_ALIAS + ", " +
                " O." + ARCHIVED_OBJ + " as " + ARCHIVED_OBJ_ALIAS + ", O." + IMG_OBJ + " as " + IMG_OBJ_ALIAS + ", " +
                " O." + COMMENT_OBJ + " as " + COMMENT_OBJ_ALIAS + ", AV2." + ID_OBJ + " as " + ID_AUTHOR_ALIAS + ", " +
                " AV2." + NAME_OBJ + " as " + NAME_AUTHOR_ALIAS +
                " from " + OBJECT_TABLE + " O " +
                " join " + ATTR_VAL_TABLE + " AV on (O." + ID_OBJ + " = AV." + ID_OBJ_AV +
                " and " + ID_ATTR_AV + "=" + Attribute.BOOK_AUTHOR_ID + ")" +
                " join (select " + ID_OBJ + ", " + NAME_OBJ + " from " + OBJECT_TABLE + " where " + ID_TYPE_OBJ + "=" + Types.AUTHOR + ")" +
                " AV2 on AV." + VALUE_AV + "=AV2." + ID_OBJ;
    }

    public static String BOOK_BY_ID(int id) {
        return ALL_BOOKS() + " where O." + ID_OBJ + "=" + id;
    }

    public static String ALL_AUTHORS_NAMES() {
        return "select " + NAME_OBJ + " from " + OBJECT_TABLE + " O where " + ID_TYPE_OBJ + "=" + Types.AUTHOR;
    }

    public static String AUTHOR_ID_BY_NAME(String name) {
        return "select " + ID_OBJ + " from " + OBJECT_TABLE + " where " + NAME_OBJ + "=\"" + name + "\" and " + ID_TYPE_OBJ + "=" + Types.AUTHOR;
    }

    public static String BOOKS_BY_IDS(String paramName) {
        return ALL_BOOKS() + " where O." + ID_OBJ + " in (:" + paramName + ")";
    }

    //SONGS
    public static String ALL_SONGS() {
        return "select O." + ID_OBJ + " as " + ID_OBJ_ALIAS + ", O." + NAME_OBJ + " " + NAME_OBJ_ALIAS + ", " +
                " O." + ARCHIVED_OBJ + " as " + ARCHIVED_OBJ_ALIAS + ", O." + IMG_OBJ + " as " + IMG_OBJ_ALIAS + ", " +
                " O." + COMMENT_OBJ + " as " + COMMENT_OBJ_ALIAS + ", AV." + VALUE_AV + " as " + DURATION_ALIAS + ", " +
                " AV3." + ID_OBJ + " as " + ID_GENRE_ALIAS + ", AV3." + NAME_OBJ + " as " + NAME_GENRE_ALIAS + " " +
                " from " + OBJECT_TABLE + " O" +
                " join " + ATTR_VAL_TABLE + " AV on (O." + ID_OBJ + " = AV." + ID_OBJ_AV + " and AV." + ID_ATTR_AV + "=" + Attribute.SONG_DURATION + ")" +
                " join " + ATTR_VAL_TABLE + " AV2 on (O." + ID_OBJ + "= AV2." + ID_OBJ_AV + " and AV2." + ID_ATTR_AV + "=" + Attribute.SONG_GENRE_ID + ")" +
                " join (select " + ID_OBJ + ", " + NAME_OBJ + " from " + OBJECT_TABLE + " where " + ID_TYPE_OBJ + "=" + Types.SONG_GENRE + ") AV3 on AV2." + VALUE_AV + "=AV3." + ID_OBJ;
    }

    public static String SONG_BY_ID(int id) {
        return ALL_SONGS() + "  where O." + ID_OBJ + "=" + id;
    }

    public static String SONGS_BY_IDS(String paramName) {
        return ALL_SONGS() + "  where O." + ID_OBJ + " in (:" + paramName + ")";
    }

    public static String ALL_GENRES_NAMES() {
        return "select " + NAME_OBJ + " from " + OBJECT_TABLE + " O where " + ID_TYPE_OBJ + "=" + Types.SONG_GENRE;
    }

    public static String GENRE_ID_BY_NAME(String name) {
        return "select " + ID_OBJ + " from " + OBJECT_TABLE + " where " + NAME_OBJ + "=\"" + name + "\" and " + ID_TYPE_OBJ + "=" + Types.SONG_GENRE;
    }

    //GENERAL
    public static String UPDATE_OBJECT_NAME(int id, String name) {
        return "update  " + OBJECT_TABLE + " set " + NAME_OBJ + "=\"" + name + "\" where " + ID_OBJ + "=" + id;
    }

    public static String CHECK_FOR_EXIST_BY_NAME_AND_TYPE(String name, int type){
        return "select COUNT(1) as " + COUNT_ALIAS + " from "+ OBJECT_TABLE + " where " + NAME_OBJ + "=\"" + name + "\" and " + ID_TYPE_OBJ + "=" + type;
    }

    public static String UPDATE_OBJECTS_STATE(int state, String paramName) {
        return "update " + OBJECT_TABLE + " set " + ARCHIVED_OBJ + "=\"" + state + "\" where " + ID_OBJ + " in (:" + paramName + ")";
    }

    public static String UPDATE_OBJECT_IMAGE(String imgPath, int id) {
        return "update " + OBJECT_TABLE + " set " + IMG_OBJ + "=\"" + imgPath + "\" where " + ID_OBJ + "=" + id;
    }

    public static String UPDATE_OBJECT_COMMENT(String comment, int id) {
        return "update " + OBJECT_TABLE + " set " + COMMENT_OBJ_ALIAS + "=\"" + comment + "\" where " + ID_OBJ + "=" + id;
    }

    public static String CREATE_ATTRIBUTE_VALUE(String value, int objectId, int attrId) {
        return "insert into " + ATTR_VAL_TABLE + " (" + VALUE_AV + ", " + ID_OBJ_AV + ", " + ID_ATTR_AV + ") values (\"" + value + "\", " + objectId + ", " + attrId + ")";
    }

    public static String UPDATE_ATTRIBUTE_VALUE(String value, int objectId, int attrId) {
        return "update " + ATTR_VAL_TABLE + " set " + VALUE_AV + "=\"" + value + "\" where " + ID_OBJ_AV + "=" + objectId + " and " + ID_ATTR_AV + "=" + attrId;
    }

    public static String DELETE_ATTRIBUTE_VALUE_BY_ALL_FIELDS(String value, int objectId, int attId) {
        return "delete from " + ATTR_VAL_TABLE + " where " + ID_OBJ_AV + "=" + objectId + " and " + ID_ATTR_AV + "=" + attId + " and " + VALUE_AV + "=\"" + value + "\"";
    }

    public static String DELETE_ATTRIBUTE_VALUE_BY_KEYS(int objectId, int attId) {
        return "delete from " + ATTR_VAL_TABLE + " where " + ID_OBJ_AV + "=" + objectId + " and " + ID_ATTR_AV + "=" + attId;
    }

    public static String ATTRIBUTE_VALUE_BY_ID_AND_ATTRIBUTES(int id, int idAttribute) {
        return "select AV." + VALUE_AV + " as " + VALUE_AV + " from " + ATTR_VAL_TABLE + " AV" +
                " where AV." + ID_OBJ_AV + "=" + id + " and AV." + ID_ATTR_AV + "=" + idAttribute;
    }

    //USERS
    public static String USER_BY_NAME(String name) {
        return "select O." + ID_OBJ + " as " + ID_OBJ_ALIAS + ", O." + NAME_OBJ + " as " + NAME_OBJ_ALIAS + ", " +
                " O." + ARCHIVED_OBJ + " as " + ARCHIVED_OBJ_ALIAS + ", AV." + VALUE_AV + " as " + PASSWORD_ALIAS + "" +
                " from " + OBJECT_TABLE + " O" +
                " join " + ATTR_VAL_TABLE + " AV on O." + ID_OBJ + " = AV." + ID_OBJ_AV + " and AV." + ID_ATTR_AV + "=" + Attribute.USER_PASSWORD +
                " where " + NAME_OBJ + " = \"" + name + "\"";
    }

    public static String ALL_SIMPLE_USERS() {
        return "select O." + ID_OBJ + " as " + ID_OBJ_ALIAS + ", O." + NAME_OBJ + " " + NAME_OBJ_ALIAS + ", O." + ARCHIVED_OBJ + " as " + ARCHIVED_OBJ_ALIAS + ", " +
                " O." + IMG_OBJ + " as " + IMG_OBJ_ALIAS + ", O." + COMMENT_OBJ + " as " + COMMENT_OBJ_ALIAS + ", AV." + VALUE_AV + " as " + DESCRIPTION_ALIAS + " " +
                "from " + OBJECT_TABLE + " O" +
                " left join " + ATTR_VAL_TABLE + " AV on O." + ID_OBJ + " = AV." + ID_OBJ_AV + " and AV." + ID_ATTR_AV + "=" + Attribute.USER_DESCRIPTION +
                " where O." + ID_TYPE_OBJ + "=" + Types.USER;
    }

    public static String GET_SIMPLE_USER(int id) {
        return ALL_SIMPLE_USERS() + " and O." + ID_OBJ + "=" + id;
    }

    public static String USER_ID_BY_NAME(String name) {
        return "select O." + ID_OBJ + " as id from " + OBJECT_TABLE + " O where O." + NAME_OBJ + "=\"" + name + "\" and O." + ID_TYPE_OBJ + "=" + Types.USER;
    }

    public static String USER_ROLES_BY_ID(int id) {
        return "select AV." + VALUE_AV + " as " + ROLE_ALIAS + " from " + ATTR_VAL_TABLE + " AV where AV." + ID_OBJ_AV + "=" + id + " and AV." + ID_ATTR_AV + "=" + Attribute.USER_ROLE;
    }
}
