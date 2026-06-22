package com.littlelee.base.detect.model.bo;

import lombok.Data;

import java.util.HashMap;

@Data
public class FlaskResponse {

    private String status;
    private String message;
    private DataDTO data;

    @Data
    public static class DataDTO {
        private String inputImg;
        private String outImg;
        private String allTime;
        private String label;
        private String confidence;
        private String startTime;
        private String endTime;
        private HashMap<String, CountItem> count;
    }

    @Data
    public static class CountItem {
        private Integer num;
    }

}
