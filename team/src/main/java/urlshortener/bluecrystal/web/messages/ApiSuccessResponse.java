package urlshortener.bluecrystal.web.messages;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Success response
 */
public class ApiSuccessResponse<T> extends ApiJsonResponse {

    @JsonProperty("data")
    private T data;

    public ApiSuccessResponse(){
        super(ApiJsonResponse.SUCCESS);
    }

    public ApiSuccessResponse(T data){
        super(ApiJsonResponse.SUCCESS);
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
