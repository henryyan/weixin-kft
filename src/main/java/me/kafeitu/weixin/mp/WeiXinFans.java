package me.kafeitu.weixin.mp;

public class WeiXinFans {
    private String fakeId;
	private String nickName;
	private String remarkName;
	private int groupId;

	public String getFakeId() {
		return fakeId;
	}

	public void setFakeId(String fakeId) {
		this.fakeId = fakeId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getRemarkName() {
		return remarkName;
	}

	public void setRemarkName(String remarkName) {
		this.remarkName = remarkName;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	@Override
	public String toString() {
		return "WeiXinFans [fakeId=" + fakeId + ", nickName=" + nickName
				+ ", remarkName=" + remarkName + ", groupId=" + groupId + "]";
	}

}