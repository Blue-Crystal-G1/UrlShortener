package urlshortener.bluecrystal.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;

/**
 * SystemRamUsage
 */

public class SystemRamUsage   {
  @JsonProperty("time")
  private Long time = null;

  @JsonProperty("memoryUsage")
  private Double memoryUsage = null;

  public SystemRamUsage time(Long time) {
    this.time = time;
    return this;
  }

   /**
   * Get time
   * @return time
  **/
  @ApiModelProperty(value = "")
  public Long getTime() {
    return time;
  }

  public void setTime(Long time) {
    this.time = time;
  }

  public SystemRamUsage memoryUsage(Double memoryUsage) {
    this.memoryUsage = memoryUsage;
    return this;
  }

   /**
   * Get memoryUsage
   * @return memoryUsage
  **/
  @ApiModelProperty(value = "")
  public Double getMemoryUsage() {
    return memoryUsage;
  }

  public void setMemoryUsage(Double memoryUsage) {
    this.memoryUsage = memoryUsage;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SystemRamUsage systemRamUsage = (SystemRamUsage) o;
    return Objects.equals(this.time, systemRamUsage.time) &&
        Objects.equals(this.memoryUsage, systemRamUsage.memoryUsage);
  }

  @Override
  public int hashCode() {
    return Objects.hash(time, memoryUsage);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SystemRamUsage {\n");
    
    sb.append("    time: ").append(toIndentedString(time)).append("\n");
    sb.append("    memoryUsage: ").append(toIndentedString(memoryUsage)).append("\n");
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

