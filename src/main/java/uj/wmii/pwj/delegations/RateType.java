package uj.wmii.pwj.delegations;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;

public enum RateType {
    FULL (BigDecimal.valueOf(1.0)),
    HALF (BigDecimal.valueOf(0.5)),
    ONE_THIRD(BigDecimal.ONE.divide(BigDecimal.valueOf(3), 10, RoundingMode.HALF_UP)),
    ZERO (BigDecimal.valueOf(0.0));
    
    private final BigDecimal multiplier;

    RateType(BigDecimal multiplier) {
        this.multiplier = multiplier;
    }

    public BigDecimal multiply(BigDecimal rate) {
        return rate
                .multiply(multiplier)
                .setScale(2, RoundingMode.HALF_UP);
    }
    
    public static RateType getRateType(Duration rest) {
        if (rest.isNegative() || rest.isZero()) {
            return ZERO;
        } else if (rest.compareTo(Duration.ofHours(12)) > 0) {
            return FULL;
        } else if ((rest.compareTo(Duration.ofHours(8)) > 0) && (rest.compareTo(Duration.ofHours(12)) <= 0)) {
            return HALF;
        } else {
            return ONE_THIRD;
        }
    }

    public static BigDecimal calcRest(BigDecimal rate, Duration rest) {
        return getRateType(rest).multiply(rate);
    }
}
