package urlshortener.bluecrystal.web.messages;

import com.fasterxml.jackson.annotation.JsonProperty;


public class ApiJsonResponse {
    public static final int ERROR = 1;
    public static final int WARNING = 2;
	public static final int INFO = 3;
	public static final int SUCCESS = 4;
	public static final int TOO_BUSY = 5;

    @JsonProperty("status")
    protected String status;

    public ApiJsonResponse() { }

	public ApiJsonResponse(int code) {
        switch (code) {
            case ERROR:
                setStatus("error");
                break;
            case WARNING:
                setStatus("warning");
                break;
            case INFO:
                setStatus("info");
                break;
            case SUCCESS:
                setStatus("success");
                break;
            case TOO_BUSY:
                setStatus("too busy");
                break;
            default:
                setStatus("unknown");
                break;
        }
    }

	private void setStatus(String status) {
		this.status = status;
	}

}
