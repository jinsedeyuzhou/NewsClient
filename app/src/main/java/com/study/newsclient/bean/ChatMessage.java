package com.study.newsclient.bean;


import com.study.newsclient.R;

import java.util.ArrayList;
import java.util.List;

public class ChatMessage
{
	private int icon;
	private String name;
	private String content;
	private String createDate;
	private boolean isComMeg;
	private int code;
	private String text;
	//0 文字 1 url 2 图片
	private int type;

	public final static int RECIEVE_MSG = 0;
	public final static int SEND_MSG = 1;

	public ChatMessage() {
	}

	public ChatMessage(int icon, String name, String content,
					   String createDate, boolean isComMeg)
	{
		this.icon = icon;
		this.name = name;
		this.content = content;
		this.createDate = createDate;
		this.isComMeg = isComMeg;
	}

	public boolean isComMeg()
	{
		return isComMeg;
	}

	public void setComMeg(boolean isComMeg)
	{
		this.isComMeg = isComMeg;
	}

	public String getContent()
	{
		return content;
	}

	public void setContent(String content)
	{
		this.content = content;
	}

	public int getIcon()
	{
		return icon;
	}

	public void setIcon(int icon)
	{
		this.icon = icon;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getCreateDate()
	{
		return createDate;
	}

	public void setCreateDate(String createDate)
	{
		this.createDate = createDate;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Override
	public String toString()
	{
		return "ChatMessage [icon=" + icon + ", name=" + name + ", content="
				+ content + ", createDate=" + createDate +", isComing = "+ isComMeg()+ "]";
	}

	public static List<ChatMessage> MOCK_DATAS = new ArrayList<>();

	static {
		ChatMessage msg = null;
		msg = new ChatMessage(R.drawable.ic_gu, "王昭君", "where are you ",
				null, false);
		MOCK_DATAS.add(msg);
		msg = new ChatMessage(R.drawable.ic_meinv, "西施", "where are you ",
				null, true);
		MOCK_DATAS.add(msg);
		msg = new ChatMessage(R.drawable.ic_gu, "王昭君", "where are you ",
				null, false);
		MOCK_DATAS.add(msg);
		msg = new ChatMessage(R.drawable.ic_meinv, "西施", "where are you ",
				null, true);
		MOCK_DATAS.add(msg);
		msg = new ChatMessage(R.drawable.ic_gu, "王昭君", "where are you ",
				null, false);
		MOCK_DATAS.add(msg);

		msg = new ChatMessage(R.drawable.ic_gu, "王昭君", "where are you ",
				null, false);
		MOCK_DATAS.add(msg);
		msg = new ChatMessage(R.drawable.ic_meinv, "西施", "where are you ",
				null, true);
		MOCK_DATAS.add(msg);
		msg = new ChatMessage(R.drawable.ic_meinv, "王昭君", "where are you ",
				null, false);
		MOCK_DATAS.add(msg);
		msg = new ChatMessage(R.drawable.ic_meinv, "西施", "where are you ",
				null, true);
		MOCK_DATAS.add(msg);
		msg = new ChatMessage(R.drawable.ic_gu, "王昭君", "where are you ",
				null, false);
		MOCK_DATAS.add(msg);
		msg = new ChatMessage(R.drawable.ic_gu, "王昭君", "where are you ",
				null, false);
		MOCK_DATAS.add(msg);
		msg = new ChatMessage(R.drawable.ic_meinv, "西施", "where are you ",
				null, true);
		MOCK_DATAS.add(msg);
		msg = new ChatMessage(R.drawable.ic_gu, "王昭君", "where are you ",
				null, false);
		MOCK_DATAS.add(msg);
		msg = new ChatMessage(R.drawable.ic_meinv, "西施", "where are you ",
				null, true);
		MOCK_DATAS.add(msg);
		msg = new ChatMessage(R.drawable.ic_gu, "王昭君", "where are you ",
				null, false);
		MOCK_DATAS.add(msg);
		msg = new ChatMessage(R.drawable.ic_gu, "王昭君", "where are you ",
				null, false);
		MOCK_DATAS.add(msg);
		msg = new ChatMessage(R.drawable.ic_meinv, "西施", "where are you ",
				null, true);
		MOCK_DATAS.add(msg);
		msg = new ChatMessage(R.drawable.ic_gu, "王昭君", "where are you ",
				null, false);
		MOCK_DATAS.add(msg);
		msg = new ChatMessage(R.drawable.ic_meinv, "西施", "where are you ",
				null, true);
		MOCK_DATAS.add(msg);
		msg = new ChatMessage(R.drawable.ic_gu, "王昭君", "where are you ",
				null, false);
		MOCK_DATAS.add(msg);
		msg = new ChatMessage(R.drawable.ic_gu, "王昭君", "where are you ",
				null, false);
		MOCK_DATAS.add(msg);
		msg = new ChatMessage(R.drawable.ic_meinv, "西施", "where are you ",
				null, true);
		MOCK_DATAS.add(msg);
		msg = new ChatMessage(R.drawable.ic_gu, "王昭君", "where are you ",
				null, false);
		MOCK_DATAS.add(msg);
		msg = new ChatMessage(R.drawable.ic_meinv, "西施", "where are you ",
				null, true);
		MOCK_DATAS.add(msg);
		msg = new ChatMessage(R.drawable.ic_gu, "王昭君", "where are you ",
				null, false);
		MOCK_DATAS.add(msg);
	}


}
