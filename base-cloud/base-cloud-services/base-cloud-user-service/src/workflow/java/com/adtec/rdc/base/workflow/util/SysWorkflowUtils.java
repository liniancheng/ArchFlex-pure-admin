package com.adtec.rdc.base.workflow.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.adtec.rdc.base.workflow.engine.WorkflowNode;
import com.adtec.rdc.base.workflow.engine.WorkflowNodeOper;
import com.adtec.rdc.base.workflow.feign.bo.WorkflowQuery;
import com.adtec.rdc.base.workflow.feign.bo.WorkflowQueryNode;
import com.adtec.rdc.base.workflow.feign.bo.WorkflowQueryNodeOper;
import com.adtec.rdc.base.workflow.model.po.SysWorkflowInstance;

public class SysWorkflowUtils {
	/**
	 * 获取新版本号
	 */
	public static String getNewVersionNum(String oldVersionNum, String versionType) {
		String[] versions = oldVersionNum.split("[.]");
		String newVersionNum = "";
		if(versions.length == 2) {
			int oldBigNum = Integer.parseInt(versions[0]);
			int oldSmallNum = Integer.parseInt(versions[1]);
			if("big".equals(versionType)) {
				newVersionNum = (oldBigNum+1) + ".0";
			}else if("small".equals(versionType)) {
				newVersionNum = oldBigNum + "." + (oldSmallNum+1);
			}					
		}
		return newVersionNum;
	}

	/**
	 * 返回工作流查询
	 * @param instance
	 * @param workflowNodes
	 * @return
	 */
	public static WorkflowQuery getWorkflowQuery(SysWorkflowInstance instance, List<WorkflowNode> workflowNodes) {
		WorkflowQuery query = new WorkflowQuery();
		query.setInstanceId(instance.getInstanceId());
		query.setInstanceStatus(instance.getInstanceStatus());
		if(workflowNodes != null) {
			List<WorkflowQueryNode> nodes = new ArrayList<WorkflowQueryNode>(workflowNodes.size());
			for(WorkflowNode workflowNode: workflowNodes) {
				WorkflowQueryNode node = new WorkflowQueryNode();
				node.setNodeName(workflowNode.getNodeName());
				node.setNodeStatus(workflowNode.getNodeStatus());
				if(workflowNode.getOpers() != null) {
					List<WorkflowQueryNodeOper> opers = new ArrayList<WorkflowQueryNodeOper>(workflowNode.getOpers().size());
					for(WorkflowNodeOper workflowNodeOper: workflowNode.getOpers()) {
						WorkflowQueryNodeOper oper = new WorkflowQueryNodeOper();
						oper.setLoginName(workflowNodeOper.getLoginName());
						oper.setUserName(workflowNodeOper.getUserName());
						oper.setOperTime(workflowNodeOper.getOperTime());
						oper.setOperStatus(workflowNodeOper.getOperStatus());
						oper.setOperRmk(workflowNodeOper.getOperRmk());
						opers.add(oper);
					}
					node.setOpers(opers);
				}else {
					node.setOpers(new ArrayList<WorkflowQueryNodeOper>(0));
				}
				nodes.add(node);
			}
			query.setNodes(nodes);
		}else {
			query.setNodes(new ArrayList<WorkflowQueryNode>(0));
		}
		return query;
	}

	/**
	 * 查询流程实例状态
	 * @param instances
	 * @param instanceIds
	 * @return
	 */
	public static List<WorkflowQuery> getWorkflowStatus(List<SysWorkflowInstance> instances, List<String> instanceIds) {
		Map<String, SysWorkflowInstance> instanceMap = new HashMap<String, SysWorkflowInstance>(instances.size());
		for(SysWorkflowInstance instance : instances) {
			instanceMap.put(instance.getInstanceId(), instance);
		}
		List<WorkflowQuery> querys = new ArrayList<WorkflowQuery>(instanceIds.size());
		for(String instanceId : instanceIds) {
			SysWorkflowInstance instance = instanceMap.get(instanceId);
			WorkflowQuery query = new WorkflowQuery();
			query.setInstanceId(instanceId);
			if(instance == null) {
				query.setInstanceStatus("-1");//实例不存在
			}else {
				query.setInstanceStatus(instance.getInstanceStatus());
			}
			querys.add(query);
		}
		return querys;
	}
}
