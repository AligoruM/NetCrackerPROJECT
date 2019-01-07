package catalogApp.shared.model;

import javax.persistence.*;

@Entity
@Table(name = "SongGenre")
public class SongGenre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idGenre")
    private int id;
    @Column(name = "Name")
    private String name;

    public SongGenre() {
    }

    public SongGenre(String name) {
        this.name = name;
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

    @Override
    public String toString() {
        return "SongGenre{" + "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
