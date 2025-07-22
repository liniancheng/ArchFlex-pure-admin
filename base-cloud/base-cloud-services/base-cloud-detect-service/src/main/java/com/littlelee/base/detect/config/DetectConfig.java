package com.littlelee.base.detect.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "detect")
public class DetectConfig {

    /**
     * 上传路径
     */
    private static String profile;
    /**
     * 权重路径
     */
    private static String weightsPath;
    /**
     * 检测脚本路径
     */
    private static String scriptPath;

    private static String outputDir;

    public static String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        DetectConfig.profile = profile;
    }

    public static String getWeightsPath() {
        return weightsPath;
    }

    public void setWeightsPath(String weightsPath) {
        DetectConfig.weightsPath = weightsPath;}

    public static String getScriptPath() {
        return scriptPath;
    }

    public void setScriptPath(String scriptPath) {
        DetectConfig.scriptPath = scriptPath;
    }

    public static String getOutputDir() {
        return outputDir;
    }
    public void setOutputDir(String outputDir) {
        DetectConfig.outputDir = outputDir;
    }

}
