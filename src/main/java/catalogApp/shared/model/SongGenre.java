package catalogApp.shared.model;

public class SongGenre extends BaseObject {

    public SongGenre() {
    }

    public SongGenre(int id, String name, Type type) {
        super(id, name, type);
    }

    @Override
    public String toString() {
        return "SongGenre{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", type=" + getType() +
                '}';
    }
}
