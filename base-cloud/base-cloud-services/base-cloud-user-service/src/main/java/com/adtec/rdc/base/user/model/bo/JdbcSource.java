package com.adtec.rdc.base.user.model.bo;

import lombok.Data;

import java.io.Serializable;

@Data
public class JdbcSource implements Serializable {
    private String driverClass;
    private String url;
    private String username;
    private String password;
}
