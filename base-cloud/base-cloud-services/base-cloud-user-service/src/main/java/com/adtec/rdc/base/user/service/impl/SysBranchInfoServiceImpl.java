package com.adtec.rdc.base.user.service.impl;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.adtec.rdc.base.common.model.vo.SysBranchInfoVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.adtec.rdc.base.common.base.service.MessageQueueService;
import com.adtec.rdc.base.common.constants.CommonConstants;
import com.adtec.rdc.base.common.constants.MqQueueNameConstant;
import com.adtec.rdc.base.common.constants.PubCodeConstants;
import com.adtec.rdc.base.common.easyexcel.EasyExcelListener;
import com.adtec.rdc.base.common.enums.BranchEnums;
import com.adtec.rdc.base.common.exception.ServiceException;
import com.adtec.rdc.base.common.model.bo.TreeNode;
import com.adtec.rdc.base.enums.ErrorCodeEnum;
import com.adtec.rdc.base.user.mapper.SysBranchInfoMapper;
import com.adtec.rdc.base.user.mapper.SysGroupBranchRelMapper;
import com.adtec.rdc.base.user.model.bo.SysBranchTree;
import com.adtec.rdc.base.user.model.excelModel.SysBranchExcelModel;
import com.adtec.rdc.base.user.model.po.SysBranchInfo;
import com.adtec.rdc.base.user.model.po.SysGroupBranchRel;
import com.adtec.rdc.base.user.model.query.SysBranchQuery;
import com.adtec.rdc.base.user.service.SysBranchInfoService;
import com.adtec.rdc.base.user.util.RegexUtil;
import com.adtec.rdc.base.user.util.TreeUtilForBranch;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @author liushp
 * @date 2019-11-28 16:11:47
 */
