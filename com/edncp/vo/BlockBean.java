package com.edncp.vo;

// vo层是实体层

public class BlockBean {
	// 区块属性
	private int blockId;
	private String blockName;
	private String blockDescribe;
	private int blockUndo;
	
	// setter和getter方法
	public int getBlockId() {
		return blockId;
	}
	public void setBlockId(int blockId) {
		this.blockId = blockId;
	}
	public String getBlockName() {
		return blockName;
	}
	public void setBlockName(String blockName) {
		this.blockName = blockName;
	}
	public String getBlockDescribe() {
		return blockDescribe;
	}
	public void setBlockDescribe(String blockDescribe) {
		this.blockDescribe = blockDescribe;
	}
	public int getBlockUndo() {
		return blockUndo;
	}
	public void setBlockUndo(int blockUndo) {
		this.blockUndo = blockUndo;
	}
}
