package urlshortener.bluecrystal.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import urlshortener.bluecrystal.persistence.dao.SystemCpuRepository;
import urlshortener.bluecrystal.persistence.model.SystemCpuUsage;
import urlshortener.bluecrystal.web.dto.util.ClickInterval;

import java.time.*;
import java.util.List;

@Service
public class SystemCpuService {

    private final static Logger LOGGER = LoggerFactory.getLogger(SystemCpuService.class);

    @Autowired
    protected SystemCpuRepository systemCpuRepository;

    public List<SystemCpuUsage> getSystemCpuByInterval(String interval) {
        List<SystemCpuUsage> usages = null;
        LocalDate lcNow = LocalDate.now();

        // Get all system information
        if(interval.equals(ClickInterval.ALL.toString())) {
            usages = systemCpuRepository.findAll();
        }
        // Get all system information in the current year
        else if(interval.equals(ClickInterval.YEAR.toString())) {
            LocalDate start = lcNow.withDayOfYear(1);
            LocalDate end = lcNow.withDayOfYear(lcNow.lengthOfYear());
            usages = findSystemCpuByDatesBetween(start,end);
        }
        // Get all system information in the current month
        else if(interval.equals(ClickInterval.MONTH.toString())) {
            LocalDate start = lcNow.withDayOfMonth(1);
            LocalDate end = lcNow.withDayOfMonth(lcNow.lengthOfMonth());
            usages = findSystemCpuByDatesBetween(start, end);
        }
        // Get all system information in the current week
        else if(interval.equals(ClickInterval.WEEK.toString())) {
            LocalDate start = lcNow.with(DayOfWeek.MONDAY);
            LocalDate end = lcNow.with(DayOfWeek.SUNDAY);
            usages = findSystemCpuByDatesBetween(start, end);
        }
        // Get all system information in the curreny day
        else if(interval.equals(ClickInterval.DAY.toString())) {
            usages = findSystemCpuByDatesBetween(lcNow, lcNow);
        }
        // Get all system information in the last two hours
        else if(interval.equals(ClickInterval.LASTHOURS.toString())) {
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime start = now.minusHours(2);
            usages = systemCpuRepository.findByTimeBetween(
                    getMillisFromLocalDateTime(start),
                    getMillisFromLocalDateTime(now));
        }

        return usages;
    }

    private List<SystemCpuUsage> findSystemCpuByDatesBetween(LocalDate startDate, LocalDate endDate) {
        LocalTime startTime = LocalTime.of(0, 0, 0, 0);
        LocalTime endTime = LocalTime.of(23, 59, 59, 999999999);

        return systemCpuRepository.findByTimeBetween(
                getMillisFromLocalDateTime(LocalDateTime.of(startDate, startTime)),
                getMillisFromLocalDateTime(LocalDateTime.of(endDate, endTime)));
    }

    private static Long getMillisFromLocalDateTime(LocalDateTime date) {
        return date.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }
}
