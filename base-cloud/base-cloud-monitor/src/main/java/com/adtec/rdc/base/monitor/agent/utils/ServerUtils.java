package com.adtec.rdc.base.monitor.agent.utils;

import java.lang.management.ManagementFactory;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import com.adtec.rdc.base.monitor.agent.bo.Cpu;
import com.adtec.rdc.base.monitor.agent.bo.Jvm;
import com.adtec.rdc.base.monitor.agent.bo.Mem;
import com.adtec.rdc.base.monitor.agent.bo.Server;
import com.adtec.rdc.base.monitor.agent.bo.Sys;
import com.adtec.rdc.base.monitor.agent.bo.SysFile;

import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.CentralProcessor.TickType;
import oshi.software.os.FileSystem;
import oshi.software.os.OSFileStore;
import oshi.software.os.OperatingSystem;
import oshi.util.Util;

/**
 * 监控工具类
 * @author JTao
 *
 */
public class ServerUtils {
	private static final int OSHI_WAIT_SECOND = 1000;

	/**
	 * 获取监控信息
	 * @param serviceName
	 * @return
	 * @throws Exception
	 */
    public static Server monitor(String serviceName) throws Exception
    {
    	SystemInfo si = new SystemInfo();
        HardwareAbstractionLayer hal = si.getHardware();
    	Server server = new Server();
    	server.setCreateTime(System.currentTimeMillis());
		server.setCreateTimeStr(DateUtils.format(new Date(server.getCreateTime()), "yyyy-MM-dd HH:mm:ss"));
		server.setServiceName(serviceName);
		server.setCpu(getCpuInfo(hal.getProcessor()));
		server.setMem(getMemInfo(hal.getMemory()));
		server.setSys(getSysInfo());
		server.setJvm(getJvmInfo());
		server.setSysFiles(getSysFiles(si.getOperatingSystem()));
		return server;
    }
    
    /**
     * 磁盘相关信息
     */
    private static List<SysFile> getSysFiles(OperatingSystem os) {
    	List<SysFile> sysFiles = new LinkedList<SysFile>();
    	FileSystem fileSystem = os.getFileSystem();
        OSFileStore[] fsArray = fileSystem.getFileStores();
        for (OSFileStore fs : fsArray)
        {
            long free = fs.getUsableSpace();
            long total = fs.getTotalSpace();
            long used = total - free;
            SysFile sysFile = new SysFile();
            sysFile.setDirName(fs.getMount());
            sysFile.setSysTypeName(fs.getType());
            sysFile.setTypeName(fs.getName());
            sysFile.setTotal(convertFileSize(total));
            sysFile.setFree(convertFileSize(free));
            sysFile.setUsed(convertFileSize(used));
            sysFile.setUsage(Arith.mul(Arith.div(used, total, 4), 100));
            sysFiles.add(sysFile);
        }
		return sysFiles;
	}

	/**
     * JVM相关信息
     */
    private static Jvm getJvmInfo() {
    	Jvm jvm = new Jvm();
    	Properties props = System.getProperties();
        jvm.setTotal(Runtime.getRuntime().totalMemory());
        jvm.setMax(Runtime.getRuntime().maxMemory());
        jvm.setFree(Runtime.getRuntime().freeMemory());
        jvm.setVersion(props.getProperty("java.version"));
        jvm.setHome(props.getProperty("java.home"));
        jvm.setName(ManagementFactory.getRuntimeMXBean().getVmName());
		return jvm;
	}

	/**
     * 服务器相关信息
     */
    private static Sys getSysInfo() {
    	Sys sys = new Sys();
    	Properties props = System.getProperties();
        sys.setComputerName(IpUtils.getHostName());
        sys.setComputerIp(IpUtils.getHostIp());
        sys.setOsName(props.getProperty("os.name"));
        sys.setOsArch(props.getProperty("os.arch"));
        sys.setUserDir(props.getProperty("user.dir"));
		return sys;
	}

	/**
     * 內存相关信息
     */
    private static Mem getMemInfo(GlobalMemory memory) {
    	Mem mem = new Mem();
    	mem.setTotal(memory.getTotal());
        mem.setUsed(memory.getTotal() - memory.getAvailable());
        mem.setFree(memory.getAvailable());
		return mem;
	}

	/**
     * CPU信息
     */
    private static Cpu getCpuInfo(CentralProcessor processor)
    {
        // CPU信息
        long[] prevTicks = processor.getSystemCpuLoadTicks();
        Util.sleep(OSHI_WAIT_SECOND);
        long[] ticks = processor.getSystemCpuLoadTicks();
        long nice = ticks[TickType.NICE.getIndex()] - prevTicks[TickType.NICE.getIndex()];
        long irq = ticks[TickType.IRQ.getIndex()] - prevTicks[TickType.IRQ.getIndex()];
        long softirq = ticks[TickType.SOFTIRQ.getIndex()] - prevTicks[TickType.SOFTIRQ.getIndex()];
        long steal = ticks[TickType.STEAL.getIndex()] - prevTicks[TickType.STEAL.getIndex()];
        long cSys = ticks[TickType.SYSTEM.getIndex()] - prevTicks[TickType.SYSTEM.getIndex()];
        long user = ticks[TickType.USER.getIndex()] - prevTicks[TickType.USER.getIndex()];
        long iowait = ticks[TickType.IOWAIT.getIndex()] - prevTicks[TickType.IOWAIT.getIndex()];
        long idle = ticks[TickType.IDLE.getIndex()] - prevTicks[TickType.IDLE.getIndex()];
        long totalCpu = user + nice + cSys + idle + iowait + irq + softirq + steal;
        Cpu cpu = new Cpu();
        cpu.setCpuNum(processor.getLogicalProcessorCount());
        cpu.setTotal(totalCpu);
        cpu.setSys(cSys);
        cpu.setUsed(user);
        cpu.setWait(iowait);
        cpu.setFree(idle);
        return cpu;
    }
    
    /**
     * 字节转换
     * 
     * @param size 字节大小
     * @return 转换后值
     */
    private static String convertFileSize(long size)
    {
        long kb = 1024;
        long mb = kb * 1024;
        long gb = mb * 1024;
        if (size >= gb)
        {
            return String.format("%.1f GB", (float) size / gb);
        }
        else if (size >= mb)
        {
            float f = (float) size / mb;
            return String.format(f > 100 ? "%.0f MB" : "%.1f MB", f);
        }
        else if (size >= kb)
        {
            float f = (float) size / kb;
            return String.format(f > 100 ? "%.0f KB" : "%.1f KB", f);
        }
        else
        {
            return String.format("%d B", size);
        }
    }
}
