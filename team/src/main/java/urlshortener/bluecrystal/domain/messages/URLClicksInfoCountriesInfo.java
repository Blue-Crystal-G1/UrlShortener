package urlshortener.bluecrystal.domain.messages;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;

/**
 * URLClicksInfoCountriesInfo
 */

public class URLClicksInfoCountriesInfo {
    @JsonProperty("country")
    private String country = null;

    @JsonProperty("counter")
    private Integer counter = null;

    public URLClicksInfoCountriesInfo(String country, Integer counter) {
        this.country = country;
        this.counter = counter;
    }

    public URLClicksInfoCountriesInfo country(String country) {
        this.country = country;
        return this;
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

    public void setCountry(String country) {
        this.country = country;
    }

    public URLClicksInfoCountriesInfo counter(Integer counter) {
        this.counter = counter;
        return this;
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

    public void setCounter(Integer counter) {
        this.counter = counter;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        URLClicksInfoCountriesInfo urLClicksInfoCountriesInfo = (URLClicksInfoCountriesInfo) o;
        return Objects.equals(this.country, urLClicksInfoCountriesInfo.country) &&
                Objects.equals(this.counter, urLClicksInfoCountriesInfo.counter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(country, counter);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class URLClicksInfoCountriesInfo {\n");

        sb.append("    country: ").append(toIndentedString(country)).append("\n");
        sb.append("    counter: ").append(toIndentedString(counter)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}

