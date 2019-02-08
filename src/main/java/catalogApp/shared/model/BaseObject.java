package catalogApp.shared.model;

import java.util.Objects;

public class BaseObject {

    private int id;
    private String name;
    private Type type;
    private boolean isArchived;
    private String comment;
    private String imagePath;

    public BaseObject(int id, String name, Type type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public BaseObject(int id, String name, Type type, boolean isArchived) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.isArchived = isArchived;
    }

    public BaseObject() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public boolean isArchived() {
        return isArchived;
    }

    public void setArchived(boolean archived) {
        isArchived = archived;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BaseObject that = (BaseObject) o;
        return id == that.id &&
                isArchived == that.isArchived &&
                Objects.equals(name, that.name) &&
                Objects.equals(type, that.type) &&
                Objects.equals(comment, that.comment) &&
                Objects.equals(imagePath, that.imagePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, type, isArchived, comment, imagePath);
    }

    @Override
    public String toString() {
        return "BaseObject{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", isArchived=" + isArchived +
                ", comment='" + comment + '\'' +
                ", imagePath='" + imagePath + '\'' +
                ", archived=" + isArchived() +
                '}';
    }
}
