package com.edncp.vo;

import javax.servlet.http.HttpSession;
import java.util.Date;

// 这个是操作记录的实体

public class OperationRecord {
    private int id; // 操作记录的id
    private Date operationTime; // 操作记录的时间
    private int operatorId; // 操作人员id
    private String operationContent; // 操作记录的内容

    // 创建一条记录，时间默认是当前时间。
    public static OperationRecord newOperationRecordWithCurrentTime(int operatorId, String operationContent) {
        OperationRecord operationRecord = new OperationRecord();
        operationRecord.operationTime = new Date();
        operationRecord.operatorId = operatorId;
        operationRecord.operationContent = operationContent;

        return operationRecord;
    }


    // 无参构造器
    public OperationRecord() {
    }

    public OperationRecord(int id, Date operationTime, int operatorId, String operationContent) {
        this.id = id;
        this.operationTime = operationTime;
        this.operatorId = operatorId;
        this.operationContent = operationContent;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(Date operationTime) {
        this.operationTime = operationTime;
    }

    public int getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(int operatorId) {
        this.operatorId = operatorId;
    }

    public String getOperationContent() {
        return operationContent;
    }

    public void setOperationContent(String operationContent) {
        this.operationContent = operationContent;
    }
}
