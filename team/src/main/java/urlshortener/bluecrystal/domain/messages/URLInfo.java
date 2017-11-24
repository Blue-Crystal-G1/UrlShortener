package urlshortener.bluecrystal.domain.messages;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import urlshortener.bluecrystal.domain.Click;
import urlshortener.bluecrystal.domain.ShortURL;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * URLInfo
 */

public class URLInfo   {
  @JsonProperty("shortUrl")
  private ShortURL shortUrl = null;

  @JsonProperty("click")
  private List<Click> click = new ArrayList<Click>();

  public URLInfo shortUrl(ShortURL shortUrl) {
    this.shortUrl = shortUrl;
    return this;
  }

   /**
   * Get shortUrl
   * @return shortUrl
  **/
  @ApiModelProperty(value = "")
  public ShortURL getShortUrl() {
    return shortUrl;
  }

  public void setShortUrl(ShortURL shortUrl) {
    this.shortUrl = shortUrl;
  }

  public URLInfo click(List<Click> click) {
    this.click = click;
    return this;
  }

  public URLInfo addClickItem(Click clickItem) {
    this.click.add(clickItem);
    return this;
  }

   /**
   * Get click
   * @return click
  **/
  @ApiModelProperty(value = "")
  public List<Click> getClick() {
    return click;
  }

  public void setClick(List<Click> click) {
    this.click = click;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    URLInfo urLInfo = (URLInfo) o;
    return Objects.equals(this.shortUrl, urLInfo.shortUrl) &&
        Objects.equals(this.click, urLInfo.click);
  }

  @Override
  public int hashCode() {
    return Objects.hash(shortUrl, click);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class URLInfo {\n");
    
    sb.append("    shortUrl: ").append(toIndentedString(shortUrl)).append("\n");
    sb.append("    click: ").append(toIndentedString(click)).append("\n");
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

