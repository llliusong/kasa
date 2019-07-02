package com.pine.kasa.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: ${description}
 * @Author: pine
 * @CreateDate: 2018-09-03 17:54
 */
@Component
@ConfigurationProperties(prefix = "web-config") //接收application.webconfig
public class WebPropertyConfiguration {
    //String类型的一定需要setter来接收属性值；maps, collections, 和 arrays 不需要
    private String rootUploadPath;
    private String parentFolder;
    private String domainName;
    private List<Map<String, String>> listprop1 = new ArrayList<>(); //接收prop1里面的属性值
    private List<String> listprop2 = new ArrayList<>(); //接收prop2里面的属性值
    private Map<String, String> mapprops = new HashMap<>(); //接收prop1里面的属性值
    private Integer suiteId;
    private String appId;
    private String webhook;
    private String workingGroupWebhook;

    public String getRootUploadPath() {
        return rootUploadPath;
    }


    public void setRootUploadPath(String rootUploadPath) {
        this.rootUploadPath = rootUploadPath;
    }

    public String getParentFolder() {
        return parentFolder;
    }

    public void setParentFolder(String parentFolder) {
        this.parentFolder = parentFolder;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public List<Map<String, String>> getListprop1() {
        return listprop1;
    }

    public void setListprop1(List<Map<String, String>> listprop1) {
        this.listprop1 = listprop1;
    }

    public List<String> getListprop2() {
        return listprop2;
    }

    public void setListprop2(List<String> listprop2) {
        this.listprop2 = listprop2;
    }

    public Map<String, String> getMapprops() {
        return mapprops;
    }

    public void setMapprops(Map<String, String> mapprops) {
        this.mapprops = mapprops;
    }

    public Integer getSuiteId() {
        return suiteId;
    }

    public void setSuiteId(Integer suiteId) {
        this.suiteId = suiteId;
    }

    public String getWebhook() {
        return webhook;
    }

    public void setWebhook(String webhook) {
        this.webhook = webhook;
    }

    public String getWorkingGroupWebhook() {
        return workingGroupWebhook;
    }

    public void setWorkingGroupWebhook(String workingGroupWebhook) {
        this.workingGroupWebhook = workingGroupWebhook;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }
}
