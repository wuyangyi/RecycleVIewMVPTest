package com.zz.recycleviewmvptest.bean;

import java.util.List;

/**
 * author: wuyangyi
 * date: 2019-11-20
 */
public class AppVersionBean {

    /**
     * outputType : {"type":"APK"}
     * apkInfo : {"type":"MAIN","splits":[],"versionCode":2,"versionName":"1.1","enabled":true,"outputFile":"app-release.apk","fullName":"release","baseName":"release"}
     * path : app-release.apk
     * properties : {}
     */

    private OutputTypeBean outputType;
    private ApkInfoBean apkInfo;
    private String path;
    private PropertiesBean properties;

    public OutputTypeBean getOutputType() {
        return outputType;
    }

    public void setOutputType(OutputTypeBean outputType) {
        this.outputType = outputType;
    }

    public ApkInfoBean getApkInfo() {
        return apkInfo;
    }

    public void setApkInfo(ApkInfoBean apkInfo) {
        this.apkInfo = apkInfo;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public PropertiesBean getProperties() {
        return properties;
    }

    public void setProperties(PropertiesBean properties) {
        this.properties = properties;
    }

    public static class OutputTypeBean {
        /**
         * type : APK
         */

        private String type;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    public static class ApkInfoBean {
        /**
         * type : MAIN
         * splits : []
         * versionCode : 2
         * versionName : 1.1
         * enabled : true
         * outputFile : app-release.apk
         * fullName : release
         * baseName : release
         */

        private String type;
        private int versionCode;
        private String versionName;
        private boolean enabled;
        private String outputFile;
        private String fullName;
        private String baseName;
        private List<?> splits;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getVersionCode() {
            return versionCode;
        }

        public void setVersionCode(int versionCode) {
            this.versionCode = versionCode;
        }

        public String getVersionName() {
            return versionName;
        }

        public void setVersionName(String versionName) {
            this.versionName = versionName;
        }

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public String getOutputFile() {
            return outputFile;
        }

        public void setOutputFile(String outputFile) {
            this.outputFile = outputFile;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public String getBaseName() {
            return baseName;
        }

        public void setBaseName(String baseName) {
            this.baseName = baseName;
        }

        public List<?> getSplits() {
            return splits;
        }

        public void setSplits(List<?> splits) {
            this.splits = splits;
        }
    }

    public static class PropertiesBean {
    }
}
