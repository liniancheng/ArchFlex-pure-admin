package com.adtec.rdc.base.enums;

import com.adtec.rdc.base.common.model.bo.ErrorEnum;

/**
 * 
 * @author: dengchf     
 * @date:   2020年3月5日 上午12:26:52   
 * @version V1.0 
 * @Copyright: adtec
 * @Description:    TODO
 * 				<table>
 *              <tr><td>响应码组成：</td><td>4位系统编码+2位模块码+2位功能编码+2位错误编码</td></tr>
 *              <tr><td>系统编码：</td><td>暂定1000</td></tr>
 *              <tr><td>模块编码：</td><td>03</td></tr>
 *              <tr><td>功能编码：</td>
 *              <td>
 *              01-->user：用户 </br>
 *              02-->role：角色；</br>
 *              03-->menu：菜单；</br>
 *              04-->auth：权限；</br>
 *              05-->oper：操作日志；</br>
 *              06-->branch：机构；</br>
 *              07-->group：用户组；</br>
 *              08-->oauth:权限</br>
 *              09-->msg：消息；</br>
 *              11-->db:数据源</br>
 *              12-->file:文件服务器</br>
 *              13-->mail：邮件服务器</br>
 *              14-->task:调度</br>
 *              15-->anno:公告</br>
 *              16-->annoType:公告类型</br>
 *              17-->migrate:迁移</td>
 *              18-->help:帮助</td>
 *              
 *              </tr>
 *              <tr><td>错误编码：</td>
 *              <td>01-->add: 新增异常；</br>
 *              02-->update: 修改异常；</br>
 *              03-->delete: 删除异常；</br>
 *              04-->select: 查询异常； </br>
 *              05-->re_name: 重名；</br>
 *              06-->has_child: 存在子元素；</br>
 *              07-->no_auth: 无权限；</br>
 *              08-->used: 使用中； </br>
 *              09-->null: 参数/实体为空；</br>
 *              10-->client: 服务器io异常；</br>
 *              11-->req: 服务器请求异常；</br>
 *              12-->res: 服务器响应异常；</br>
 *              13-->comp: 两者比较不匹配；</br>
 *              14-->format: 格式校验不正确；</br>
 *              99-->test: 测试不通过；</td>
 *              </tr>
 *              </table>
 * 
 */
