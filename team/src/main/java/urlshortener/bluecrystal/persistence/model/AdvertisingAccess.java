package urlshortener.bluecrystal.persistence.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Click
 */
@Entity
@Table(name="ADVERTISING_ACCESS")
public class AdvertisingAccess {
    @Id
    @Column(name = "ID")
    private String id = null;

    @Column(name = "HASH")
    @JsonProperty("hash")
    private String hash = null;

    @Column(name = "ACCESS")
    @JsonProperty("access")
    private Boolean access = null;

    public AdvertisingAccess() {
    }

    public AdvertisingAccess(String id, String hash, Boolean access) {
        this.id = id;
        this.hash = hash;
        this.access = access;
    }

    /**
     * Get id
     *
     * @return id
     **/
    @ApiModelProperty(value = "")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    /**
     * Get access
     *
     * @return access
     **/
    @ApiModelProperty(value = "")
    public Boolean getAccess() {
        return access;
    }

    public void setAccess(Boolean access) {
        this.access = access;
    }

}
