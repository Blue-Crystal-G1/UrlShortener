package urlshortener.bluecrystal.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

/**
 * URLInfoDTO
 */

public class URLInfoDTO {
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

    public URLInfoDTO() {
    }

    public URLInfoDTO(String hash, String target, String uri, String created, Integer totalClicks,
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

}

