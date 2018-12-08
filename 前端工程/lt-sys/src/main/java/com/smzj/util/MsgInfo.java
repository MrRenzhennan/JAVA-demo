package com.smzj.util;

public class MsgInfo {

	public static final int AUDITED   = 1;//审核通过
	public static final int NOT_AUDITED   = 2;//审核不通

	public static final String AUDITED_CONTENT   = "亲爱的用户【{0}】，您申请的【{1}】办事项【{2}】审核已通过！";//审核通过或者不通过发送信息信息
	public static final String NOT_AUDITED_CONTENT   = "亲爱的用户【{0}，您申请的【{1}】办事项【{2}】审核未通过！";//审核通过或者不通过发送信息信息

	public static final String ORGED_CONTENT   = "{0}于{1}提交的网上办事“{2}”，即将超过审核的办理时限，请您尽快处理！";

}
