package urlshortener.bluecrystal.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import urlshortener.bluecrystal.persistence.model.Click;
import urlshortener.bluecrystal.persistence.dao.ClickRepository;
import urlshortener.bluecrystal.web.dto.util.ClickInterval;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class ClickService {

    private final static Logger LOGGER = LoggerFactory.getLogger(ClickService.class);

    @Autowired
    private ClickRepository clickRepository;


    public Click save(Click click) {
        if(click != null)
            return clickRepository.save(click);
        else return null;
    }

    public Integer countClicksByHash(String hash) {
        return clickRepository.countClicksByHash(hash);
    }

    public List<Click> getClicksByInterval(String interval) {
        List<Click> clicks = null;
        LocalDate lcNow = LocalDate.now();

        // Get all clicks since the url creation
        if(interval.equals(ClickInterval.ALL.toString())) {
            clicks = clickRepository.findAll();
        }
        // Get all clicks in the current year
        else if(interval.equals(ClickInterval.YEAR.toString())) {
            LocalDate start = lcNow.withDayOfYear(1);
            LocalDate end = lcNow.withDayOfYear(lcNow.lengthOfYear());
            clicks = findClicksByCreatedBetween(start, end);
        }
        // Get all clicks in the current month
        else if(interval.equals(ClickInterval.MONTH.toString())) {
            LocalDate start = lcNow.withDayOfMonth(1);
            LocalDate end = lcNow.withDayOfMonth(lcNow.lengthOfMonth());
            clicks = findClicksByCreatedBetween(start, end);
        }
        // Get all clicks in the current week
        else if(interval.equals(ClickInterval.WEEK.toString())) {
            LocalDate start = lcNow.with(DayOfWeek.MONDAY);
            LocalDate end = lcNow.with(DayOfWeek.SUNDAY);
            clicks = findClicksByCreatedBetween(start, end);
        }
        // Get all clicks in the curreny day
        else if(interval.equals(ClickInterval.DAY.toString())) {
            clicks = findClicksByCreatedBetween(lcNow, lcNow);
        }
        // Get all clicks in the last two hours
        else if(interval.equals(ClickInterval.LASTHOURS.toString())) {
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime start = now.minusHours(2);
            clicks = clickRepository.findByCreatedBetween(start, now);
        }

        return clicks;
    }

    public List<Click> getClicksByIntervalAndHash(String hash, String interval) {
        List<Click> clicks = null;
        LocalDate lcNow = LocalDate.now();

        // Get all clicks since the url creation
        if(interval.equals(ClickInterval.ALL.toString())) {
            clicks = clickRepository.findByHash(hash);
        }
        // Get all clicks in the current year
        else if(interval.equals(ClickInterval.YEAR.toString())) {
            LocalDate start = lcNow.withDayOfYear(1);
            LocalDate end = lcNow.withDayOfYear(lcNow.lengthOfYear());
            clicks = findClicksByHashAndCreatedBetween(hash, start, end);
        }
        // Get all clicks in the current month
        else if(interval.equals(ClickInterval.MONTH.toString())) {
            LocalDate start = lcNow.withDayOfMonth(1);
            LocalDate end = lcNow.withDayOfMonth(lcNow.lengthOfMonth());
            clicks = findClicksByHashAndCreatedBetween(hash, start, end);
        }
        // Get all clicks in the current week
        else if(interval.equals(ClickInterval.WEEK.toString())) {
            LocalDate start = lcNow.with(DayOfWeek.MONDAY);
            LocalDate end = lcNow.with(DayOfWeek.SUNDAY);
            clicks = findClicksByHashAndCreatedBetween(hash, start, end);
        }
        // Get all clicks in the curreny day
        else if(interval.equals(ClickInterval.DAY.toString())) {
            clicks = findClicksByHashAndCreatedBetween(hash, lcNow, lcNow);
        }
        // Get all clicks in the last two hours
        else if(interval.equals(ClickInterval.LASTHOURS.toString())) {
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime start = now.minusHours(2);
            clicks = clickRepository.findByHashAndCreatedBetween(hash, start, now);
        }

        return clicks;
    }

    private List<Click> findClicksByHashAndCreatedBetween(String hash, LocalDate startDate, LocalDate endDate) {
        LocalTime startTime = LocalTime.of(0, 0, 0, 0);
        LocalTime endTime = LocalTime.of(23, 59, 59, 999999999);

        return clickRepository.findByHashAndCreatedBetween(hash,
                LocalDateTime.of(startDate, startTime),
                LocalDateTime.of(endDate, endTime));

    }

    private List<Click> findClicksByCreatedBetween(LocalDate startDate, LocalDate endDate) {
        LocalTime startTime = LocalTime.of(0, 0, 0, 0);
        LocalTime endTime = LocalTime.of(23, 59, 59, 999999999);

        return clickRepository.findByCreatedBetween(
                LocalDateTime.of(startDate, startTime),
                LocalDateTime.of(endDate, endTime));

    }
}
