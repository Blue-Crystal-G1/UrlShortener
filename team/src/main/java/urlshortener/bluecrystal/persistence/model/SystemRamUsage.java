package urlshortener.bluecrystal.persistence.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * SystemRamUsage
 */
@Entity
@Table(name = "SYSTEM_RAM_USAGE")
public class SystemRamUsage {

    @Id
    @Column(name = "TIME")
    @JsonProperty("time")
    private Long time = null;

    @Column(name = "USAGE")
    @JsonProperty("usage")
    private Double usage = null;

    public SystemRamUsage() {
    }

    public SystemRamUsage(Long time, Double usage) {
        this.time = time;
        this.usage = usage;
    }

    /**
     * Get time
     *
     * @return time
     **/
    @ApiModelProperty(value = "")
    public Long getTime() {
        return time;
    }

    /**
     * Get usage
     *
     * @return usage
     **/
    @ApiModelProperty(value = "")
    public Double getUsage() {
        return usage;
    }

}
