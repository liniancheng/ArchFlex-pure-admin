package com.adtec.rdc.base.monitor.agent.bo;

/**
 * JVM相关信息
 * 
 * @author JTao
 */
public class Jvm implements java.io.Serializable
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 5054098709764172582L;

	/**
     * 当前JVM占用的内存总数(M)
     */
    private double total;

    /**
     * JVM最大可用内存总数(M)
     */
    private double max;

    /**
     * JVM空闲内存(M)
     */
    private double free;

    /**
     * JDK版本
     */
    private String version;

    /**
     * JDK路径
     */
    private String home;
    
    /**
     * JDK名称
     */
    private String name;

    public double getTotal()
    {
        return total;
    }

    public void setTotal(double total)
    {
        this.total = total;
    }

    public double getMax()
    {
        return max;
    }

    public void setMax(double max)
    {
        this.max = max;
    }

    public double getFree()
    {
        return free;
    }

    public void setFree(double free)
    {
        this.free = free;
    }

    public String getVersion()
    {
        return version;
    }

    public void setVersion(String version)
    {
        this.version = version;
    }

    public String getHome()
    {
        return home;
    }

    public void setHome(String home)
    {
        this.home = home;
    }
    
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Jvm [total=" + total + ", max=" + max + ", free=" + free + ", version=" + version + ", home=" + home
				+ "]";
	}

}
