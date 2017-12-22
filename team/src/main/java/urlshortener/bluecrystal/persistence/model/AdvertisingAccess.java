package urlshortener.bluecrystal.persistence.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AdvertisingAccess click = (AdvertisingAccess) o;
        return Objects.equals(this.id, click.id) &&
                Objects.equals(this.hash, click.hash) &&
                Objects.equals(this.access, click.access);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, hash, access);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class AdvertisingAccess {\n");

        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    hash: ").append(toIndentedString(hash)).append("\n");
        sb.append("    access: ").append(toIndentedString(access)).append("\n");
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
