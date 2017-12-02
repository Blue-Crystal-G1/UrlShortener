package urlshortener.bluecrystal.domain.messages;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;

/**
 * URLClicksInfoBrowsersInfo
 */

public class URLClicksInfoBrowsersInfo   {
  @JsonProperty("browser")
  private String browser = null;

  @JsonProperty("counter")
  private Integer counter = null;

  public URLClicksInfoBrowsersInfo browser(String browser) {
    this.browser = browser;
    return this;
  }

   /**
   * Get browser
   * @return browser
  **/
  @ApiModelProperty(value = "")


  public String getBrowser() {
    return browser;
  }

  public void setBrowser(String browser) {
    this.browser = browser;
  }

  public URLClicksInfoBrowsersInfo counter(Integer counter) {
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
    URLClicksInfoBrowsersInfo urLClicksInfoBrowsersInfo = (URLClicksInfoBrowsersInfo) o;
    return Objects.equals(this.browser, urLClicksInfoBrowsersInfo.browser) &&
        Objects.equals(this.counter, urLClicksInfoBrowsersInfo.counter);
  }

  @Override
  public int hashCode() {
    return Objects.hash(browser, counter);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class URLClicksInfoBrowsersInfo {\n");
    
    sb.append("    browser: ").append(toIndentedString(browser)).append("\n");
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

