package urlshortener.bluecrystal.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import urlshortener.bluecrystal.persistence.model.SystemCpuUsage;
import urlshortener.bluecrystal.persistence.model.SystemRamUsage;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * SystemInfoDTO
 */

public class SystemInfoDTO {

    @JsonProperty("countriesInfo")
    @Valid
    private java.util.List<URLClicksInfoCountriesInfoDTO> countriesInfo = null;

    @JsonProperty("platformsInfo")
    @Valid
    private java.util.List<URLClicksInfoPlatformsInfoDTO> platformsInfo = null;

    @JsonProperty("browsersInfo")
    @Valid
    private java.util.List<URLClicksInfoBrowsersInfoDTO> browsersInfo = null;

    @JsonProperty("referrersInfo")
    @Valid
    private java.util.List<URLClicksInfoReferrersInfoDTO> referrersInfo = null;

    @JsonProperty("clicksInfo")
    @Valid
    private java.util.List<URLClicksInfoClicksInfoDTO> clicksInfo = null;

    @JsonProperty("memoryUsage")
    private List<SystemCpuUsage> memoryUsage = new ArrayList<SystemCpuUsage>();

    @JsonProperty("ramUsage")
    private List<SystemRamUsage> ramUsage = new ArrayList<SystemRamUsage>();

    @JsonProperty("upTime")
    private String upTime = null;

    @JsonProperty("totalUsers")
    private Long totalUsers = null;

    @JsonProperty("totalUrls")
    private Long totalUrls = null;

    @JsonProperty("totalClicks")
    private Long totalClicks = null;

    public SystemInfoDTO addCountriesInfoItem(URLClicksInfoCountriesInfoDTO countriesInfoItem) {
        if (this.countriesInfo == null) {
            this.countriesInfo = new java.util.ArrayList<>();
        }
        this.countriesInfo.add(countriesInfoItem);
        return this;
    }

    /**
     * Get countriesInfo
     *
     * @return countriesInfo
     **/
    @ApiModelProperty(value = "")
    @Valid
    public java.util.List<URLClicksInfoCountriesInfoDTO> getCountriesInfo() {
        return countriesInfo;
    }

    public SystemInfoDTO addPlatformsInfoItem(URLClicksInfoPlatformsInfoDTO platformsInfoItem) {
        if (this.platformsInfo == null) {
            this.platformsInfo = new java.util.ArrayList<>();
        }
        this.platformsInfo.add(platformsInfoItem);
        return this;
    }

    /**
     * Get platformsInfo
     *
     * @return platformsInfo
     **/
    @ApiModelProperty(value = "")
    @Valid
    public java.util.List<URLClicksInfoPlatformsInfoDTO> getPlatformsInfo() {
        return platformsInfo;
    }

    public SystemInfoDTO addBrowsersInfoItem(URLClicksInfoBrowsersInfoDTO browsersInfoItem) {
        if (this.browsersInfo == null) {
            this.browsersInfo = new java.util.ArrayList<>();
        }
        this.browsersInfo.add(browsersInfoItem);
        return this;
    }

    /**
     * Get browsersInfo
     *
     * @return browsersInfo
     **/
    @ApiModelProperty(value = "")
    @Valid
    public java.util.List<URLClicksInfoBrowsersInfoDTO> getBrowsersInfo() {
        return browsersInfo;
    }

    public SystemInfoDTO addReferrersInfoItem(URLClicksInfoReferrersInfoDTO referrersInfoItem) {
        if (this.referrersInfo == null) {
            this.referrersInfo = new java.util.ArrayList<>();
        }
        this.referrersInfo.add(referrersInfoItem);
        return this;
    }

    /**
     * Get referrersInfo
     *
     * @return referrersInfo
     **/
    @ApiModelProperty(value = "")
    @Valid
    public java.util.List<URLClicksInfoReferrersInfoDTO> getReferrersInfo() {
        return referrersInfo;
    }

    public SystemInfoDTO addClicksInfoItem(URLClicksInfoClicksInfoDTO clicksInfoItem) {
        if (this.clicksInfo == null) {
            this.clicksInfo = new java.util.ArrayList<>();
        }
        this.clicksInfo.add(clicksInfoItem);
        return this;
    }

    /**
     * Get clicksInfo
     *
     * @return clicksInfo
     **/
    @ApiModelProperty(value = "")
    @Valid
    public java.util.List<URLClicksInfoClicksInfoDTO> getClicksInfo() {
        return clicksInfo;
    }

    /**
     * Time that server has been online formatted (YYYY MM DD HH:MM:SS)
     *
     * @return upTime
     **/
    @ApiModelProperty(value = "Time that server has been online formatted (YYYY MM DD HH:MM:SS)")
    public String getUpTime() {
        return upTime;
    }

    public void setUpTime(String upTime) {
        this.upTime = upTime;
    }

    /**
     * Total number of users registered
     *
     * @return totalUsers
     **/
    @ApiModelProperty(value = "Total number of users registered")
    public Long getTotalUsers() {
        return totalUsers;
    }

    public void setTotalUsers(Long totalUsers) {
        this.totalUsers = totalUsers;
    }

    /**
     * Total number of Shorted URLs
     *
     * @return totalUrls
     **/
    @ApiModelProperty(value = "Total number of Shorted URLs")
    public Long getTotalUrls() {
        return totalUrls;
    }

    public void setTotalUrls(Long totalUrls) {
        this.totalUrls = totalUrls;
    }

    /**
     * Total number of clicks done to all Shorted URLs
     *
     * @return totalClicks
     **/
    @ApiModelProperty(value = "Total number of clicks done to all Shorted URLs")
    public Long getTotalClicks() {
        return totalClicks;
    }

    public void setTotalClicks(Long totalClicks) {
        this.totalClicks = totalClicks;
    }

    /**
     * array with memory used in intervals of time
     *
     * @return memoryUsage
     **/
    @ApiModelProperty(value = "array with memory used in intervals of time")
    public List<SystemCpuUsage> getMemoryUsage() {
        return memoryUsage;
    }

    public void setMemoryUsage(List<SystemCpuUsage> memoryUsage) {
        this.memoryUsage = memoryUsage;
    }

    /**
     * array with ram used in intervals of time
     *
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
        SystemInfoDTO systemInfo = (SystemInfoDTO) o;
        return Objects.equals(this.upTime, systemInfo.upTime) &&
                Objects.equals(this.totalUsers, systemInfo.totalUsers) &&
                Objects.equals(this.totalUrls, systemInfo.totalUrls) &&
                Objects.equals(this.totalClicks, systemInfo.totalClicks) &&
                Objects.equals(this.memoryUsage, systemInfo.memoryUsage) &&
                Objects.equals(this.ramUsage, systemInfo.ramUsage) &&
                Objects.equals(this.countriesInfo, systemInfo.countriesInfo) &&
                Objects.equals(this.platformsInfo, systemInfo.platformsInfo) &&
                Objects.equals(this.browsersInfo, systemInfo.browsersInfo) &&
                Objects.equals(this.referrersInfo, systemInfo.referrersInfo) &&
                Objects.equals(this.clicksInfo, systemInfo.clicksInfo);
    }

}