@Service
public class SysBranchInfoServiceImpl extends ServiceImpl<SysBranchInfoMapper, SysBranchInfo>
		implements SysBranchInfoService {
	@Autowired
	private SysBranchInfoMapper mapper;
	@Autowired
	private SysGroupBranchRelMapper sysGroupBranchRelMapper;
	@Autowired
	private MessageQueueService messageQueueService;
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;


	@Override
	public boolean removeById(Serializable id) {
		SysBranchInfo info = mapper.selectById(id);
		if (mapper.hasChildren(id.toString())) {
			throw new ServiceException(ErrorCodeEnum.BRANCH_HAS_CHILD.getErrorCode(),
					ErrorCodeEnum.BRANCH_HAS_CHILD.getMessage());
		}

		QueryWrapper<SysGroupBranchRel> query = new QueryWrapper<SysGroupBranchRel>();
		query.lambda().eq(SysGroupBranchRel::getBranchNo, id.toString());
		sysGroupBranchRelMapper.delete(query);// 移除用户组机构关系

		Object value = redisTemplate.boundHashOps(CommonConstants.SYSTEM_PUBLIC_CODE).get(PubCodeConstants.ASYNC.ASYNC_FLAG.getCode());
		if (value != null && !PubCodeConstants.ASYNC.ASYNC_FLAG.getValue().equals(String.valueOf(value))) {
			// 发送消息-删除机构
			info.setDelFlag(BranchEnums.DEL_DELETE.getCode());
			messageQueueService.convertAndSend(MqQueueNameConstant.REPORT_BRANCH_QUEUE, info);
		}
		return super.removeById(id);
	}

	@Override
	public List<SysBranchTree> getAllBranchTree() {
		QueryWrapper<SysBranchInfo> query = new QueryWrapper<SysBranchInfo>();
		query.lambda().orderByAsc(true, SysBranchInfo::getBranchName);
		List<SysBranchInfo> list = mapper.selectList(query);
		return TreeUtilForBranch.list2Tree(list, CommonConstants.TREE_ROOT);
	}

	@Override
	public List<TreeNode> getAllBranchTreeNode() {
		QueryWrapper<SysBranchInfo> query = new QueryWrapper<SysBranchInfo>();
		List<SysBranchInfo> list = mapper.selectList(query);
		return TreeUtilForBranch.list2TreeNode(list, CommonConstants.TREE_ROOT);
	}

	@Override
	public List<SysBranchInfo> getAllBranchs() {
		return mapper.selectList(null);
	}

	@Override
	public String importBranch(InputStream inputStream) {
		List<SysBranchInfo> list0 = new ArrayList<SysBranchInfo>();
		List<SysBranchInfo> list1 = new ArrayList<SysBranchInfo>();
		List<SysBranchInfo> allBranchs = getAllBranchs();
		// key:机构号 value:机构名称
		HashMap<String, String> branchNoMap = new HashMap<String, String>(allBranchs.size());
		// key:机构号 value:机构类型
		Map<String, String> branchNoTypeMap = new HashMap<String, String>(allBranchs.size());
		allBranchs.forEach(b -> {
			branchNoMap.put(b.getBranchNo(), b.getBranchName());
			branchNoTypeMap.put(b.getBranchNo(), b.getBranchType());
		});

		EasyExcelListener<SysBranchExcelModel> listener = new EasyExcelListener<SysBranchExcelModel>();
		try {
			EasyExcel.read(inputStream, SysBranchExcelModel.class, listener).sheet().doRead();
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<SysBranchExcelModel> list = listener.getList();
		for (int i = 1, len = list.size(); i <= len; i++) {
			SysBranchExcelModel branch = list.get(i - 1);
			if (StringUtils.isEmpty(branch.getBranchName())) {
				throw new ServiceException(ErrorCodeEnum.BRANCH_NAME_NULL.getErrorCode(),
						"第[" + i + "]行：机构名称不能为空，请检查！");
			}
			if (branchNoMap.containsValue(branch.getBranchName())) {
				throw new ServiceException(ErrorCodeEnum.BRANCH_NAME_RE_NAME.getErrorCode(),
						"第[" + i + "]行：机构名称已存在，请检查！");
			}
			if (StringUtils.isEmpty(branch.getBranchNo()) && !RegexUtil.validateOnlyEngAndNum(branch.getBranchNo())) {
				throw new ServiceException(ErrorCodeEnum.BRANCH_NO_FORMAT.getErrorCode(),
						"第[" + i + "]行：机构号应该为数字、英文或二者组合，请检查！");
			}
			if (branchNoMap.containsKey(branch.getBranchNo())) {
				throw new ServiceException(ErrorCodeEnum.BRANCH_NO_RE_NAME.getErrorCode(),
						"第[" + i + "]行：机构号已存在，请检查！");
			}
			if (StringUtils.isEmpty(branch.getParentBranchNo())) {
				branch.setParentBranchNo(CommonConstants.TREE_ROOT);
			} else if (!RegexUtil.validateEnglish(branch.getParentBranchNo())) {
				throw new ServiceException(ErrorCodeEnum.BRANCH_PARENTNO_FORMAT.getErrorCode(),
						"第[" + i + "]行：父级机构号应该为数字、英文或二者组合，请检查！");
			}

			if (branch.getBranchNo().equals(branch.getParentBranchNo())) {
				throw new ServiceException(ErrorCodeEnum.BRANCH_NOANDUPNO_RE_NAME.getErrorCode(),
						"第[" + i + "]行：机构号和父级机构号相同，请检查！");
			}
			if (StringUtils.isEmpty(branch.getBranchType())) {
				throw new ServiceException(ErrorCodeEnum.BRANCH_TYPE_NULL.getErrorCode(),
						"第[" + i + "]行：机构类型不能为空，请检查！");
			}
			if (BranchEnums.VIR.getDesc().equals(branch.getVirFlag())) {
				branch.setVirFlag(BranchEnums.NOTVIR.getCode());
			} else {
				branch.setVirFlag(BranchEnums.NOTVIR.getCode());
			}
			SysBranchInfo branchInfo = new SysBranchInfo();
			branchInfo.setBranchName(branch.getBranchName());
			branchInfo.setBranchNo(branch.getBranchNo());
			branchInfo.setBranchShortName(branch.getBranchShortName());
			branchInfo.setBranchType(branch.getBranchType());

			branchInfo.setParentBranchNo(branch.getParentBranchNo());
			branchInfo.setVirFlag(branch.getVirFlag());
			branchInfo.setEtFlag(BranchEnums.VIR.getCode());
			list1.add(branchInfo);
			branchNoMap.put(branchInfo.getBranchNo(), branchInfo.getBranchName());
			branchNoTypeMap.put(branchInfo.getBranchNo(), branchInfo.getBranchType());
		}

		List<SysBranchInfo>[] arrList = reBrnachList(list1);
		if (arrList[0].size() > 0) {
			list0 = arrList[0];
		} else {
			if (arrList[1].size() > 0) {
				HashSet<String> idSet = new HashSet<String>();
				HashSet<String> pIdSet = new HashSet<String>();
				for (SysBranchInfo b : arrList[1]) {
					idSet.add(b.getBranchNo());
					pIdSet.add(b.getParentBranchNo());
					if (CommonConstants.TREE_ROOT.equals(b.getParentBranchNo())) {

					} else if (branchNoTypeMap.get(b.getParentBranchNo()) == null) {
						throw new ServiceException(ErrorCodeEnum.BRANCH_PARENTNO_NULL.getErrorCode(),
								"机构号：" + b.getBranchNo() + " 父级机构号不存在，请检查！");
					} else if (!branchNoTypeMap.get(b.getParentBranchNo()).equals(b.getBranchType())) {
						throw new ServiceException(ErrorCodeEnum.BRANCH_TYPE_COMP.getErrorCode(),
								"机构号：" + b.getBranchNo() + " 机构类型和上级机构类型不一致，请检查！");
					}
				}
				mapper.batchInsert(arrList[1]);
			} else {
				throw new ServiceException(ErrorCodeEnum.BRNACH_NULL.getErrorCode(),
						"没有可操作的数据！");
			}
		}
		Object value = redisTemplate.boundHashOps(CommonConstants.SYSTEM_PUBLIC_CODE).get(PubCodeConstants.ASYNC.ASYNC_FLAG.getCode());
		if (value != null && !PubCodeConstants.ASYNC.ASYNC_FLAG.getValue().equals(String.valueOf(value))) {
			// 发送消息--批量同步机构
			messageQueueService.convertAndSend(MqQueueNameConstant.REPORT_BRANCH_BATCH_QUEUE, arrList[1]);
		}

		return "成功上传" + arrList[1].size() + "个机构。";
	}

	/**
	 * 找出excel中的重复机构
	 *
	 * @param list
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<SysBranchInfo>[] reBrnachList(List<SysBranchInfo> list) {
		List<SysBranchInfo>[] lists = new List[2];
		Map<String, SysBranchInfo> map1 = new HashMap<String, SysBranchInfo>();
		List<SysBranchInfo> repeatList = new ArrayList<SysBranchInfo>();// 重复
		List<SysBranchInfo> result = new ArrayList<SysBranchInfo>();
		for (int i = 0, j = list.size(); i < j; i++) {
			if (map1.containsKey(list.get(i).getBranchNo() + list.get(i).getBranchName())) {
				repeatList.add(list.get(i));
			} else {
				map1.put(list.get(i).getBranchNo() + list.get(i).getBranchName(), list.get(i));
				result.add(list.get(i));
			}
		}
		lists[0] = repeatList;
		lists[1] = result;
		return lists;
	}

	@Override
	public boolean save(SysBranchInfo branch) {
		if (!CommonConstants.TREE_ROOT.equals(branch.getParentBranchNo())) {
			SysBranchInfo parent = Optional.ofNullable(mapper.selectById(branch.getParentBranchNo()))
					.orElseThrow(() -> new ServiceException(ErrorCodeEnum.BRANCH_PARENTNO_NULL.getErrorCode(),
							"父级机构无法识别！"));
			if (!parent.getBranchType().equals(branch.getBranchType())) {
				throw new ServiceException(ErrorCodeEnum.BRANCH_TYPE_COMP.getErrorCode(),
						ErrorCodeEnum.BRANCH_TYPE_COMP.getMessage());
			}
		}
		// 判断机构名称
		if (mapper.isSameBranchName(branch.getBranchName()) > 0) {
			throw new ServiceException(ErrorCodeEnum.BRANCH_NAME_RE_NAME.getErrorCode(),
					ErrorCodeEnum.BRANCH_NAME_RE_NAME.getMessage());
		}
		// 判断机构号
		if (mapper.isSameBranchNo(branch.getBranchNo()) > 0) {
			throw new ServiceException(ErrorCodeEnum.BRANCH_NO_RE_NAME.getErrorCode(),
					ErrorCodeEnum.BRANCH_NO_RE_NAME.getMessage());
		}
		branch.setEtFlag(BranchEnums.ETL.getCode());
		boolean ok = super.save(branch);

		Object value = redisTemplate.boundHashOps(CommonConstants.SYSTEM_PUBLIC_CODE).get(PubCodeConstants.ASYNC.ASYNC_FLAG.getCode());
		if (value != null && !PubCodeConstants.ASYNC.ASYNC_FLAG.getValue().equals(String.valueOf(value))) {
			// 发送消息 --更新机构
			branch.setDelFlag(BranchEnums.DEL_NORMAL.getCode());
			messageQueueService.convertAndSend(MqQueueNameConstant.REPORT_BRANCH_QUEUE, branch);
		}
		return ok;
	}

	@Override
	public boolean updateById(SysBranchInfo branch) {
		// 判断机构名称
		if (mapper.isSameBranchNoAndName(branch.getBranchName(), branch.getBranchNo()) > 0) {
			throw new ServiceException(ErrorCodeEnum.BRANCH_NAME_RE_NAME.getErrorCode(),
					ErrorCodeEnum.BRANCH_NAME_RE_NAME.getMessage());
		}
		if (!CommonConstants.TREE_ROOT.equals(branch.getParentBranchNo())) {
			SysBranchInfo parent = Optional.ofNullable(mapper.selectById(branch.getParentBranchNo()))
					.orElseThrow(() -> new ServiceException(ErrorCodeEnum.BRANCH_PARENTNO_NULL.getErrorCode(),
							"父级机构无法识别！"));
			if (!parent.getBranchType().equals(branch.getBranchType())) {
				throw new ServiceException(ErrorCodeEnum.BRANCH_TYPE_COMP.getErrorCode(),
						"修改类型和父机构类型不一致，请重新输入！");
			}
		}
		boolean ok = super.updateById(branch);
		// 发送消息--更新机构信息

		Object value = redisTemplate.boundHashOps(CommonConstants.SYSTEM_PUBLIC_CODE).get(PubCodeConstants.ASYNC.ASYNC_FLAG.getCode());
		if (value != null && !PubCodeConstants.ASYNC.ASYNC_FLAG.getValue().equals(String.valueOf(value))) {
			branch.setDelFlag(BranchEnums.DEL_NORMAL.getCode());
			messageQueueService.convertAndSend(MqQueueNameConstant.REPORT_BRANCH_QUEUE, branch);
		}
		return ok;
	}

	@Override
	public List<TreeNode> treeBranchNosByType(String type) {
		QueryWrapper<SysBranchInfo> query = new QueryWrapper<SysBranchInfo>();
		query.lambda().eq(SysBranchInfo::getBranchType, type);
		List<SysBranchInfo> list = mapper.selectList(query);
		return TreeUtilForBranch.list2TreeNode(list, CommonConstants.TREE_ROOT);
	}

	@Override
	public SysBranchQuery getAllBranchTreeByPage(SysBranchQuery query) {
		mapper.getPageForBranch(query);
		List<SysBranchInfo> list = mapper.selectList(null);
		List<SysBranchTree> setChildren = TreeUtilForBranch.setChildren(query.getRecords(), list);
		query.setRecords(setChildren);
		return query;
	}

	@Override
	public List<SysBranchInfo> fetchBranchs() {
		QueryWrapper<SysBranchInfo> queryWrapper = new QueryWrapper<>();
		queryWrapper.lambda().eq(SysBranchInfo::getParentBranchNo,"-1");
		queryWrapper.lambda().orderByAsc(SysBranchInfo::getBranchNo);
		return mapper.selectList(queryWrapper);
	}

	@Override
	public List<SysBranchInfoVo> selectBatchByIds(List<String> orgVals) {
		List<SysBranchInfo> sysBranchInfos = mapper.selectBatchIds(orgVals);
		List<SysBranchInfoVo> list = new ArrayList<>();
		for (SysBranchInfo sysBranchInfo : sysBranchInfos) {
			SysBranchInfoVo vo = new SysBranchInfoVo();
			BeanUtils.copyProperties(sysBranchInfo,vo);
			list.add(vo);
		}
		return list;
	}
}
