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
     * Get created
     *
     * @return created
     **/
    @ApiModelProperty(value = "")
    public LocalDateTime getCreated() {
        return created;
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

    /**
     * Get browser
     *
     * @return browser
     **/
    @ApiModelProperty(value = "")
    public String getBrowser() {
        return browser;
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

    /**
     * Get ip
     *
     * @return ip
     **/
    @ApiModelProperty(value = "")
    public String getIp() {
        return ip;
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

}
