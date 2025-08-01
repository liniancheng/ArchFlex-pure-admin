package com.littlelee.base.detect.model.bo;

import lombok.Data;

import java.util.Date;

@Data
public class PredictRequest {

    private String startTime;

    private String weight;

    private String username;

    private String inputImg;

    private String inputVideo;

    private String kind;

    private String conf;

}
