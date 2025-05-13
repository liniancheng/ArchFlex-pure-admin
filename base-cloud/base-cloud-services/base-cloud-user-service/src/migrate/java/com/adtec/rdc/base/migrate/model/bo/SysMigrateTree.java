package com.adtec.rdc.base.migrate.model.bo;

import java.util.List;

import com.adtec.rdc.base.migrate.model.po.SysMigrateInfo;

/**
 * 
 * @author dengchf
 * @date: 2019-12-13
 * @description: 资源树
 *
 */
public class SysMigrateTree {
	public static final String ROOT_ID = "-1";
	/**
     * id
     */
    private String migId;

    /**
     * 主键字段
     */
    private String migColId;
    /**
     * 主键字段中文名
     */
    private String migColName;
    /**
     * 自关联结束标识
     */
    private String migEndSign;
    /**
     * 关联表关联字段
     */
    private String migRelId;
    /**
     * 关联类型：0：父->子;1:子->父；2：自关联
     */
    private String migRelType;
    /**
     * 关联表-关联字段解析类型;
     */
    private String migConverType;
    /**
     * 关联表-关联字段提取值
     */
    private String migRelValue;
    /**
     * 表中文名
     */
    private String migTabCname;
    /**
     * 表英文名
     */
    private String migTabName;
    /**
     * 表类型,0:目录；1：实体表；2：关系表；
     */
    private String migType;
    /**
     * 父级id
     */
    private String parentMigId;
	/**
	 * 子节点
	 */
	private List<SysMigrateTree> children;

	public void addChildren(SysMigrateTree tree) {
		this.children.add(tree);
	}
	public String getMigId() {
		return migId;
	}


	public void setMigId(String migId) {
		this.migId = migId;
	}


	public String getMigColId() {
		return migColId;
	}


	public void setMigColId(String migColId) {
		this.migColId = migColId;
	}


	public String getMigColName() {
		return migColName;
	}


	public void setMigColName(String migColName) {
		this.migColName = migColName;
	}


	public String getMigEndSign() {
		return migEndSign;
	}


	public void setMigEndSign(String migEndSign) {
		this.migEndSign = migEndSign;
	}


	public String getMigRelId() {
		return migRelId;
	}


	public void setMigRelId(String migRelId) {
		this.migRelId = migRelId;
	}


	public String getMigRelType() {
		return migRelType;
	}


	public void setMigRelType(String migRelType) {
		this.migRelType = migRelType;
	}


	public String getMigConverType() {
		return migConverType;
	}


	public void setMigConverType(String migConverType) {
		this.migConverType = migConverType;
	}


	public String getMigRelValue() {
		return migRelValue;
	}


	public void setMigRelValue(String migRelValue) {
		this.migRelValue = migRelValue;
	}


	public String getMigTabCname() {
		return migTabCname;
	}


	public void setMigTabCname(String migTabCname) {
		this.migTabCname = migTabCname;
	}


	public String getMigTabName() {
		return migTabName;
	}


	public void setMigTabName(String migTabName) {
		this.migTabName = migTabName;
	}


	public String getMigType() {
		return migType;
	}


	public void setMigType(String migType) {
		this.migType = migType;
	}


	public String getParentMigId() {
		return parentMigId;
	}


	public void setParentMigId(String parentMigId) {
		this.parentMigId = parentMigId;
	}


	public List<SysMigrateTree> getChildren() {
		return children;
	}


	public void setChildren(List<SysMigrateTree> children) {
		this.children = children;
	}


	public SysMigrateTree(String migId, String migColId, String migColName, String migEndSign, String migRelId,
			String migRelType, String migConverType, String migRelValue, String migTabCname, String migTabName,
			String migType, String parentMigId, List<SysMigrateTree> children) {
		super();
		this.migId = migId;
		this.migColId = migColId;
		this.migColName = migColName;
		this.migEndSign = migEndSign;
		this.migRelId = migRelId;
		this.migRelType = migRelType;
		this.migConverType = migConverType;
		this.migRelValue = migRelValue;
		this.migTabCname = migTabCname;
		this.migTabName = migTabName;
		this.migType = migType;
		this.parentMigId = parentMigId;
		this.children = children;
	}
	
	public SysMigrateTree() {
	}

	public SysMigrateTree(SysMigrateInfo info) {
		super();
		this.migId = info.getMigId();
		this.migColId = info.getMigColId();
		this.migColName = info.getMigColName();
		this.migEndSign = info.getMigEndSign();
		this.migRelId = info.getMigRelId();
		this.migRelType = info.getMigRelType();
		this.migConverType = info.getMigConverType();
		this.migRelValue = info.getMigRelValue();
		this.migTabCname = info.getMigTabCname();
		this.migTabName = info.getMigTabName();
		this.migType = info.getMigType();
		this.parentMigId = info.getParentMigId();
	}

}
