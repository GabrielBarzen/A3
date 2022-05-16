package Shared;

public class Book {

    private long id;
    private String name;
    private String author;
    private long year;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }


    public long getYear() {
        return year;
    }

    public void setYear(long year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "Book:{" +
                "id:" + id +
                ", name:'" + name + '\'' +
                ", author:'" + author + '\'' +
                ", year:" + year +
                '}';
    }
}