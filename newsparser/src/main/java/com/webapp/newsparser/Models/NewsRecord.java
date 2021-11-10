package com.webapp.newsparser.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "records")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class NewsRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "tag")
    private String tag; // Экономика

    @Column(name = "keyword")
    private String keyWord; // economy

    @Column(name = "title")
    private String title;

    @Column(name = "content_link")
    private String contentLink;

    @Column(name = "created_on", nullable = false)
    private LocalDateTime createdOn;

    @OneToMany(targetEntity = Picture.class)
    private List<Picture> pictures;


}
