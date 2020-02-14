package com.example.gamestore.GameStoreApp.domain.entities;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "games")
public class Game extends BaseEntity {

    @Column(name = "title", nullable = false, unique = true)
    @Pattern(regexp = "([A-Z][a-z-A-Z0-9]+)", message = "Title doesn't start with capital letter")
    @Size(min = 3, max = 100)
    private String title;

    @Column(name = "trailer", nullable = false)
    @Size(min = 11, max = 11)
    private String trailer;

    @Column(name = "image_thumbnail", unique = true)
    @Pattern(regexp = "(http)?(https)?:\\/\\/.+", message = "Invalid protocol")
    private String imageThumbnail;

    @Column(name = "size", nullable = false, scale = 19, precision = 1)
    @Min(value = 0)
    private double size;

    @Column(name = "price", nullable = false, precision = 19, scale = 2)
    @Min(value = 0)
    private BigDecimal price;

    @Column(name = "description")
    @Size(min = 20, max = 1000)
    private String description;

    @Column(name = "release_date", nullable = false)
    private LocalDate releaseDate;

    @ManyToMany(cascade = CascadeType.ALL,mappedBy = "games")
    private Set<User> users;

    public Game() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    public String getImageThumbnail() {
        return imageThumbnail;
    }

    public void setImageThumbnail(String imageThumbnail) {
        this.imageThumbnail = imageThumbnail;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
