package com.adtec.rdc.base.layout.utils;

import java.util.ArrayList;
import java.util.List;

import com.adtec.rdc.base.layout.enums.LayoutEnums;
import com.adtec.rdc.base.layout.model.po.SysLayoutInfo;
import com.adtec.rdc.base.layout.model.po.SysLayoutItemInfo;

/**
 * @author: dengchf
 * @date: 2020年8月28日
 * @Description: 布局工具类
 * @version V1.0
 * @Copyright: adtec
 */
public class LayoutUtils {

	private static final class LayoutUtilsHelper {
		private static final LayoutUtils INSTANCE = new LayoutUtils();
	}

	public static LayoutUtils getInstance() {
		return LayoutUtilsHelper.INSTANCE;
	}

	private LayoutUtils() {
	}

	/**
	 * 构建默认布局（当表中无布局时，使用）
	 */
	public SysLayoutInfo getDefault() {
		List<SysLayoutItemInfo> list = new ArrayList<SysLayoutItemInfo>();

		SysLayoutItemInfo item0 = new SysLayoutItemInfo();
		item0.setItemName("个人中心").setRelH(7).setRelW(24).setRelX(0).setRelY(0)
				.setItemComp("views/dashboard/modules/BasePerson");
		list.add(item0);

		SysLayoutItemInfo item1 = new SysLayoutItemInfo();
		item1.setItemName("待办任务").setRelH(19).setRelW(12).setRelX(0).setRelY(7)
				.setItemComp("views/dashboard/modules/BaseInstance");
		list.add(item1);

		SysLayoutItemInfo item2 = new SysLayoutItemInfo();
		item2.setItemName("消息公告").setRelH(19).setRelW(12).setRelX(12).setRelY(7)
				.setItemComp("views/dashboard/modules/BaseMessage");
		list.add(item2);

		SysLayoutInfo info = new SysLayoutInfo();
		info.setLayLevel(1).setLayName("系统默认布局").setLayType(LayoutEnums.TYPE_DEFAULT.getCode()).setListItems(list);
		return info;
	}

}
