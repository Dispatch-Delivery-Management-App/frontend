package com.fullstack.frontend.Retro;

public class OnBoardingResponse {
    private int status;
    private Response response;

    public OnBoardingResponse(int status, Response response) {
        this.status = status;
        this.response = response;
    }

    public int getStatus() {
        return status;
    }

    public Response getResponse() {
        return response;
    }
}
