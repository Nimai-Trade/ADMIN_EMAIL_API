package com.nimai.admin.payload;

/**
 * Created by Sahadeo
 */
public class ApiResponse {
    private Boolean success;
    private String message;
    private String uniqueKey;
    private int KeyId;
    public ApiResponse(Boolean success, String message) {
        this.success = success;
        this.message = message;
        
    }

    
    public ApiResponse(Boolean success, String message, String uniqueKey) {
		this.success = success;
		this.message = message;
		this.uniqueKey = uniqueKey;
	}

    public ApiResponse(Boolean success, String message, int KeyId) {
		this.success = success;
		this.message = message;
		this.KeyId = KeyId;
	}

    
    
    
	public int getKeyId() {
		return KeyId;
	}


	public void setKeyId(int keyId) {
		KeyId = keyId;
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
