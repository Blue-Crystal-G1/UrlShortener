package urlshortener.bluecrystal.domain.messages;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.Objects;

/**
 * URLClicksInfo
 */
@Validated

public class URLClicksInfo   {
  @JsonProperty("urlInfo")
  private URLInfo urlInfo = null;

  @JsonProperty("countriesInfo")
  @Valid
  private java.util.List<URLClicksInfoCountriesInfo> countriesInfo = null;

  @JsonProperty("platformsInfo")
  @Valid
  private java.util.List<URLClicksInfoPlatformsInfo> platformsInfo = null;

  @JsonProperty("browsersInfo")
  @Valid
  private java.util.List<URLClicksInfoBrowsersInfo> browsersInfo = null;

  @JsonProperty("referrersInfo")
  @Valid
  private java.util.List<URLClicksInfoReferrersInfo> referrersInfo = null;

  public URLClicksInfo urlInfo(URLInfo urlInfo) {
    this.urlInfo = urlInfo;
    return this;
  }

   /**
   * Get urlInfo
   * @return urlInfo
  **/
  @ApiModelProperty(value = "")

  @Valid

  public URLInfo getUrlInfo() {
    return urlInfo;
  }

  public void setUrlInfo(URLInfo urlInfo) {
    this.urlInfo = urlInfo;
  }

  public URLClicksInfo countriesInfo(java.util.List<URLClicksInfoCountriesInfo> countriesInfo) {
    this.countriesInfo = countriesInfo;
    return this;
  }

  public URLClicksInfo addCountriesInfoItem(URLClicksInfoCountriesInfo countriesInfoItem) {
    if (this.countriesInfo == null) {
      this.countriesInfo = new java.util.ArrayList<>();
    }
    this.countriesInfo.add(countriesInfoItem);
    return this;
  }

   /**
   * Get countriesInfo
   * @return countriesInfo
  **/
  @ApiModelProperty(value = "")

  @Valid

  public java.util.List<URLClicksInfoCountriesInfo> getCountriesInfo() {
    return countriesInfo;
  }

  public void setCountriesInfo(java.util.List<URLClicksInfoCountriesInfo> countriesInfo) {
    this.countriesInfo = countriesInfo;
  }

  public URLClicksInfo platformsInfo(java.util.List<URLClicksInfoPlatformsInfo> platformsInfo) {
    this.platformsInfo = platformsInfo;
    return this;
  }

  public URLClicksInfo addPlatformsInfoItem(URLClicksInfoPlatformsInfo platformsInfoItem) {
    if (this.platformsInfo == null) {
      this.platformsInfo = new java.util.ArrayList<>();
    }
    this.platformsInfo.add(platformsInfoItem);
    return this;
  }

   /**
   * Get platformsInfo
   * @return platformsInfo
  **/
  @ApiModelProperty(value = "")

  @Valid

  public java.util.List<URLClicksInfoPlatformsInfo> getPlatformsInfo() {
    return platformsInfo;
  }

  public void setPlatformsInfo(java.util.List<URLClicksInfoPlatformsInfo> platformsInfo) {
    this.platformsInfo = platformsInfo;
  }

  public URLClicksInfo browsersInfo(java.util.List<URLClicksInfoBrowsersInfo> browsersInfo) {
    this.browsersInfo = browsersInfo;
    return this;
  }

  public URLClicksInfo addBrowsersInfoItem(URLClicksInfoBrowsersInfo browsersInfoItem) {
    if (this.browsersInfo == null) {
      this.browsersInfo = new java.util.ArrayList<>();
    }
    this.browsersInfo.add(browsersInfoItem);
    return this;
  }

   /**
   * Get browsersInfo
   * @return browsersInfo
  **/
  @ApiModelProperty(value = "")

  @Valid

  public java.util.List<URLClicksInfoBrowsersInfo> getBrowsersInfo() {
    return browsersInfo;
  }

  public void setBrowsersInfo(java.util.List<URLClicksInfoBrowsersInfo> browsersInfo) {
    this.browsersInfo = browsersInfo;
  }

  public URLClicksInfo referrersInfo(java.util.List<URLClicksInfoReferrersInfo> referrersInfo) {
    this.referrersInfo = referrersInfo;
    return this;
  }

  public URLClicksInfo addReferrersInfoItem(URLClicksInfoReferrersInfo referrersInfoItem) {
    if (this.referrersInfo == null) {
      this.referrersInfo = new java.util.ArrayList<>();
    }
    this.referrersInfo.add(referrersInfoItem);
    return this;
  }

   /**
   * Get referrersInfo
   * @return referrersInfo
  **/
  @ApiModelProperty(value = "")

  @Valid

  public java.util.List<URLClicksInfoReferrersInfo> getReferrersInfo() {
    return referrersInfo;
  }

  public void setReferrersInfo(java.util.List<URLClicksInfoReferrersInfo> referrersInfo) {
    this.referrersInfo = referrersInfo;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    URLClicksInfo urLClicksInfo = (URLClicksInfo) o;
    return Objects.equals(this.urlInfo, urLClicksInfo.urlInfo) &&
        Objects.equals(this.countriesInfo, urLClicksInfo.countriesInfo) &&
        Objects.equals(this.platformsInfo, urLClicksInfo.platformsInfo) &&
        Objects.equals(this.browsersInfo, urLClicksInfo.browsersInfo) &&
        Objects.equals(this.referrersInfo, urLClicksInfo.referrersInfo);
  }

  @Override
  public int hashCode() {
    return Objects.hash(urlInfo, countriesInfo, platformsInfo, browsersInfo, referrersInfo);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class URLClicksInfo {\n");
    
    sb.append("    urlInfo: ").append(toIndentedString(urlInfo)).append("\n");
    sb.append("    countriesInfo: ").append(toIndentedString(countriesInfo)).append("\n");
    sb.append("    platformsInfo: ").append(toIndentedString(platformsInfo)).append("\n");
    sb.append("    browsersInfo: ").append(toIndentedString(browsersInfo)).append("\n");
    sb.append("    referrersInfo: ").append(toIndentedString(referrersInfo)).append("\n");
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

