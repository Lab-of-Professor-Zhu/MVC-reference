package com.edncp.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.edncp.service.BlockService;
import com.edncp.service.UnitService;
import com.edncp.vo.BlockBean;

import com.edncp.vo.OperationRecord;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

// controller层里面是Servlet
// 作用是从页面获取值【通过request.getParameter("前台传过来的变量名")】
// 和把后台的值传到前台（我这里是通过json的形式）【out.println(json);】

public class BlockServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public BlockServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		getPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	private void getPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html; charset = utf-8");
		request.setCharacterEncoding("utf-8");

		// 从session中获取用户的信息
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("user_id"); // 获取用户id
		String userName = (String) session.getAttribute("user_name"); // 获取用户名
		
		// 判断前台需要执行什么功能(根据传入的order)
		String order = request.getParameter("order"); // 前台传过来的变量名就叫"order"，变量的值是一个字符串，比如下面的"listAllBlock"。

		//  ==================== 查询全部区块 ====================
		if (order.equals("listAllBlock")) {
			List<BlockBean> listResult = new ArrayList<BlockBean>();

			listResult = BlockService.listAllBlock(); // 从后台返回包含所有区块的List

			String json = JSONArray.fromObject(listResult).toString(); // 把java对象转为json字符串,并输出到页面
			PrintWriter out = response.getWriter();
			out.println(json);
			out.close();
		}

		//  ==================== 查询一个区块  ====================
		if (order.equals("getBlockById")) {
			BlockBean result = new BlockBean();

			int blockId = Integer.parseInt(request.getParameter("blockId"));
			result = BlockService.getBlockById(blockId);

			String json = JSONObject.fromObject(result).toString();
			PrintWriter out = response.getWriter();
			out.println(json);
			out.close();
		}

		// ==================== 添加区块 ====================
		if (order.equals("insertBlock")) {
			BlockBean blockBean = new BlockBean();
			JSONObject result = new JSONObject();

			String blockName = request.getParameter("blockName");
			String blockDescribe = request.getParameter("blockDescribe");
			blockBean.setBlockName(blockName);
			blockBean.setBlockDescribe(blockDescribe);

			int operatorId = Integer.parseInt(userId); // 操作人id
			String operationContent = "人员【" + userName + "】添加了名称为【" + blockName + "】的区块。"; // 操作内容
			boolean insertSuccessFlag = BlockService.insertBlock(blockBean, OperationRecord.newOperationRecordWithCurrentTime(operatorId, operationContent));

			if (insertSuccessFlag == true) {
				result.put("flag", "true");
			} else {
				result.put("flag", "false");
			}

			String json = JSONObject.fromObject(result).toString();
			PrintWriter out = response.getWriter();
			out.println(json);
			out.close();
		}

		// ==================== 修改区块 ====================
		if (order.equals("updateBlock")) {
			BlockBean blockBean = new BlockBean();
			JSONObject result = new JSONObject();


			blockBean.setBlockId(Integer.parseInt(request.getParameter("blockId")));
			blockBean.setBlockName(request.getParameter("blockName"));
			blockBean.setBlockDescribe(request.getParameter("blockDescribe"));
			blockBean.setBlockUndo(Integer.parseInt(request.getParameter("blockUndo")));

			int operatorId = Integer.parseInt(userId); // 操作人id
			String operationContent = "人员【" + userName + "】修改了名称为【" + request.getParameter("blockName") + "】的区块。"; // 操作内容
			boolean updateSuccessFlag = BlockService.updateBlock(blockBean, OperationRecord.newOperationRecordWithCurrentTime(operatorId, operationContent));

			if (updateSuccessFlag == true) {
				result.put("flag", "true");
			} else {
				result.put("flag", "false");
			}

			String json = JSONObject.fromObject(result).toString();
			PrintWriter out = response.getWriter();
			out.println(json);
			out.close();
		}

		// ==================== 撤销区块 ====================
		if (order.equals("revokeBlockById")) {
			JSONObject result = new JSONObject();
			int blockId = Integer.parseInt(request.getParameter("blockId"));

			int operatorId = Integer.parseInt(userId); // 操作人id
			String blockName = BlockService.getBlockById(blockId).getBlockName(); //获取被操作的区块名称
			String operationContent = "人员【" + userName + "】撤销了名称为【" + blockName + "】的区块。"; // 操作内容
			boolean revokeSuccessFlag = BlockService.revokeBlockById(blockId, OperationRecord.newOperationRecordWithCurrentTime(operatorId, operationContent));

			if (revokeSuccessFlag == true) {
				result.put("flag", "true");
			} else {
				result.put("flag", "false");
			}

			String json = JSONObject.fromObject(result).toString();
			PrintWriter out = response.getWriter();
			out.println(json);
			out.close();
		}
	}
}