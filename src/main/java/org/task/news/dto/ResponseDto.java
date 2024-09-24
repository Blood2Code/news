package org.task.news.dto;

public class ResponseDto<Т> {

    private Boolean success;
    private String message;
    private Т data;
    private Integer statusCode;

    public ResponseDto() {}

    public ResponseDto(Boolean success, String message, Т data, Integer statusCode) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.statusCode = statusCode;
    }

    public ResponseDto(Boolean success, String message, Integer statusCode) {
        this.success = success;
        this.message = message;
        this.statusCode = statusCode;
    }

    public ResponseDto( String message) {
        this.message = message;
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

    public Т getData() {
        return data;
    }

    public void setData(Т data) {
        this.data = data;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }
}
