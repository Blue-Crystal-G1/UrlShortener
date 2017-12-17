package urlshortener.bluecrystal.web.messages;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Error response
 */
public class ApiErrorResponse extends ApiJsonResponse {

    @JsonProperty("type")
    private String type;

    @JsonProperty("message")
    private String message;

    public ApiErrorResponse(){
        super(ApiJsonResponse.ERROR);
    }

    public ApiErrorResponse(String message){
        super(ApiJsonResponse.ERROR);
        this.message = message;
    }

    public ApiErrorResponse(String message, String type){
        super(ApiJsonResponse.ERROR);
        this.type = type;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() { return type; }

    public void setType(String type) { this.type = type; }
}
