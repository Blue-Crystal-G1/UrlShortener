package urlshortener.bluecrystal.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;

/**
 * URLClicksInfoPlatformsInfoDTO
 */

public class URLClicksInfoPlatformsInfoDTO {
    @JsonProperty("platform")
    private String platform = null;

    @JsonProperty("counter")
    private Integer counter = null;

    public URLClicksInfoPlatformsInfoDTO(String platform, Integer counter) {
        this.platform = platform;
        this.counter = counter;
    }

    /**
     * Get platform
     *
     * @return platform
     **/
    @ApiModelProperty(value = "")
    public String getPlatform() {
        return platform;
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
        URLClicksInfoPlatformsInfoDTO urLClicksInfoPlatformsInfo = (URLClicksInfoPlatformsInfoDTO) o;
        return Objects.equals(this.platform, urLClicksInfoPlatformsInfo.platform) &&
                Objects.equals(this.counter, urLClicksInfoPlatformsInfo.counter);
    }

}
