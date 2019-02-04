package catalogApp.shared.model;

import catalogApp.server.dao.constants.Types;

import java.util.Objects;

public class Book extends BaseObject {


    private Author author;

    public Book() {
    }

    public Book(int id, String name) {
        super(id, name, new Type(Types.BOOK, "Book"));
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Book book = (Book) o;
        return Objects.equals(author, book.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), author);
    }
}
