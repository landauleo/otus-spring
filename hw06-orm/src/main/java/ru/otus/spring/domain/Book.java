package ru.otus.spring.domain;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Getter
@Setter
@Entity
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "book")
@NamedEntityGraph(name = "book-author-genre-entity-graph",
        attributeNodes = {@NamedAttributeNode("genre"), @NamedAttributeNode("author"), @NamedAttributeNode("comments")})
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(targetEntity = Genre.class, fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "genre_id")
    @Fetch(FetchMode.JOIN)
    private Genre genre;

    @ManyToOne(targetEntity = Author.class, fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "author_id")
    @Fetch(FetchMode.JOIN)
    private Author author;

    //внимательней с CascadeType!!
    //обрати внимание, что нет mappedBy
    //атрибут необходим для создания двунаправленной связи, чтобы указать “владельца” отношения → без этой аннотации Hibernate считает, что у него просто 2 однонаправленные связи(!!!), а не 1 двунаправленная
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private List<Comment> comments;

    public Book(String name, Genre genre, Author author, List<Comment> comments) {
        this.name = name;
        this.genre = genre;
        this.author = author;
        this.comments = comments;
    }

    public Book(long id, String name, Genre genre, Author author) {
        this.id = id;
        this.name = name;
        this.genre = genre;
        this.author = author;
    }

    public Book(String name, Genre genre, Author author) {
        this.name = name;
        this.genre = genre;
        this.author = author;
    }

    @Override
    public String toString() {
        StringBuilder commentsStr = new StringBuilder("");

        if (comments != null) {
            for (Comment comment : comments) {
                String commentText = comment.getText();
                commentsStr.append(commentText).append(", ");
            }
        }
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", genre=" + genre +
                ", author=" + author +
                ", comments=" + commentsStr +
                '}';
    }

}
