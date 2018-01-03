package urlshortener.bluecrystal.persistence.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * SystemCpuUsage
 */
@Entity
@Table(name = "SYSTEM_CPU_USAGE")
public class SystemCpuUsage {

    @Id
    @Column(name = "TIME")
    @JsonProperty("time")
    private Long time = null;

    @Column(name = "USAGE")
    @JsonProperty("memoryUsage")
    private Double memoryUsage = null;


    public SystemCpuUsage() {
    }

    public SystemCpuUsage(Long time, Double memoryUsage) {
        this.time = time;
        this.memoryUsage = memoryUsage;
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
     * Get memoryUsage
     *
     * @return memoryUsage
     **/
    @ApiModelProperty(value = "")
    public Double getMemoryUsage() {
        return memoryUsage;
    }

}
