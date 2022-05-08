package com.webapp.newsparser;

import com.webapp.newsparser.Services.NewsParserService;
import com.webapp.newsparser.configuration.BaseDbTestClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.shaded.org.hamcrest.MatcherAssert;
import org.testcontainers.shaded.org.hamcrest.Matchers;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@SpringBootTest
public class DateFormatTest extends BaseDbTestClass {

    @Autowired
    private NewsParserService newsParserService;

    @Test
    public void givenLocalDateTime_whenNow_thenGetFixedLocalDateTime() {
        Clock clock = Clock.fixed(Instant.parse("2014-12-22T10:15:30.00Z"), ZoneId.of("UTC"));
        LocalDateTime dateTimeExpected = LocalDateTime.parse("2014-12-22T10:15:30");

        LocalDateTime dateTime = LocalDateTime.now(clock);

        MatcherAssert.assertThat(dateTime, Matchers.equalTo(dateTimeExpected));
    }

    @Test
    public void testStringContainingYearRIATest() {
        Clock clock = Clock.fixed(Instant.parse("2015-07-09T21:51:00.00Z"), ZoneId.of("UTC"));
        LocalDateTime time1 = newsParserService.convertStringToDateTime("9 июля 2015, 21:51", LocalDateTime.now(clock));
        Assertions.assertEquals(LocalDateTime
                        .of(2015, 7, 9, 21, 51, 0),
                time1);
        System.out.println(time1);
    }

    @Test
    public void testStringNotContainingYearRIATest() {
        Clock clock = Clock.fixed(Instant.parse("2015-07-09T21:51:00.00Z"), ZoneId.of("UTC"));
        LocalDateTime time1 = newsParserService.convertStringToDateTime("9 июля, 21:51", LocalDateTime.now(clock));
        Assertions.assertEquals(LocalDateTime
                        .of(2015, 7, 9, 21, 51, 0),
                time1);
        System.out.println(time1);
    }

    @Test
    public void testYesterdayKeywordRIATest() {
        Clock clock = Clock.fixed(Instant.parse("2021-07-09T21:51:00.00Z"), ZoneId.of("UTC"));
        System.out.println(LocalDateTime.now());
        LocalDateTime time1 = newsParserService.convertStringToDateTime("Вчера, 21:51", LocalDateTime.now(clock));
        Assertions.assertEquals(LocalDateTime
                        .of(2021, 7, 8, 21, 51, 0),
                time1);
        System.out.println(time1);
    }

    @Test
    public void testYesterdayOverMonthKeywordRIATest() {
        Clock clock = Clock.fixed(Instant.parse("2021-07-01T21:51:00.00Z"), ZoneId.of("UTC"));
        LocalDateTime time1 = newsParserService.convertStringToDateTime("Вчера, 21:51", LocalDateTime.now(clock));
        Assertions.assertEquals(LocalDateTime
                        .of(2021, 6, 30, 21, 51, 0),
                time1);
        System.out.println(time1);
    }

    @Test
    public void testTodayKeywordRIATest() {
        LocalDateTime time1 = newsParserService.convertStringToDateTime("21:51", LocalDateTime.now());
        Assertions.assertEquals(LocalDateTime
                        .of(LocalDateTime.now().getYear(), LocalDateTime.now().getMonth(), LocalDateTime.now().getDayOfMonth(), 21, 51, 0),
                time1);
        System.out.println(time1);
    }
}
