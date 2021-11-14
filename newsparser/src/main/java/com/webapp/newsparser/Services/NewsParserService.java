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
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class NewsParserService {

    private static final String URL_ECONOMY = "https://ria.ru/economy";
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final int NEWS_PARSE_COUNT = 4;
    NewsRecordRepository newsRecordRepository;
    PictureRepository pictureRepository;

    public NewsParserService(NewsRecordRepository newsRecordRepository, PictureRepository pictureRepository) {
        this.newsRecordRepository = newsRecordRepository;
        this.pictureRepository = pictureRepository;
    }

    public List<NewsRecord> getLastNews() {
        parseNews();
        //return newsRecordRepository.findTop4ByOrderByIdAsc();
        return newsRecordRepository.findTop4ByOrderByCreatedOnDesc();
    }

    public void parseNews() {
        try {
            Document doc = Jsoup.connect(URL_ECONOMY).get();
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
                String resPostedTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + " " + postedTime;
                LocalDateTime time = LocalDateTime.parse(resPostedTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

                System.out.println(resPostedTime);
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
}
