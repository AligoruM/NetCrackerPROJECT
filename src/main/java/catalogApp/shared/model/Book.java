package catalogApp.shared.model;

import catalogApp.server.dao.constants.Types;
import com.google.gwt.core.client.GWT;

import java.util.Objects;

public class Book extends BaseObject implements Ratable {


    private Author author;
    private float rating;
    private boolean isMarked;


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
    public float getRating() {
        return rating;
    }

    @Override
    public void setRating(float rating) {
        this.rating = rating;
    }

    @Override
    public boolean isMarked() {
        return isMarked;
    }

    @Override
    public void setMarked(boolean marked) {
        isMarked = marked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        Book book = (Book) o;
        return Objects.equals(author, book.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), author);
    }

    @Override
    public String toString() {
        return "Book{" +
                "author=" + author +
                ", rating=" + rating +
                ", isMarked=" + isMarked +
                ", id=" + getId() +
                ", name='" + getName() + '\'' +
                ", type=" + getType() +
                '}';
    }
}
