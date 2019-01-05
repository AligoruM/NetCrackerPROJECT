package catalogApp.shared.model;

import javax.persistence.*;

@Entity
@Table(name = "Book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idBook")
    private int id;
    @Column(name = "Name")
    private String name;
    @ManyToOne
    @JoinColumn(name = "Types_idTypes", referencedColumnName = "idTypes")
    private Type type;
    @ManyToOne
    @JoinColumn(name = "Author_idAuthor", referencedColumnName = "idAuthor")
    private Author author;

    public Book() {
    }

    public Book(String name, Type type, Author author) {
        this.name = name;
        this.type = type;
        this.author = author;
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

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "Book{" + "id=" + id +
                ", name='" + name + '\'' +
                ", author=" + author +
                '}';
    }
}
