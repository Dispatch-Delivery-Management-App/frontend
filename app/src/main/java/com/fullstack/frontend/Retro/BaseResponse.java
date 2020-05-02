package com.fullstack.frontend.Retro;

import java.util.List;

public class BaseResponse<T> {
    public T response;
    public int status;
    public String error;

    public T getResponse() {
        return response;
    }

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
