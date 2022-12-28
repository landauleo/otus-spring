package ru.otus.spring.domain;

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
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "book")
@NamedEntityGraph(name = "book-author-genre-entity-graph",
        attributeNodes = {@NamedAttributeNode("genre"), @NamedAttributeNode("author")}) //всё делает дочерние сущности EAGER
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

}
