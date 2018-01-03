package urlshortener.bluecrystal.persistence.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * ShortURL
 */
@Entity
@Table(name = "SHORTURL")
public class ShortURL {

    @Id
    @Column(name = "HASH")
    @JsonProperty("hash")
    private String hash = null;

    @JsonProperty("target")
    @Column(name = "TARGET")
    private String target = null;

    @JsonProperty("uri")
    private URI uri = null;

    @JsonProperty("created")
    @Column(name = "CREATED")
    private LocalDateTime created = null;

    @JsonProperty("owner")
    @Column(name = "OWNER")
    private Long owner = null;

    @JsonProperty("ip")
    @Column(name = "IP")
    private String ip = null;

    @JsonProperty("country")
    @Column(name = "COUNTRY")
    private String country = null;

    @JsonProperty("lastCheckSafeDate")
    @Column(name = "LASTCHECKSAFEDATE")
    private LocalDateTime lastCheckSafeDate = null;

    @JsonProperty("safe")
    @Column(name = "SAFE")
    private Boolean safe = null;

    @JsonProperty("lastCheckAvailableDate")
    @Column(name = "LASTCHECKAVAILABLEDATE")
    private LocalDateTime lastCheckAvailableDate = null;

    @JsonProperty("available")
    @Column(name = "AVAILABLE")
    private Boolean available = null;

    public ShortURL() {
    }

    public ShortURL(String hash, String target, URI uri, LocalDateTime created,
                    Long owner, String ip, String country, LocalDateTime lastCheckSafeDate,
                    Boolean safe, LocalDateTime lastCheckAvailableDate, Boolean available) {
        this.hash = hash;
        this.target = target;
        this.uri = uri;
        this.created = created;
        this.owner = owner;
        this.ip = ip;
        this.country = country;
        this.lastCheckSafeDate = lastCheckSafeDate;
        this.safe = safe;
        this.lastCheckAvailableDate = lastCheckAvailableDate;
        this.available = available;
    }

    /**
     * Get hash
     *
     * @return hash
     **/
    @ApiModelProperty(value = "")
    public String getHash() {
        return hash;
    }

    /**
     * Get target
     *
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
     *
     * @return uri
     **/
    @ApiModelProperty(value = "")
    public URI getUri() {
        return uri;
    }

    /**
     * Get created
     *
     * @return created
     **/
    @ApiModelProperty(value = "")
    public LocalDateTime getCreated() {
        return created;
    }

    /**
     * Get owner
     *
     * @return owner
     **/
    @ApiModelProperty(value = "")
    public Long getOwner() {
        return owner;
    }

    /**
     * Get ip
     *
     * @return ip
     **/
    @ApiModelProperty(value = "")
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * Get country
     *
     * @return country
     **/
    @ApiModelProperty(value = "")
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Get lastCheckSafeDate
     *
     * @return lastCheckSafeDate
     **/
    @ApiModelProperty(value = "")
    public LocalDateTime getLastCheckSafeDate() {
        return lastCheckSafeDate;
    }

    public void setLastCheckSafeDate(LocalDateTime lastCheckSafeDate) {
        this.lastCheckSafeDate = lastCheckSafeDate;
    }

    /**
     * Get safe
     *
     * @return safe
     **/
    @ApiModelProperty(value = "")
    public Boolean getSafe() {
        return safe;
    }

    public void setSafe(Boolean safe) {
        this.safe = safe;
    }

    /**
     * Get lastCheckAvailableDate
     *
     * @return lastCheckAvailableDate
     **/
    @ApiModelProperty(value = "")
    public LocalDateTime getLastCheckAvailableDate() {
        return lastCheckAvailableDate;
    }

    public void setLastCheckAvailableDate(LocalDateTime lastCheckAvailableDate) {
        this.lastCheckAvailableDate = lastCheckAvailableDate;
    }

    /**
     * Get available
     *
     * @return available
     **/
    @ApiModelProperty(value = "")
    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ShortURL shortURL = (ShortURL) o;
        return Objects.equals(this.hash, shortURL.hash) &&
                Objects.equals(this.target, shortURL.target) &&
                Objects.equals(this.uri, shortURL.uri) &&
                Objects.equals(this.created, shortURL.created) &&
                Objects.equals(this.owner, shortURL.owner) &&
                Objects.equals(this.ip, shortURL.ip) &&
                Objects.equals(this.country, shortURL.country) &&
                Objects.equals(this.lastCheckSafeDate, shortURL.lastCheckSafeDate) &&
                Objects.equals(this.safe, shortURL.safe) &&
                Objects.equals(this.lastCheckAvailableDate, shortURL.lastCheckAvailableDate) &&
                Objects.equals(this.available, shortURL.available);
    }

}
