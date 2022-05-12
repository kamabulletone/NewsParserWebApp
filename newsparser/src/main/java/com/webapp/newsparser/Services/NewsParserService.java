package com.webapp.newsparser.Services;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.webapp.newsparser.DAO.NewsRecordRepository;
import com.webapp.newsparser.DAO.PictureRepository;
import com.webapp.newsparser.Filters.NewsRecordFilter;
import com.webapp.newsparser.Models.NewsRecord;
import com.webapp.newsparser.Models.Picture;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
@Slf4j
public class NewsParserService {

    private static final String URL_BASE = "https://ria.ru/";
    private static final ObjectMapper objectMapper = new ObjectMapper();
//    private static final String tagsNames;


    public LocalDateTime convertStringToDateTime(String str, LocalDateTime currentDateTime) {
        String resPostedTime;
        if (str.matches("(?m)^(\\d\\d:\\d\\d)")) {  //21:25
            resPostedTime = currentDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + " " + str;
            return LocalDateTime.parse(resPostedTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        } else if (str.contains("Вчера")) { // Вчера, 21:25
            String[] strBuff = str.split(", ");
            String time = strBuff[1];
            resPostedTime = currentDateTime.minus(1, ChronoUnit.DAYS).
                    format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + " " + time;
            return LocalDateTime.parse(resPostedTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        } else { //13 ноября, 21:25 ; 9 июля 2015, 21:25
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MMMM-yyyy", new Locale("ru"));
            String[] strBuff = str.split(", ");
            LocalDate date;
            if (str.matches(".*\\s\\d{4},\\s.*")) {
                System.out.println("HAS YEAR");
                date = LocalDate.parse(strBuff[0].replace(" ", "-"), formatter);
            } else {
                System.out.println("NO YEAR");
                date = LocalDate.parse((strBuff[0] + " " + currentDateTime.getYear()).replace(" ", "-"), formatter);
            }
            resPostedTime = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + " " + strBuff[1];
            return LocalDateTime.parse(resPostedTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        }
    }

    private static final int NEWS_PARSE_COUNT = 4;
    NewsRecordRepository newsRecordRepository;
    PictureRepository pictureRepository;

    @Scheduled(cron = "0 0/1 * * * *")
    @PostConstruct
    public void ScheduledParse() {
        for (newsTagsEnum tag : newsTagsEnum.values()) {
            log.info("Starting parsin: " + URL_BASE + tag.key);
            parseNews(tag.key);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }

        }
        log.info("Finished all parsing");
    }

    public NewsParserService(NewsRecordRepository newsRecordRepository, PictureRepository pictureRepository) {
        this.newsRecordRepository = newsRecordRepository;
        this.pictureRepository = pictureRepository;
    }

    public Page<NewsRecord> getAll(NewsRecordFilter filter, Pageable pageable) {
        return newsRecordRepository.findAll(byFilter(filter), pageable);
    }

    private Specification<NewsRecord> byFilter(NewsRecordFilter filter) {
        return new Specification<NewsRecord>() {
            @Override
            public Predicate toPredicate(Root<NewsRecord> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();

                if (filter.getCreatedFrom() != null) {
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(
                            root.get("createdOn"), filter.getCreatedFrom()
                    ));
                }
                if (filter.getCreatedTo() != null) {
                    predicates.add(criteriaBuilder.lessThanOrEqualTo(
                            root.get("createdOn"), filter.getCreatedTo()
                    ));
                }
                if (filter.getTag() != null) {
                    predicates.add(criteriaBuilder.equal(
                            root.get("tag"), filter.getTag()
                    ));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            }
        };
    }

    public List<String> getNewsTags() {
        return Arrays.stream(newsTagsEnum.values()).map(x -> x.value).collect(Collectors.toList());
    }


    public void parseNews(String newsTheme) { //RIA
        try {
            Document doc = Jsoup.connect(URL_BASE + newsTheme).get();
            String title = doc.title();

            String[] keyWords = doc.getElementsByAttributeValue("name", "analytics:keyw").attr("content").split(", ");
            String[] tags = doc.getElementsByAttributeValue("name", "analytics:tags").attr("content").split(",");

            ArrayList<String> newsDate = new ArrayList<>();

            List<Element> newsElements = doc.getElementsByClass("list-item");

            newsElements.forEach(element -> {
                NewsRecord newsRecord = new NewsRecord();

                Element contentRoot = element.getElementsByClass("list-item__content").subList(0, 1).get(0); // единственный тег для контента
                List<Element> contentsLinkTags = contentRoot.getElementsByTag("a").subList(0, 2); //2 ссылки внутри тега
//                System.out.println(contentsLinkTags);

                String newsLink = contentsLinkTags.get(0).attr("href"); //ссыдка на ресурс из тега для картинок
                String newsName = contentsLinkTags.get(1).text(); // название тайтла из 2го тега
                List<Element> test = new ArrayList<>(contentsLinkTags.get(0).child(0).getElementsByTag("source"));

                List<Picture> pictures = new ArrayList<>();

                for (Element el : test) {
                    List<String> attrValues = new ArrayList<>();
                    List<Attribute> attrs = el.attributes().asList();

                    for (Attribute attr : attrs) {
                        attrValues.add(attr.getValue());
                    }
                    Picture picture = new Picture(0, attrValues.get(0), attrValues.get(1), attrValues.get(2));
//                    if ( (pictureRepository.findBySrc(picture.getSrc()) == null) ) {
//                        picture = pictureRepository.save(picture);
//                    }
//                    else {
//                        picture = pictureRepository.findBySrc(picture.getSrc());
//                        picture.setId(0);
//                    }

                    pictures.add(picture);

                }
                String postedTime = element.getElementsByClass("list-item__info") //инфо баннера: дата просмотры
                        .get(0).getElementsByClass("list-item__date").text(); //по популярности

                LocalDateTime time = convertStringToDateTime(postedTime, LocalDateTime.now());

                //System.out.println(time);
                newsRecord.setPictures(pictures);
                newsRecord.setKeyWord(keyWords[0]);
                newsRecord.setTitle(newsName);
                newsRecord.setTag(tags[0]);
                newsRecord.setContentLink(newsLink);
                newsRecord.setCreatedOn(time);

                if (newsRecordRepository.findByContentLink(newsLink) == null) {


//                    System.out.println(newsRecord);
                    pictureRepository.saveAllAndFlush(pictures);
                    //newsRecordRepository.save(newsRecord);
                    newsRecordRepository.saveAndFlush(newsRecord);
                }

            });

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    private enum newsTagsEnum {
        TAG("politics", "Политика"),
        TAG1("world", "В мире"),
        TAG2("economy", "Экономика"),
        TAG3("society", "Общество"),
        TAG4("incidents", "Происшествия"),
        TAG5("defense_safety", "Безопасность"),
        TAG6("culture", "Культура");

        private final String key;
        private final String value;

        newsTagsEnum(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

    }


}
