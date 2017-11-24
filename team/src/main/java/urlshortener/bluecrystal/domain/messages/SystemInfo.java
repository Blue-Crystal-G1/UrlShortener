package urlshortener.bluecrystal.domain.messages;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import urlshortener.bluecrystal.domain.SystemMemoryUsage;
import urlshortener.bluecrystal.domain.SystemRamUsage;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * SystemInfo
 */

public class SystemInfo   {
  @JsonProperty("upTime")
  private String upTime = null;

  @JsonProperty("totalUsers")
  private Integer totalUsers = null;

  @JsonProperty("totalUrls")
  private Long totalUrls = null;

  @JsonProperty("totalClicks")
  private Long totalClicks = null;

  @JsonProperty("lastRedirectionTime")
  private Integer lastRedirectionTime = null;

  @JsonProperty("memoryUsage")
  private List<SystemMemoryUsage> memoryUsage = new ArrayList<SystemMemoryUsage>();

  @JsonProperty("ramUsage")
  private List<SystemRamUsage> ramUsage = new ArrayList<SystemRamUsage>();

  public SystemInfo upTime(String upTime) {
    this.upTime = upTime;
    return this;
  }

   /**
   * Time that server has been online formatted (YYYY MM DD HH:MM:SS)
   * @return upTime
  **/
  @ApiModelProperty(value = "Time that server has been online formatted (YYYY MM DD HH:MM:SS)")
  public String getUpTime() {
    return upTime;
  }

  public void setUpTime(String upTime) {
    this.upTime = upTime;
  }

  public SystemInfo totalUsers(Integer totalUsers) {
    this.totalUsers = totalUsers;
    return this;
  }

   /**
   * Total number of users registered
   * @return totalUsers
  **/
  @ApiModelProperty(value = "Total number of users registered")
  public Integer getTotalUsers() {
    return totalUsers;
  }

  public void setTotalUsers(Integer totalUsers) {
    this.totalUsers = totalUsers;
  }

  public SystemInfo totalUrls(Long totalUrls) {
    this.totalUrls = totalUrls;
    return this;
  }

   /**
   * Total number of Shorted URLs
   * @return totalUrls
  **/
  @ApiModelProperty(value = "Total number of Shorted URLs")
  public Long getTotalUrls() {
    return totalUrls;
  }

  public void setTotalUrls(Long totalUrls) {
    this.totalUrls = totalUrls;
  }

  public SystemInfo totalClicks(Long totalClicks) {
    this.totalClicks = totalClicks;
    return this;
  }

   /**
   * Total number of clicks done to all Shorted URLs
   * @return totalClicks
  **/
  @ApiModelProperty(value = "Total number of clicks done to all Shorted URLs")
  public Long getTotalClicks() {
    return totalClicks;
  }

  public void setTotalClicks(Long totalClicks) {
    this.totalClicks = totalClicks;
  }

  public SystemInfo lastRedirectionTime(Integer lastRedirectionTime) {
    this.lastRedirectionTime = lastRedirectionTime;
    return this;
  }

   /**
   * last redirection time in seconds
   * @return lastRedirectionTime
  **/
  @ApiModelProperty(value = "last redirection time in seconds")
  public Integer getLastRedirectionTime() {
    return lastRedirectionTime;
  }

  public void setLastRedirectionTime(Integer lastRedirectionTime) {
    this.lastRedirectionTime = lastRedirectionTime;
  }

  public SystemInfo memoryUsage(List<SystemMemoryUsage> memoryUsage) {
    this.memoryUsage = memoryUsage;
    return this;
  }

  public SystemInfo addMemoryUsageItem(SystemMemoryUsage memoryUsageItem) {
    this.memoryUsage.add(memoryUsageItem);
    return this;
  }

   /**
   * array with memory used in intervals of time
   * @return memoryUsage
  **/
  @ApiModelProperty(value = "array with memory used in intervals of time")
  public List<SystemMemoryUsage> getMemoryUsage() {
    return memoryUsage;
  }

  public void setMemoryUsage(List<SystemMemoryUsage> memoryUsage) {
    this.memoryUsage = memoryUsage;
  }

  public SystemInfo ramUsage(List<SystemRamUsage> ramUsage) {
    this.ramUsage = ramUsage;
    return this;
  }

  public SystemInfo addRamUsageItem(SystemRamUsage ramUsageItem) {
    this.ramUsage.add(ramUsageItem);
    return this;
  }

   /**
   * array with ram used in intervals of time
   * @return ramUsage
  **/
  @ApiModelProperty(value = "array with ram used in intervals of time")
  public List<SystemRamUsage> getRamUsage() {
    return ramUsage;
  }

  public void setRamUsage(List<SystemRamUsage> ramUsage) {
    this.ramUsage = ramUsage;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SystemInfo systemInfo = (SystemInfo) o;
    return Objects.equals(this.upTime, systemInfo.upTime) &&
        Objects.equals(this.totalUsers, systemInfo.totalUsers) &&
        Objects.equals(this.totalUrls, systemInfo.totalUrls) &&
        Objects.equals(this.totalClicks, systemInfo.totalClicks) &&
        Objects.equals(this.lastRedirectionTime, systemInfo.lastRedirectionTime) &&
        Objects.equals(this.memoryUsage, systemInfo.memoryUsage) &&
        Objects.equals(this.ramUsage, systemInfo.ramUsage);
  }

  @Override
  public int hashCode() {
    return Objects.hash(upTime, totalUsers, totalUrls, totalClicks, lastRedirectionTime, memoryUsage, ramUsage);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SystemInfo {\n");
    
    sb.append("    upTime: ").append(toIndentedString(upTime)).append("\n");
    sb.append("    totalUsers: ").append(toIndentedString(totalUsers)).append("\n");
    sb.append("    totalUrls: ").append(toIndentedString(totalUrls)).append("\n");
    sb.append("    totalClicks: ").append(toIndentedString(totalClicks)).append("\n");
    sb.append("    lastRedirectionTime: ").append(toIndentedString(lastRedirectionTime)).append("\n");
    sb.append("    memoryUsage: ").append(toIndentedString(memoryUsage)).append("\n");
    sb.append("    ramUsage: ").append(toIndentedString(ramUsage)).append("\n");
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

