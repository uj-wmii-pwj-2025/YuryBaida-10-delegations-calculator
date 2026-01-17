package uj.wmii.pwj.delegations;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Calc {

    BigDecimal calculate(String name, String start, String end, BigDecimal dailyRate) throws IllegalArgumentException {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm VV");
        Instant startTime = ZonedDateTime.parse(start, formatter).toInstant();
        Instant endTime = ZonedDateTime.parse(end, formatter).toInstant();
        Duration diff = Duration.between(startTime, endTime);
        long fullDays = diff.toDays();
        Duration rest = diff.minusDays(fullDays);
        BigDecimal restMoney = RateType.calcRest(dailyRate, rest);

        return dailyRate.multiply(BigDecimal.valueOf(fullDays)).add(restMoney);
    }
}
