package urlshortener.bluecrystal.domain.messages;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;

/**
 * URLClicksInfoPlatformsInfo
 */

public class URLClicksInfoPlatformsInfo   {
  @JsonProperty("platform")
  private String platform = null;

  @JsonProperty("counter")
  private Integer counter = null;

  public URLClicksInfoPlatformsInfo platform(String platform) {
    this.platform = platform;
    return this;
  }

   /**
   * Get platform
   * @return platform
  **/
  @ApiModelProperty(value = "")


  public String getPlatform() {
    return platform;
  }

  public void setPlatform(String platform) {
    this.platform = platform;
  }

  public URLClicksInfoPlatformsInfo counter(Integer counter) {
    this.counter = counter;
    return this;
  }

   /**
   * Get counter
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
    URLClicksInfoPlatformsInfo urLClicksInfoPlatformsInfo = (URLClicksInfoPlatformsInfo) o;
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
    sb.append("class URLClicksInfoPlatformsInfo {\n");
    
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

