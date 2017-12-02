package urlshortener.bluecrystal.domain.messages;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;

/**
 * URLInfo
 */

public class URLInfo {
    @JsonProperty("hash")
    private String hash;

    @JsonProperty("target")
    private String target = null;

    @JsonProperty("uri")
    private String uri = null;

    @JsonProperty("created")
    private String created = null;

    @JsonProperty("totalClicks")
    private Integer totalClicks = null;

    @JsonProperty("lastCheckAvailableDate")
    private String lastCheckAvailableDate = null;

    @JsonProperty("available")
    private Integer available = null;

    @JsonProperty("lastCheckSafeDate")
    private String lastCheckSafeDate = null;

    @JsonProperty("safe")
    private Integer safe = null;

    public URLInfo() {
    }

    public URLInfo(String hash, String target, String uri, String created, Integer totalClicks,
                   String lastCheckAvailableDate, Integer available, String lastCheckSafeDate,
                   Integer safe) {
        this.hash = hash;
        this.target = target;
        this.uri = uri;
        this.created = created;
        this.totalClicks = totalClicks;
        this.lastCheckAvailableDate = lastCheckAvailableDate;
        this.available = available;
        this.lastCheckSafeDate = lastCheckSafeDate;
        this.safe = safe;
    }

    public URLInfo hash(String hash) {
        this.hash = hash;
        return this;
    }


    /**
     * Get hash
     * @return hash
     **/
    @ApiModelProperty(value = "")


    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public URLInfo target(String target) {
        this.target = target;
        return this;
    }

    /**
     * Get target
     * @return target
     **/
    @ApiModelProperty(value = "")


    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public URLInfo uri(String uri) {
        this.uri = uri;
        return this;
    }

    /**
     * Get uri
     * @return uri
     **/
    @ApiModelProperty(value = "")


    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public URLInfo created(String created) {
        this.created = created;
        return this;
    }

    /**
     * Get created
     * @return created
     **/
    @ApiModelProperty(value = "")


    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public URLInfo totalClicks(Integer totalClicks) {
        this.totalClicks = totalClicks;
        return this;
    }

    /**
     * Get totalClicks
     * @return totalClicks
     **/
    @ApiModelProperty(value = "")


    public Integer getTotalClicks() {
        return totalClicks;
    }

    public void setTotalClicks(Integer totalClicks) {
        this.totalClicks = totalClicks;
    }

    public URLInfo lastCheckAvailableDate(String lastCheckAvailableDate) {
        this.lastCheckAvailableDate = lastCheckAvailableDate;
        return this;
    }

    /**
     * Get lastCheckAvailableDate
     * @return lastCheckAvailableDate
     **/
    @ApiModelProperty(value = "")


    public String getLastCheckAvailableDate() {
        return lastCheckAvailableDate;
    }

    public void setLastCheckAvailableDate(String lastCheckAvailableDate) {
        this.lastCheckAvailableDate = lastCheckAvailableDate;
    }

    public URLInfo available(Integer available) {
        this.available = available;
        return this;
    }

    /**
     * Get available
     * @return available
     **/
    @ApiModelProperty(value = "")


    public Integer getAvailable() {
        return available;
    }

    public void setAvailable(Integer available) {
        this.available = available;
    }

    public URLInfo lastCheckSafeDate(String lastCheckSafeDate) {
        this.lastCheckSafeDate = lastCheckSafeDate;
        return this;
    }

    /**
     * Get lastCheckSafeDate
     * @return lastCheckSafeDate
     **/
    @ApiModelProperty(value = "")


    public String getLastCheckSafeDate() {
        return lastCheckSafeDate;
    }

    public void setLastCheckSafeDate(String lastCheckSafeDate) {
        this.lastCheckSafeDate = lastCheckSafeDate;
    }

    public URLInfo safe(Integer safe) {
        this.safe = safe;
        return this;
    }

    /**
     * Get safe
     * @return safe
     **/
    @ApiModelProperty(value = "")


    public Integer getSafe() {
        return safe;
    }

    public void setSafe(Integer safe) {
        this.safe = safe;
    }


    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        URLInfo urLInfo = (URLInfo) o;
        return Objects.equals(this.hash, urLInfo.hash) &&
                Objects.equals(this.target, urLInfo.target) &&
                Objects.equals(this.uri, urLInfo.uri) &&
                Objects.equals(this.created, urLInfo.created) &&
                Objects.equals(this.totalClicks, urLInfo.totalClicks) &&
                Objects.equals(this.lastCheckAvailableDate, urLInfo.lastCheckAvailableDate) &&
                Objects.equals(this.available, urLInfo.available) &&
                Objects.equals(this.lastCheckSafeDate, urLInfo.lastCheckSafeDate) &&
                Objects.equals(this.safe, urLInfo.safe);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hash, target, uri, created, totalClicks, lastCheckAvailableDate, available, lastCheckSafeDate, safe);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class URLInfo {\n");

        sb.append("    hash: ").append(toIndentedString(hash)).append("\n");
        sb.append("    target: ").append(toIndentedString(target)).append("\n");
        sb.append("    uri: ").append(toIndentedString(uri)).append("\n");
        sb.append("    created: ").append(toIndentedString(created)).append("\n");
        sb.append("    totalClicks: ").append(toIndentedString(totalClicks)).append("\n");
        sb.append("    lastCheckAvailableDate: ").append(toIndentedString(lastCheckAvailableDate)).append("\n");
        sb.append("    available: ").append(toIndentedString(available)).append("\n");
        sb.append("    lastCheckSafeDate: ").append(toIndentedString(lastCheckSafeDate)).append("\n");
        sb.append("    safe: ").append(toIndentedString(safe)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}

