package com.adtec.rdc.base.monitor.agent.bo;

import java.util.List;

/**
 * 服务器相关信息
 * 
 * @author JTao
 */
public class Server implements java.io.Serializable
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 9175653752224612657L;

	private String serviceName;
	private long createTime;
	private String createTimeStr;
	private boolean health;
	private Cpu cpu;
	private Mem mem;
	private Jvm jvm;
	private Sys sys;
	private List<SysFile> sysFiles;
    
    public Cpu getCpu()
    {
        return cpu;
    }

    public void setCpu(Cpu cpu)
    {
        this.cpu = cpu;
    }

    public Mem getMem()
    {
        return mem;
    }

    public void setMem(Mem mem)
    {
        this.mem = mem;
    }

    public Jvm getJvm()
    {
        return jvm;
    }

    public void setJvm(Jvm jvm)
    {
        this.jvm = jvm;
    }

    public Sys getSys()
    {
        return sys;
    }

    public void setSys(Sys sys)
    {
        this.sys = sys;
    }

    public List<SysFile> getSysFiles()
    {
        return sysFiles;
    }

    public void setSysFiles(List<SysFile> sysFiles)
    {
        this.sysFiles = sysFiles;
    }
    
    public String getCreateTimeStr() {
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}
    
	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	
	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	
	public boolean isHealth() {
		return health;
	}

	public void setHealth(boolean health) {
		this.health = health;
	}

	@Override
	public String toString() {
		return "Server [service=" + serviceName + ",cpu=" + cpu + ", mem=" + mem + ", jvm=" + jvm + ", sys=" + sys + ", sysFiles=" + sysFiles + "]";
	}
    
}
