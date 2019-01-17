package catalogApp.shared.model;

public class Author extends BaseObject{

    public Author() {
    }

    public Author(int id, String name, Type type) {
        super(id, name, type);
    }


    @Override
    public String toString() {
        return "Author{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", type=" + getType() +
                '}';
    }
}
