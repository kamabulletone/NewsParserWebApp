package com.webapp.newsparser.Services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webapp.newsparser.DAO.NewsRecordRepository;
import com.webapp.newsparser.DAO.PictureRepository;
import com.webapp.newsparser.Models.NewsRecord;
import com.webapp.newsparser.Models.Picture;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class NewsParserService {

    private static final String URL_BASE = "https://ria.ru/";
    private static final ObjectMapper objectMapper = new ObjectMapper();

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

    public NewsParserService(NewsRecordRepository newsRecordRepository, PictureRepository pictureRepository) {
        this.newsRecordRepository = newsRecordRepository;
        this.pictureRepository = pictureRepository;
    }

    @Scheduled(cron = "*/5 * * * * *")
    public void ScheduledParse() {
        for (newsTagsEnum tag : newsTagsEnum.values()) {
            parseNews(tag.toString());
        }
    }

    public List<NewsRecord> getLastNews() {
        // parseNews();
        //return newsRecordRepository.findTop4ByOrderByIdAsc();
        return newsRecordRepository.findTop4ByOrderByCreatedOnDesc();
    }

    public void parseNews(String newsTheme) { //RIA
        try {
            Document doc = Jsoup.connect(URL_BASE + newsTheme).get();
            String title = doc.title();
            //System.out.println(title);
            String[] keyWords = doc.getElementsByAttributeValue("name", "analytics:keyw").attr("content").split(", ");
            //System.out.println(Arrays.toString(keyWords));
            String[] tags = doc.getElementsByAttributeValue("name", "analytics:tags").attr("content").split(",");

            ArrayList<String> newsDate = new ArrayList<>();

            List<Element> newsElements = doc.getElementsByClass("list-item").subList(0, NEWS_PARSE_COUNT); //только первые 4 новости
            newsElements.stream().forEach(element -> {
                NewsRecord newsRecord = new NewsRecord();

                Element contentRoot = element.getElementsByClass("list-item__content").subList(0, 1).get(0); // единственный тег для контента
                List<Element> contentsLinkTags = contentRoot.getElementsByTag("a").subList(0, 2); //2 ссылки внутри тега

                String newsLink = contentsLinkTags.get(0).attr("href"); //ссыдка на ресурс из тега для картинок
                String newsName = contentsLinkTags.get(1).text(); // название тайтла из 2го тега
//.map(x -> Map.of(x.attributes(),x.attr("media"))
                List<Element> test = new ArrayList<>(contentsLinkTags.get(0).child(0).getElementsByTag("source"));

                List<Picture> pictures = new ArrayList<>(); //картинки ТОЛЬКО на 16х9 436х245 px

                for (Element el : test) {
                    List<String> attrValues = new ArrayList<>();
                    List<Attribute> attrs = el.attributes().asList();

                    for (Attribute attr : attrs) {
                        attrValues.add(attr.getValue());
                    }
                    Picture picture = new Picture(0, attrValues.get(0), attrValues.get(1), attrValues.get(2));
                    if (pictureRepository.findBySrc(picture.getSrc()) == null) {
                        picture = pictureRepository.save(picture);
                        pictures.add(picture);
                    }

                }
                String postedTime = element.getElementsByClass("list-item__info") //инфо баннера: дата просмотры
                        .get(0).getElementsByClass("list-item__date").text(); //по популярности
                //System.out.println(element.getElementsByClass("list-item__info"));

                LocalDateTime time = convertStringToDateTime(postedTime, LocalDateTime.now());

//                String resPostedTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + " " + postedTime;
//                LocalDateTime time = LocalDateTime.parse(resPostedTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

                //System.out.println(resPostedTime);
                System.out.println(time);
                //System.out.println(time);

                newsRecord.setPictures(pictures);
                newsRecord.setKeyWord(keyWords[0]);
                newsRecord.setTitle(newsName);
                newsRecord.setTag(tags[0]);
                newsRecord.setContentLink(newsLink);
                newsRecord.setCreatedOn(time);
                if (newsRecordRepository.findByContentLink(newsRecord.getContentLink()) == null) {
                    System.out.println(newsRecord);
                    newsRecordRepository.save(newsRecord);
                }



                //System.out.println(test);


//				System.out.println(test.stream().forEach(el -> el.getAllElements().subList(0,3)));
//				newsPictures.add(contentsLinkTags.get(0).child(0).getAllElements().subList(0,3)
//						.stream().collect(Collectors.toMap(el -> el.)))


            });

            //System.out.println(newsElements);


            //System.out.println(Arrays.toString(tags));
            //System.out.println(doc);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }


    private enum newsTagsEnum {
        economy,
        politics,
        world,
        society,
        incidents,
        defense_safety,
        culture
    }


}
