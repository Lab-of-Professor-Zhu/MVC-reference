package com.edncp.dao;

import com.edncp.vo.MyUtil;
import com.edncp.vo.OperationRecord;

public class OperationRecordDao extends BaseDao {
    // ==================== 插入一条操作记录(根据OperationRecord对象) ====================
    public void insertOperationRecord(OperationRecord operationRecord) {
        String sql = "insert into operation(operation_time, operator_id, operation_content) values(?, ?, ?)";
        Object[] para = new Object[3];
        para[0] = MyUtil.dateToStrLong(operationRecord.getOperationTime());
        para[1] = operationRecord.getOperatorId();
        para[2] = operationRecord.getOperationContent();

        try {
            executeUpdate(sql, para);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 释放操作
            closeResultSet();
            closeStatement();
            closeConnection();
        }
    }
}
