package catalogApp.shared.model;

public class Book extends BaseObject {


    private Author author;

    public Book() {
    }

    public Book(int id, String name, Type type) {
        super(id, name, type);
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "Book{" + "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", author=" + author +
                '}';
    }
}
