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

    public void setPlatform(String platform) {
        this.platform = platform;
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
        URLClicksInfoPlatformsInfoDTO urLClicksInfoPlatformsInfo = (URLClicksInfoPlatformsInfoDTO) o;
        return Objects.equals(this.platform, urLClicksInfoPlatformsInfo.platform) &&
                Objects.equals(this.counter, urLClicksInfoPlatformsInfo.counter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(platform, counter);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class URLClicksInfoPlatformsInfoDTO {\n");

        sb.append("    platform: ").append(toIndentedString(platform)).append("\n");
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
