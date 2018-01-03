package urlshortener.bluecrystal.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;

/**
 * URLClicksInfoBrowsersInfoDTO
 */

public class URLClicksInfoBrowsersInfoDTO {
    @JsonProperty("browser")
    private String browser = null;

    @JsonProperty("counter")
    private Integer counter = null;

    public URLClicksInfoBrowsersInfoDTO(String browser, Integer counter) {
        this.browser = browser;
        this.counter = counter;
    }

    /**
     * Get browser
     *
     * @return browser
     **/
    @ApiModelProperty(value = "")
    public String getBrowser() {
        return browser;
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
        URLClicksInfoBrowsersInfoDTO urLClicksInfoBrowsersInfo = (URLClicksInfoBrowsersInfoDTO) o;
        return Objects.equals(this.browser, urLClicksInfoBrowsersInfo.browser) &&
                Objects.equals(this.counter, urLClicksInfoBrowsersInfo.counter);
    }

}