public enum ErrorCodeEnum implements ErrorEnum {
	
//	01-->user：用户 </br>
	USER_RE_NAME(1000030105, "用户登录名重复,请检查！"), USER_RE_MOBEL(1000030105, "手机号重复，请检查！"), USER_RE_EMAIL(1000030105, "邮箱重复，请检查！"),
	USER_PASS_NULL(1000030109, "密码为空，请检查！"), USER_NULL(1000030109, "无效的用户信息"), USER_OPASS_COMP(1000030113, "旧密码不正确，请检查！"), 
	USER_CAP_COMP(1000030109,"无效的验证码！"), USER_FORGET_NULL(1000030109, "参数异常，请重试！"), USER_CAP_NULL(1000030109, "验证码无效！"),
	USER_RE_PWD(1000030105, "最近%s次密码不允许重复,请检查！"),
//	02-->role：角色；</br>
	ROLE_RE_NAME(1000030205, "角色名称重复,请检查！"), ROLE_RE_CODE(1000030205, "角色代码重复,请检查！"), 
//	03-->menu：菜单；</br>
	MENU_RE_NAME(1000030305, "资源名称重复,请检查！"), 
	MENU_RE_BTN(1000030305, "按钮资源权限重复,请检查！"),  
	MENU_RE_ROUTE_NAME(1000030305, "路径名称重复,请检查！"),
//	04-->auth：权限；</br>
//	05-->oper：操作日志；</br>
//	06-->branch：机构；</br>
	BRANCH_NO_NULL(1000030609,"机构号为空，请检查！"), BRANCH_NAME_NULL(1000030609,"机构名称为空，请检查！"), BRANCH_TYPE_NULL(1000030609,"机构类型为空，请检查！"),
	BRANCH_NO_RE_NAME(1000030609,"机构名称为空，请检查！"), BRANCH_HAS_CHILD(1000030606,"存在子机构，请检查！"), BRANCH_NAME_RE_NAME(1000030608, "机构名称重复，请检查！"),	
	BRANCH_NO_FORMAT(1000030614,"机构号格式不正确，请检查！"), BRANCH_PARENTNO_FORMAT(1000030614,"上级机构号格式不正确，请检查！"), BRNACH_NULL(1000030609,"机构为空，请检查！"),
	BRANCH_NOANDUPNO_RE_NAME(1000030605,"机构号和上级机构号重复，请检查！"), BRANCH_PARENTNO_NULL(1000030609, "父机构号不存在，请检查！"),
	BRANCH_TYPE_COMP(1000030613, "机构类型和上级机构类型不一致，请检查！"),
//	07-->group：用户组；</br>
	GROUP_RE_NAME(1000030705,"用户组名称重复，请检查！"),
//	08-->oauth:权限</br>
//	09-->msg：消息；</br>
//	11-->db:数据源</br>
	DB_RE_NAME(1000031105,"数据源名称重复"),
	DB_TEST(1000031199,"数据源测试失败"),
	DB_NULL(1000031109,"数据源不存在"),
//	12-->file:文件服务器</br>
//	13-->mail：邮件服务器</br>
	SRV_RE_NAME(1000031305,"服务器名称已存在"),
	TEMP_RE_NAME(1000032105,"模板名称已存在"),
//	14-->task:调度</br>
//	15-->anno:公告</br>
	ANNO_NULL(1000031509,"数据为空，请检查！"),
	ANNO_RE_NAME(1000031505,"公告标题已存在，请检查！"),
	ANNO_NULL_ID(1000031505,"公告id不存在，请检查！"),
	ANNO_ATT_NULL(1000031505,"附件为空，请检查！"),
	ANNO_ATT_RE_NAME(1000031505,"附件名称已存在，请检查！"),
	ANNO_ATT_SAVE(1000031505,"附件保存异常，请检查！"),
//	16-->annoType:公告类型</br>
	ANNO_TYPE_RE_NAME(1000031605,"类型名称重复，请检查！"),
//	17-->migrate:迁移</td>
//  18-->help:帮助</td>
	HELP_NULL(1000030918,"文件为空，请检查！"), HELP_CLIENT(1000031110,"文件过大，请检查！"), HELP_RE_NAME(1000031109,"文件已存在，请检查"),
	HELP_SAVE(1000030901,"保存文件失败！"),
	
//  20-->DICT:数据字典</td>
	DICT_RE_NAME(1000032005, "字典编码已存在,请检查！"),
//  22-->KN:知识库</td>
	KN_RE_NAME(1000032205, "知识库标题已存在,请检查！"),
	KN_TP_RE_NAME(1000032305, "分类名称已存在,请检查！"),
//	24--> LAYOUT:布局
	LAYOUT_UPDATE(1000032402, "自定义布局保存失败，请检查！"),
	LAYOUT_RE_NAME(1000032405, "布局名称已存在，请检查！"),
	LAYOUT_RE_LEVEL(1000032405, "布局权重已存在，请检查！"),
	LAYOUT_NO_AUTH(1000032407, "您无权限修改他人的布局，请检查！"),
	LAYOUT_NULL(1000032409, "自定义布局不存在，请检查！"),
	LAYOUT_NULL_APP(1000032409, "登录名不存在，请检查！"),
	LAYOUT_NULL_USER(1000032409, "所属应用未知，请检查！"),
//	25--> layoutItem: 布局数据项
	LAYOUT_ITEM_RE_NAME(1000032505, "布局数据项已存在，请检查！"),
//	26--> layoutItem: 布局-数据项关系
	LAYOUT_ITEM_REL_REL(1000032605, "布局-数据项关系已存在，请检查！"),
//	27--> layoutRole: 布局-角色关系
	LAYOUT_ROLE_NULL_ROLEID(1000032705, "请选中角色！"),
	LAYOUT_ROLE_NULL_LAYID(1000032705, "请选中布局！"),
	;
	
	private Integer errorCode;
	private String message;

	public Integer getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	ErrorCodeEnum(Integer errorCode, String message) {

		this.errorCode = errorCode;
		this.message = message;
	}

}
