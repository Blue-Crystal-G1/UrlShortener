package urlshortener.bluecrystal.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.Objects;

/**
 * URLClicksInfo
 */
@Validated

public class URLClicksInfoDTO {
    @JsonProperty("urlInfo")
    private URLInfoDTO urlInfo = null;

    @JsonProperty("countriesInfo")
    @Valid
    private java.util.List<URLClicksInfoCountriesInfoDTO> countriesInfo = null;

    @JsonProperty("platformsInfo")
    @Valid
    private java.util.List<URLClicksInfoPlatformsInfoDTO> platformsInfo = null;

    @JsonProperty("browsersInfo")
    @Valid
    private java.util.List<URLClicksInfoBrowsersInfoDTO> browsersInfo = null;

    @JsonProperty("referrersInfo")
    @Valid
    private java.util.List<URLClicksInfoReferrersInfoDTO> referrersInfo = null;

    @JsonProperty("clicksInfo")
    @Valid
    private java.util.List<URLClicksInfoClicksInfoDTO> clicksInfo = null;

    /**
     * Get urlInfo
     *
     * @return urlInfo
     **/
    @ApiModelProperty(value = "")
    @Valid
    public URLInfoDTO getUrlInfo() {
        return urlInfo;
    }

    public void setUrlInfo(URLInfoDTO urlInfo) {
        this.urlInfo = urlInfo;
    }

    public URLClicksInfoDTO addCountriesInfoItem(URLClicksInfoCountriesInfoDTO countriesInfoItem) {
        if (this.countriesInfo == null) {
            this.countriesInfo = new java.util.ArrayList<>();
        }
        this.countriesInfo.add(countriesInfoItem);
        return this;
    }

    /**
     * Get countriesInfo
     *
     * @return countriesInfo
     **/
    @ApiModelProperty(value = "")
    @Valid
    public java.util.List<URLClicksInfoCountriesInfoDTO> getCountriesInfo() {
        return countriesInfo;
    }

    public URLClicksInfoDTO addPlatformsInfoItem(URLClicksInfoPlatformsInfoDTO platformsInfoItem) {
        if (this.platformsInfo == null) {
            this.platformsInfo = new java.util.ArrayList<>();
        }
        this.platformsInfo.add(platformsInfoItem);
        return this;
    }

    /**
     * Get platformsInfo
     *
     * @return platformsInfo
     **/
    @ApiModelProperty(value = "")
    @Valid
    public java.util.List<URLClicksInfoPlatformsInfoDTO> getPlatformsInfo() {
        return platformsInfo;
    }

    public URLClicksInfoDTO addBrowsersInfoItem(URLClicksInfoBrowsersInfoDTO browsersInfoItem) {
        if (this.browsersInfo == null) {
            this.browsersInfo = new java.util.ArrayList<>();
        }
        this.browsersInfo.add(browsersInfoItem);
        return this;
    }

    /**
     * Get browsersInfo
     *
     * @return browsersInfo
     **/
    @ApiModelProperty(value = "")
    @Valid
    public java.util.List<URLClicksInfoBrowsersInfoDTO> getBrowsersInfo() {
        return browsersInfo;
    }

    public URLClicksInfoDTO addReferrersInfoItem(URLClicksInfoReferrersInfoDTO referrersInfoItem) {
        if (this.referrersInfo == null) {
            this.referrersInfo = new java.util.ArrayList<>();
        }
        this.referrersInfo.add(referrersInfoItem);
        return this;
    }

    /**
     * Get referrersInfo
     *
     * @return referrersInfo
     **/
    @ApiModelProperty(value = "")
    @Valid
    public java.util.List<URLClicksInfoReferrersInfoDTO> getReferrersInfo() {
        return referrersInfo;
    }

    public URLClicksInfoDTO addClicksInfoItem(URLClicksInfoClicksInfoDTO clicksInfoItem) {
        if (this.clicksInfo == null) {
            this.clicksInfo = new java.util.ArrayList<>();
        }
        this.clicksInfo.add(clicksInfoItem);
        return this;
    }

    /**
     * Get clicksInfo
     *
     * @return clicksInfo
     **/
    @ApiModelProperty(value = "")
    @Valid
    public java.util.List<URLClicksInfoClicksInfoDTO> getClicksInfo() {
        return clicksInfo;
    }


    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        URLClicksInfoDTO urLClicksInfo = (URLClicksInfoDTO) o;
        return Objects.equals(this.urlInfo, urLClicksInfo.urlInfo) &&
                Objects.equals(this.countriesInfo, urLClicksInfo.countriesInfo) &&
                Objects.equals(this.platformsInfo, urLClicksInfo.platformsInfo) &&
                Objects.equals(this.browsersInfo, urLClicksInfo.browsersInfo) &&
                Objects.equals(this.referrersInfo, urLClicksInfo.referrersInfo) &&
                Objects.equals(this.clicksInfo, urLClicksInfo.clicksInfo);
    }

}
