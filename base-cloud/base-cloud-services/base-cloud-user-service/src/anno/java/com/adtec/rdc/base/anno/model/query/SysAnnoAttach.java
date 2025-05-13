package com.adtec.rdc.base.anno.model.query;

import java.io.Serializable;

import lombok.Data;

@Data
public class SysAnnoAttach implements Serializable{
	
	private String uid;
	private String name;
	private String status = "done";
	
}
