package com.edncp.dao;

import java.rmi.server.Operation;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.edncp.vo.BlockBean;
import com.edncp.vo.OperationRecord;
import com.edncp.dao.OperationRecordDao;

// Dao层负责和数据库进行交互
// 所有的Dao都要继承BaseDao

public class BlockDao extends BaseDao { 
	// ==================== 查询所有区块[返回List] ====================
	public List<BlockBean> listAllBlock() {
		List<BlockBean> listBlock = new ArrayList<BlockBean>(); // 新建一个List，用来存储多条BlockBean对象
		String sql = "select * from block order by block_id"; // sql语句

		//查询操作
		try {
			getConn(); // 获取数据库连接
			ResultSet rs = executeQuery(sql); // 执行sql语句，返回结果集。
			while (rs.next()) { // 遍历结果集rs
				BlockBean blockBean = new BlockBean(); // new一个BlockBean对象
				// 利用setter方法将从数据库中获取到的值存入blockBean对象。
				// 如果是int型，就用rs.getInt("数据库的字段名")；
				// 如果是String型，就用rs.getString("数据库的字段名")，以此类推。
				blockBean.setBlockId(rs.getInt("block_id")); 
				blockBean.setBlockName(rs.getString("block_name"));
				blockBean.setBlockDescribe(rs.getString("block_describe"));
				blockBean.setBlockUndo(rs.getInt("block_undo"));
				listBlock.add(blockBean); // 把blockBean对象存入List
			}
		} catch (SQLException e) {
			e.printStackTrace(); // 捕获异常并打印
		} finally {
			// 释放操作
			closeResultSet();
			closeStatement();
			closeConnection();
		}

		return listBlock; // 将list传回Service层
	}

	// ==================== 查询一个区块(根据区块id)[返回Block对象] ====================
	public BlockBean getBlockById(int blockId) {
		BlockBean blockBean = new BlockBean();
		String sql = "select * from block where block_id = ?";
		Object[] para = new Object[1]; // new一个参数数组，数组类型为Object，里面存放的就是sql语句里的问号部分，注意顺序和sql语句的问号顺序一致。
		para[0] = blockId; // 此处是根据区块id查询区块，所以传入的参数为blockId，只需要一个参数。

		try {
			getConn();
			ResultSet rs = executeQuery(sql, para);
			while (rs.next()) {
				blockBean.setBlockId(rs.getInt("block_id"));
				blockBean.setBlockName(rs.getString("block_name"));
				blockBean.setBlockDescribe(rs.getString("block_describe"));
				blockBean.setBlockUndo(rs.getInt("block_undo"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 释放操作
			closeResultSet();
			closeStatement();
			closeConnection();
		}

		return blockBean;
	}
	// ==================== 查询一个区块(根据区块名)[返回Block对象] ====================
	public BlockBean getBlockByName(String blockName) {
		BlockBean blockBean = new BlockBean();
		String sql = "select * from block where block_name = ?";
		Object[] para = new Object[1];
		para[0] = blockName;

		try {
			getConn();
			ResultSet rs = executeQuery(sql, para);
			while (rs.next()) {
				blockBean.setBlockId(rs.getInt("block_id"));
				blockBean.setBlockName(rs.getString("block_name"));
				blockBean.setBlockDescribe(rs.getString("block_describe"));
				blockBean.setBlockUndo(rs.getInt("block_undo"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 释放操作
			closeResultSet();
			closeStatement();
			closeConnection();
		}

		return blockBean;
	}
	
	// ==================== 添加区块(根据BlockBean对象)[返回boolean] ====================
	public boolean insertBlock(BlockBean blockBean, OperationRecord operationRecord) {
		boolean insertSuccessFlag = false;
		String sql = "insert into block(block_id, block_name, block_describe) values(?, ?, ?)";
		Object[] para = new Object[3];
		para[0] = blockBean.getBlockId();
		para[1] = blockBean.getBlockName();
		para[2] = blockBean.getBlockDescribe();

		try {
			getConn();
			int affectedRowNumber = executeUpdate(sql, para); // affectedRowNumber是受影响的行数
			if (affectedRowNumber != 0) { // 如果受影响的行数不为0，即插入成功。
				insertSuccessFlag = true;
                new OperationRecordDao().insertOperationRecord(operationRecord); // 插入操作记录
			}
		} catch (Exception e) {
			e.printStackTrace();
		}  finally {
			// 释放操作
			closeResultSet();
			closeStatement();
			closeConnection();
		}

		return insertSuccessFlag;
	}

	// ==================== 修改区块(根据区块)[返回boolean] ====================
	public boolean updateBlock(BlockBean blockBean, OperationRecord operationRecord) {
		boolean updateSuccessFlag = false;
		Object[] para = new Object[4];
		String sql = "update block set block_name=?, block_describe=?, block_undo=? where block_id=? ";
		para[0] = blockBean.getBlockName();
		para[1] = blockBean.getBlockDescribe();
		para[2] = blockBean.getBlockUndo();
		para[3] = blockBean.getBlockId();

		try {
			getConn();
			int affectedRowNumber = executeUpdate(sql, para);
			if (affectedRowNumber != 0) {
				updateSuccessFlag = true;
                new OperationRecordDao().insertOperationRecord(operationRecord); // 插入操作记录
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 释放操作
			closeResultSet();
			closeStatement();
			closeConnection();
		}

		return updateSuccessFlag;
	}
	
	// ==================== 删除区块(根据区块id)[返回boolean] ====================
	public boolean revokeBlockById(int blockId, OperationRecord operationRecord) {
		boolean deleteSuccessFlag = false;
		Object[] para = new Object[1];
		String sql = "update block set block_undo = 0 where block_id = ?";
		para[0] = blockId;

		try {
			getConn();
			int affectedRowNumber = executeUpdate(sql, para);
			if (affectedRowNumber == 1) {
				deleteSuccessFlag = true;
                new OperationRecordDao().insertOperationRecord(operationRecord); // 插入操作记录
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 释放操作
			closeResultSet();
			closeStatement();
			closeConnection();
		}

		return deleteSuccessFlag;
	}
}