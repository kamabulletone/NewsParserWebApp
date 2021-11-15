package com.webapp.newsparser.DAO;

import com.webapp.newsparser.Models.NewsRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NewsRecordRepository extends JpaRepository<NewsRecord, Long> {
    List<NewsRecord> findTop4ByOrderByIdAsc();
    List<NewsRecord> findTop4ByOrderByCreatedOnDesc();
    NewsRecord findByContentLink(String contentLink);

}
