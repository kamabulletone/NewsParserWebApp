package com.webapp.newsparser.DAO;

import com.webapp.newsparser.Models.Picture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PictureRepository extends JpaRepository<Picture, Long> {

}
