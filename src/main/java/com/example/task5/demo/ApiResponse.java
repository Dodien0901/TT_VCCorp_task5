package com.example.task5.demo;


public class ApiResponse<T> {
    private int status;
    private String message;
    private int code;
    private T data;

    public ApiResponse(int status, String message, int code, T data) {
        this.status = status;
        this.message = message;
        this.code = code;
        this.data = data;
    }

    // Getters
    public int getStatus() { return status; }
    public String getMessage() { return message; }
    public int getCode() { return code; }
    public T getData() { return data; }

    @Override
    public String toString() {
        return String.format(
                "{\n\"status\": %d,\n\"message\": \"%s\",\n\"code\": %d,\n\"data\": %s\n}",
                status, message, code, data == null ? "{}" : data.toString()
        );
    }
}