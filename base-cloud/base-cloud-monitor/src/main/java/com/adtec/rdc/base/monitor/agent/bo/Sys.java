package com.adtec.rdc.base.monitor.agent.bo;

/**
 * 系统相关信息
 * 
 * @author JTao
 */
public class Sys implements java.io.Serializable
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -3704961287646785231L;

	/**
     * 服务器名称
     */
    private String computerName;

    /**
     * 服务器Ip
     */
    private String computerIp;

    /**
     * 项目路径
     */
    private String userDir;

    /**
     * 操作系统
     */
    private String osName;

    /**
     * 系统架构
     */
    private String osArch;

    public String getComputerName()
    {
        return computerName;
    }

    public void setComputerName(String computerName)
    {
        this.computerName = computerName;
    }

    public String getComputerIp()
    {
        return computerIp;
    }

    public void setComputerIp(String computerIp)
    {
        this.computerIp = computerIp;
    }

    public String getUserDir()
    {
        return userDir;
    }

    public void setUserDir(String userDir)
    {
        this.userDir = userDir;
    }

    public String getOsName()
    {
        return osName;
    }

    public void setOsName(String osName)
    {
        this.osName = osName;
    }

    public String getOsArch()
    {
        return osArch;
    }

    public void setOsArch(String osArch)
    {
        this.osArch = osArch;
    }

	@Override
	public String toString() {
		return "Sys [computerName=" + computerName + ", computerIp=" + computerIp + ", userDir=" + userDir + ", osName="
				+ osName + ", osArch=" + osArch + "]";
	}
    
}
