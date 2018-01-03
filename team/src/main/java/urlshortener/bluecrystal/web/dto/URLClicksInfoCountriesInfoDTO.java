package urlshortener.bluecrystal.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;

/**
 * URLClicksInfoCountriesInfoDTO
 */

public class URLClicksInfoCountriesInfoDTO {
    @JsonProperty("country")
    private String country = null;

    @JsonProperty("counter")
    private Integer counter = null;

    public URLClicksInfoCountriesInfoDTO(String country, Integer counter) {
        this.country = country;
        this.counter = counter;
    }

    /**
     * Get country
     *
     * @return country
     **/
    @ApiModelProperty(value = "")
    public String getCountry() {
        return country;
    }

    /**
     * Get counter
     *
     * @return counter
     **/
    @ApiModelProperty(value = "")
    public Integer getCounter() {
        return counter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        URLClicksInfoCountriesInfoDTO urLClicksInfoCountriesInfo = (URLClicksInfoCountriesInfoDTO) o;
        return Objects.equals(this.country, urLClicksInfoCountriesInfo.country) &&
                Objects.equals(this.counter, urLClicksInfoCountriesInfo.counter);
    }

}

