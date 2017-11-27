package urlshortener.bluecrystal.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.net.URI;
import java.time.LocalDate;
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
    //@GeneratedValue(strategy = GenerationType.TABLE)
    private String hash = null;

    @JsonProperty("target")
    @Column(name = "TARGET")
    private String target = null;

    @JsonProperty("uri")
    //@Transient
    private URI uri = null;

    @JsonProperty("sponsor")
    @Column(name = "SPONSOR")
    private String sponsor = null;

    @JsonProperty("created")
    @Column(name = "CREATED")
    private LocalDate created = null;

    @JsonProperty("owner")
    @Column(name = "OWNER")
    private String owner = null;

    @JsonProperty("mode")
    @Column(name = "MODE")
    private Integer mode = null;

    @JsonProperty("ip")
    @Column(name = "IP")
    private String ip = null;

    @JsonProperty("country")
    @Column(name = "COUNTRY")
    private String country = null;

    @JsonProperty("lastCheckSafeDate")
    @Column(name = "LASTCHECKSAFEDATE")
    private LocalDate lastCheckSafeDate = null;

    @JsonProperty("safe")
    @Column(name = "SAFE")
    private Boolean safe = null;

    @JsonProperty("lastCheckAvailableDate")
    @Column(name = "LASTCHECKAVAILABLEDATE")
    private LocalDate lastCheckAvailableDate = null;

    @JsonProperty("available")
    @Column(name = "AVAILABLE")
    private Boolean available = null;

    public ShortURL() {
    }

    public ShortURL(String hash, String target, URI uri, String sponsor, LocalDate created,
                    String owner, Integer mode, String ip, String country, LocalDate lastCheckSafeDate,
                    Boolean safe, LocalDate lastCheckAvailableDate, Boolean available) {
        this.hash = hash;
        this.target = target;
        this.uri = uri;
        this.sponsor = sponsor;
        this.created = created;
        this.owner = owner;
        this.mode = mode;
        this.ip = ip;
        this.country = country;
        this.lastCheckSafeDate = lastCheckSafeDate;
        this.safe = safe;
        this.lastCheckAvailableDate = lastCheckAvailableDate;
        this.available = available;
    }

    public ShortURL hash(String hash) {
        this.hash = hash;
        return this;
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

    public void setHash(String hash) {
        this.hash = hash;
    }

    public ShortURL target(String target) {
        this.target = target;
        return this;
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

    public ShortURL uri(URI uri) {
        this.uri = uri;
        return this;
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

    public void setUri(URI uri) {
        this.uri = uri;
    }

    public ShortURL sponsor(String sponsor) {
        this.sponsor = sponsor;
        return this;
    }

    /**
     * Get sponsor
     *
     * @return sponsor
     **/
    @ApiModelProperty(value = "")
    public String getSponsor() {
        return sponsor;
    }

    public void setSponsor(String sponsor) {
        this.sponsor = sponsor;
    }

    public ShortURL created(LocalDate created) {
        this.created = created;
        return this;
    }

    /**
     * Get created
     *
     * @return created
     **/
    @ApiModelProperty(value = "")
    public LocalDate getCreated() {
        return created;
    }

    public void setCreated(LocalDate created) {
        this.created = created;
    }

    public ShortURL owner(String owner) {
        this.owner = owner;
        return this;
    }

    /**
     * Get owner
     *
     * @return owner
     **/
    @ApiModelProperty(value = "")
    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public ShortURL mode(Integer mode) {
        this.mode = mode;
        return this;
    }

    /**
     * Get mode
     *
     * @return mode
     **/
    @ApiModelProperty(value = "")
    public Integer getMode() {
        return mode;
    }

    public void setMode(Integer mode) {
        this.mode = mode;
    }

    public ShortURL ip(String ip) {
        this.ip = ip;
        return this;
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

    public ShortURL country(String country) {
        this.country = country;
        return this;
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

    public ShortURL lastCheckSafeDate(LocalDate lastCheckSafeDate) {
        this.lastCheckSafeDate = lastCheckSafeDate;
        return this;
    }

    /**
     * Get lastCheckSafeDate
     *
     * @return lastCheckSafeDate
     **/
    @ApiModelProperty(value = "")
    public LocalDate getLastCheckSafeDate() {
        return lastCheckSafeDate;
    }

    public void setLastCheckSafeDate(LocalDate lastCheckSafeDate) {
        this.lastCheckSafeDate = lastCheckSafeDate;
    }

    public ShortURL safe(Boolean safe) {
        this.safe = safe;
        return this;
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

    public ShortURL available(Boolean available) {
        this.available = available;
        return this;
    }

    public ShortURL lastCheckAvailableDate(LocalDate lastCheckAvailableDate) {
        this.lastCheckAvailableDate = lastCheckAvailableDate;
        return this;
    }

    /**
     * Get lastCheckAvailableDate
     *
     * @return lastCheckAvailableDate
     **/
    @ApiModelProperty(value = "")
    public LocalDate getLastCheckAvailableDate() {
        return lastCheckAvailableDate;
    }

    public void setLastCheckAvailableDate(LocalDate lastCheckAvailableDate) {
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
                Objects.equals(this.sponsor, shortURL.sponsor) &&
                Objects.equals(this.created, shortURL.created) &&
                Objects.equals(this.owner, shortURL.owner) &&
                Objects.equals(this.mode, shortURL.mode) &&
                Objects.equals(this.ip, shortURL.ip) &&
                Objects.equals(this.country, shortURL.country) &&
                Objects.equals(this.lastCheckSafeDate, shortURL.lastCheckSafeDate) &&
                Objects.equals(this.safe, shortURL.safe) &&
                Objects.equals(this.lastCheckAvailableDate, shortURL.lastCheckAvailableDate) &&
                Objects.equals(this.available, shortURL.available);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hash, target, uri, sponsor, created, owner, mode, ip, country,
                lastCheckSafeDate, safe, lastCheckAvailableDate, available);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ShortURL {\n");

        sb.append("    hash: ").append(toIndentedString(hash)).append("\n");
        sb.append("    target: ").append(toIndentedString(target)).append("\n");
        sb.append("    uri: ").append(toIndentedString(uri)).append("\n");
        sb.append("    sponsor: ").append(toIndentedString(sponsor)).append("\n");
        sb.append("    created: ").append(toIndentedString(created)).append("\n");
        sb.append("    owner: ").append(toIndentedString(owner)).append("\n");
        sb.append("    mode: ").append(toIndentedString(mode)).append("\n");
        sb.append("    ip: ").append(toIndentedString(ip)).append("\n");
        sb.append("    country: ").append(toIndentedString(country)).append("\n");
        sb.append("    lastCheckSafeDate: ").append(toIndentedString(lastCheckSafeDate)).append("\n");
        sb.append("    safe: ").append(toIndentedString(safe)).append("\n");
        sb.append("    lastCheckAvailableDate: ").append(toIndentedString(lastCheckAvailableDate)).append("\n");
        sb.append("    available: ").append(toIndentedString(available)).append("\n");
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
