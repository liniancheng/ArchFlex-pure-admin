package com.adtec.rdc.base.knowledge.model.query;

import java.io.Serializable;

import lombok.Data;

@Data
public class KnowKnowledgeAttach implements Serializable{
	
	private String uid;
	private String name;
	private String status = "done";
	
}
