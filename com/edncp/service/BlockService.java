package com.edncp.service;

import java.util.ArrayList;
import java.util.List;

import com.edncp.dao.BlockDao;
import com.edncp.dao.UnitDao;
import com.edncp.vo.BlockBean;
import com.edncp.vo.OperationRecord;
import com.edncp.vo.UnitBean;

// Service是业务逻辑层，下面的这些操作基本上不涉及业务逻辑，所以此处的Service层仅仅起到了传值的作用。
public class BlockService {
	// ==================== 查询全部区块()[返回List] ====================
	public static List<BlockBean> listAllBlock() {
		return new BlockDao().listAllBlock();
	}

	// ==================== 查询一个区块(根据区块id)[返回Block对象] ====================
	public static BlockBean getBlockById(int blockId) {
		return new BlockDao().getBlockById(blockId);
	}

    // ==================== 添加区块(根据BlockBean对象)[返回boolean] ====================
    public static boolean insertBlock(BlockBean blockBean, OperationRecord operationRecord) {
        return new BlockDao().insertBlock(blockBean, operationRecord);
    }

    // ==================== 修改区块(根据区块id)[返回boolean] ====================
	public static boolean updateBlock(BlockBean blockBean, OperationRecord operationRecord) {
		return new BlockDao().updateBlock(blockBean, operationRecord);
	}

	// ==================== 撤销区块(根据区块id)[返回boolean] ====================
	public static boolean revokeBlockById(int blockId, OperationRecord operationRecord) {
		return new BlockDao().revokeBlockById(blockId, operationRecord);
	}
}
