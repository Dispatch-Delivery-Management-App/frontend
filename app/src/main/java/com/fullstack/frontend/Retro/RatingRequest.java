package com.fullstack.frontend.Retro;

public class RatingRequest {
    public int order_id;
    public int feedback;

    public RatingRequest(int order_id, int feedback) {
        this.order_id = order_id;
        this.feedback = feedback;
    }
}
