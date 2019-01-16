package catalogApp.shared.model;

public class Book implements IBaseInterface{
    private int id;
    private String name;
    private Type type;
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

    @Override
    public String getAuthorName() {
        return author.getName();
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
