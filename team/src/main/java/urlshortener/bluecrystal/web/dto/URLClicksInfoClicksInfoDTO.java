package urlshortener.bluecrystal.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;

/**
 * URLClicksInfoClicksInfoDTO
 */

public class URLClicksInfoClicksInfoDTO {
    @JsonProperty("time")
    private Long time = null;

    @JsonProperty("counter")
    private Integer counter = null;

    public URLClicksInfoClicksInfoDTO(Long time, Integer counter) {
        this.time = time;
        this.counter = counter;
    }

    public URLClicksInfoClicksInfoDTO time(Long time) {
        this.time = time;
        return this;
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

    public void setTime(Long time) {
        this.time = time;
    }

    public URLClicksInfoClicksInfoDTO counter(Integer counter) {
        this.counter = counter;
        return this;
    }

    /**
     * Get counter
     *
     * @return counter
     **/
    @ApiModelProperty(value = "")


    public Integer getCounter() {
        return counter;
    }

    public void setCounter(Integer counter) {
        this.counter = counter;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        URLClicksInfoClicksInfoDTO urLClicksInfoClicksInfo = (URLClicksInfoClicksInfoDTO) o;
        return Objects.equals(this.time, urLClicksInfoClicksInfo.time) &&
                Objects.equals(this.counter, urLClicksInfoClicksInfo.counter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(time, counter);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class URLClicksInfoClicksInfoDTO {\n");

        sb.append("    time: ").append(toIndentedString(time)).append("\n");
        sb.append("    counter: ").append(toIndentedString(counter)).append("\n");
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

