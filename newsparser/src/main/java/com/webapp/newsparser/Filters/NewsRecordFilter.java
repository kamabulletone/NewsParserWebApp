package com.webapp.newsparser.Filters;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;

public class NewsRecordFilter {
    @Nullable
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createdFrom;

    @Nullable
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createdTo;

    @Nullable
    private String tag;

    @Nullable
    public LocalDateTime getCreatedFrom() {
        return createdFrom;
    }

    public void setCreatedFrom(@Nullable LocalDateTime createdFrom) {
        this.createdFrom = createdFrom;
    }

    @Nullable
    public LocalDateTime getCreatedTo() {
        return createdTo;
    }

    public void setCreatedTo(@Nullable LocalDateTime createdTo) {
        this.createdTo = createdTo;
    }

    @Nullable
    public String getTag() {
        return tag;
    }

    public void setTag(@Nullable String tag) {
        this.tag = tag;
    }
}
