package com.astgo.fenxiao.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/2/16.
 * 通话记录实体类
 */
public class CallsBean{
    private int callsId;//通话记录Id
    private int callsType;//通话记录类型
    private String callsName;//通话记录名字
    private String callsNum;//通话记录号码
    private Long callsTime;//通话记录时间
    private Long callsDurations;//通话时长
    private List<Integer> callsSameId;//相邻且号码相同的记录
    private boolean callsNewNum;//是否是陌生号码 false:不是 true:是

    public int getCallsId() {
        return callsId;
    }

    public void setCallsId(int callsId) {
        this.callsId = callsId;
    }

    public int getCallsType() {
        return callsType;
    }

    public void setCallsType(int callsType) {
        this.callsType = callsType;
    }

    public String getCallsName() {
        return callsName;
    }

    public void setCallsName(String callsName) {
        this.callsName = callsName;
    }

    public String getCallsNum() {
        return callsNum;
    }

    public void setCallsNum(String callsNum) {
        this.callsNum = callsNum;
    }

    public Long getCallsTime() {
        return callsTime;
    }

    public void setCallsTime(Long callsTime) {
        this.callsTime = callsTime;
    }

    public Long getCallsDurations() {
        return callsDurations;
    }

    public void setCallsDurations(Long callsDurations) {
        this.callsDurations = callsDurations;
    }

    public List<Integer> getCallsSameId() {
        return callsSameId;
    }
    public void addCallsSameId(int sameId){
        if(callsSameId == null){
            callsSameId = new ArrayList<>();//生成列表用来存放相邻且号码相同的记录Id
        }
        callsSameId.add(sameId);
    }

    public boolean isCallsNewNum() {
        return callsNewNum;
    }

    public void setCallsNewNum(boolean callsNewNum) {
        this.callsNewNum = callsNewNum;
    }
}
