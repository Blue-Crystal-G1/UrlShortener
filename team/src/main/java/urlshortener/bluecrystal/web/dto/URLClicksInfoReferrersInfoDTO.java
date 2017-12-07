package urlshortener.bluecrystal.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;

/**
 * URLClicksInfoReferrersInfoDTO
 */

public class URLClicksInfoReferrersInfoDTO {
    @JsonProperty("referrer")
    private String referrer = null;

    @JsonProperty("counter")
    private Integer counter = null;

    public URLClicksInfoReferrersInfoDTO(String referrer, Integer counter) {
        this.referrer = referrer;
        this.counter = counter;
    }

    public URLClicksInfoReferrersInfoDTO referrer(String referrer) {
        this.referrer = referrer;
        return this;
    }

    /**
     * Get referrer
     *
     * @return referrer
     **/
    @ApiModelProperty(value = "")


    public String getReferrer() {
        return referrer;
    }

    public void setReferrer(String referrer) {
        this.referrer = referrer;
    }

    public URLClicksInfoReferrersInfoDTO counter(Integer counter) {
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
        URLClicksInfoReferrersInfoDTO urLClicksInfoReferrersInfo = (URLClicksInfoReferrersInfoDTO) o;
        return Objects.equals(this.referrer, urLClicksInfoReferrersInfo.referrer) &&
                Objects.equals(this.counter, urLClicksInfoReferrersInfo.counter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(referrer, counter);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class URLClicksInfoReferrersInfoDTO {\n");

        sb.append("    referrer: ").append(toIndentedString(referrer)).append("\n");
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
