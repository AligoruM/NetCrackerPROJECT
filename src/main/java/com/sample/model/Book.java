package com.sample.model;

import java.util.Objects;

public class Book {
    private int idBook;
    private String name;
    private int typeId;
    private int authorId;

    public Book(int idBook, String name, int typeId, int authorId) {
        this.idBook = idBook;
        this.name = name;
        this.typeId = typeId;
        this.authorId = authorId;
    }

    public int getIdBook() {
        return idBook;
    }

    public void setIdBook(int idBook) {
        this.idBook = idBook;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return idBook == book.idBook &&
                typeId == book.typeId &&
                authorId == book.authorId &&
                Objects.equals(name, book.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idBook, name, typeId, authorId);
    }

    @Override
    public String toString() {
        return "Book{" +
                "idBook=" + idBook +
                ", name='" + name + '\'' +
                ", typeId=" + typeId +
                ", authorId=" + authorId +
                '}';
    }
}
