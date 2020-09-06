package com.ssafy.devit.model.common;

public class Common {
	private long commonId;

	public Common(long commonId) {
		super();
		this.commonId = commonId;
	}

	public Common() {
		super();
	}

	public long getCommonId() {
		return commonId;
	}

	public void setCommonId(long commonId) {
		this.commonId = commonId;
	}

	@Override
	public String toString() {
		return "Common [commonId=" + commonId + "]";
	}

}
