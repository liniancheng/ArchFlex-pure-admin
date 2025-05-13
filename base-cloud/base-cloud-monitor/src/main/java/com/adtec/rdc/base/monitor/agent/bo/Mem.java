package com.adtec.rdc.base.monitor.agent.bo;

/**
 * 內存相关信息
 * 
 * @author JTao
 */
public class Mem implements java.io.Serializable
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -3177834491919948936L;

	/**
     * 内存总量
     */
    private long total;

    /**
     * 已用内存
     */
    private long used;

    /**
     * 剩余内存
     */
    private long free;

    public long getTotal()
    {
        return total;
    }

    public void setTotal(long total)
    {
        this.total = total;
    }

    public long getUsed()
    {
        return used;
    }

    public void setUsed(long used)
    {
        this.used = used;
    }

    public long getFree()
    {
        return free;
    }

    public void setFree(long free)
    {
        this.free = free;
    }

	@Override
	public String toString() {
		return "Mem [total=" + total + ", used=" + used + ", free=" + free + "]";
	}
    
}
