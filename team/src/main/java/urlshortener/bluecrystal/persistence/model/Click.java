package urlshortener.bluecrystal.persistence.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Click
 */
@Entity
@Table(name="CLICK")
public class Click {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)

    @JsonProperty("id")
    private Long id = null;

    @Column(name = "HASH")
    @JsonProperty("hash")
    private String hash = null;

    @Column(name = "CREATED")
    @JsonProperty("created")
    private LocalDateTime created = null;

    @Column(name = "REFERRER")
    @JsonProperty("referrer")
    private String referrer = null;

    @Column(name = "BROWSER")
    @JsonProperty("browser")
    private String browser = null;

    @Column(name = "PLATFORM")
    @JsonProperty("platform")
    private String platform = null;

    @Column(name = "IP")
    @JsonProperty("ip")
    private String ip = null;

    @Column(name = "COUNTRY")
    @JsonProperty("country")
    private String country = null;

    public Click() {}

    public Click id(Long id) {
        this.id = id;
        return this;
    }

    public Click(String hash, LocalDateTime created, String referrer, String browser,
                 String platform, String ip, String country) {
        this.hash = hash;
        this.created = created;
        this.referrer = referrer;
        this.browser = browser;
        this.platform = platform;
        this.ip = ip;
        this.country = country;
    }

    /**
     * Get id
     *
     * @return id
     **/
    @ApiModelProperty(value = "")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Click hash(String hash) {
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

    public Click created(LocalDateTime created) {
        this.created = created;
        return this;
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

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public Click referrer(String referrer) {
        this.referrer = referrer;
        return this;
    }

    /**
     * Get referrer
     *
     * @return referrer
     **/
    @ApiModelProperty(value = "")
    public String getReferrer() {
        return referrer;
    }

    public void setReferrer(String referrer) {
        this.referrer = referrer;
    }

    public Click browser(String browser) {
        this.browser = browser;
        return this;
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

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public Click platform(String platform) {
        this.platform = platform;
        return this;
    }

    /**
     * Get platform
     *
     * @return platform
     **/
    @ApiModelProperty(value = "")
    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public Click ip(String ip) {
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

    public Click country(String country) {
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


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Click click = (Click) o;
        return Objects.equals(this.id, click.id) &&
                Objects.equals(this.hash, click.hash) &&
                Objects.equals(this.created, click.created) &&
                Objects.equals(this.referrer, click.referrer) &&
                Objects.equals(this.browser, click.browser) &&
                Objects.equals(this.platform, click.platform) &&
                Objects.equals(this.ip, click.ip) &&
                Objects.equals(this.country, click.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, hash, created, referrer, browser, platform, ip, country);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Click {\n");

        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    hash: ").append(toIndentedString(hash)).append("\n");
        sb.append("    created: ").append(toIndentedString(created)).append("\n");
        sb.append("    referrer: ").append(toIndentedString(referrer)).append("\n");
        sb.append("    browser: ").append(toIndentedString(browser)).append("\n");
        sb.append("    platform: ").append(toIndentedString(platform)).append("\n");
        sb.append("    ip: ").append(toIndentedString(ip)).append("\n");
        sb.append("    country: ").append(toIndentedString(country)).append("\n");
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
