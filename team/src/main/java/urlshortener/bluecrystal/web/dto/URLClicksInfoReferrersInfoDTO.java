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

    /**
     * Get referrer
     *
     * @return referrer
     **/
    @ApiModelProperty(value = "")
    public String getReferrer() {
        return referrer;
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
        URLClicksInfoReferrersInfoDTO urLClicksInfoReferrersInfo = (URLClicksInfoReferrersInfoDTO) o;
        return Objects.equals(this.referrer, urLClicksInfoReferrersInfo.referrer) &&
                Objects.equals(this.counter, urLClicksInfoReferrersInfo.counter);
    }

}
