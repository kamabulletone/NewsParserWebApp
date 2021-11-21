package com.webapp.newsparser.DAO;

import com.webapp.newsparser.Models.NewsRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface NewsRecordRepository extends JpaRepository<NewsRecord, Long>, JpaSpecificationExecutor<NewsRecord> {

    NewsRecord findByContentLink(String contentLink);

}
