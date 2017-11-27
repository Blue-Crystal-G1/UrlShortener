package urlshortener.bluecrystal.domain.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Gets or Sets TypeInfo
 */
public enum TypeInfo {

    ALL_TYPES("ALL_TYPES"),

    RAM_USAGE("RAM_USAGE"),

    MEMORY_USAGE("MEMORY_USAGE"),

    UPTIME("UPTIME"),

    TOTAL_URLS("TOTAL_URLS"),

    TOTAL_CLICKS("TOTAL_CLICKS"),

    TOTAL_USERS("TOTAL_USERS"),

    LAST_REDIRECTION_TIME("LAST_REDIRECTION_TIME");

    private String value;

    TypeInfo(String value) {
        this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(value);
    }

    @JsonCreator
    public static TypeInfo fromValue(String text) {
        for (TypeInfo b : TypeInfo.values()) {
            if (String.valueOf(b.value).equals(text)) {
                return b;
            }
        }
        return null;
    }
}

