package com.adtec.rdc.web.antd.bo;

import lombok.Data;

/**
 * 动态表格列
 * @author JTao
 *
 */
@Data
public class TableColumn {
	/**
     * 列头显示文字
     */
    private String title;
    /**
     * 列数据在数据项中对应的 key，支持 a.b.c 的嵌套写法
     */
    private String dataIndex;
    /**
     * Vue 需要的 key，如果已经设置了唯一的 dataIndex，可以忽略这个属性
     */
    private String key;
    /**
     * 列宽度
     */
    private Integer width;
    /**
     * 设置列内容的对齐方式 'left' | 'right' | 'center'
     */
    private String align;
    /**
     * 超过宽度将自动省略，暂不支持和排序筛选一起使用。设置为 true 时，表格布局将变成 tableLayout="fixed"
     */
    private boolean ellipsis;
    /**
     * 表头列合并,设置为 0 时，不渲染
     */
    private Integer colSpan;
}
