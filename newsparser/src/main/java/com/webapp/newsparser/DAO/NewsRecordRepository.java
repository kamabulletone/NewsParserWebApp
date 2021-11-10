package com.webapp.newsparser.DAO;

import com.webapp.newsparser.Models.NewsRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRecordRepository extends JpaRepository<NewsRecord, Long> {

}
