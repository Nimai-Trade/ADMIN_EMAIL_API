package com.nimai.admin.payload;

/**
 * Created by Sahadeo
 */
public class ApiResponse {
    private Boolean success;
    private String message;
    private String uniqueKey;

    public ApiResponse(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    
    public ApiResponse(Boolean success, String message, String uniqueKey) {
		this.success = success;
		this.message = message;
		this.uniqueKey = uniqueKey;
	}


	public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

	public String getUniqueKey() {
		return uniqueKey;
	}

	public void setUniqueKey(String uniqueKey) {
		this.uniqueKey = uniqueKey;
	}
    
    
}
