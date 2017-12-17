package urlshortener.bluecrystal.web.dto.util;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Gets or Sets ClickInterval
 */
public enum ClickInterval {

    ALL("ALL"),

    YEAR("YEAR"),

    MONTH("MONTH"),

    WEEK("WEEK"),

    DAY("DAY"),

    LASTHOURS("LASTHOURS");

    private String value;

    ClickInterval(String value) {
        this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(value);
    }

    @JsonCreator
    public static ClickInterval fromValue(String text) {
        for (ClickInterval b : ClickInterval.values()) {
            if (String.valueOf(b.value).equals(text)) {
                return b;
            }
        }
        return null;
    }
}

