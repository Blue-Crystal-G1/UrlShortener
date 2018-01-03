package urlshortener.bluecrystal.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

/**
 * URLClicksInfoClicksInfoDTO
 */

public class URLClicksInfoClicksInfoDTO {
    @JsonProperty("time")
    private Long time = null;

    @JsonProperty("counter")
    private Integer counter = null;

    public URLClicksInfoClicksInfoDTO(Long time, Integer counter) {
        this.time = time;
        this.counter = counter;
    }

    /**
     * Get time
     *
     * @return time
     **/
    @ApiModelProperty(value = "")
    public Long getTime() {
        return time;
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


}

