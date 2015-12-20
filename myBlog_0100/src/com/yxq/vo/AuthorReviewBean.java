package com.yxq.vo;

import java.io.Serializable;

public class AuthorReviewBean implements Serializable {
	
	private static final long serialVersionUID = -8878665579345529076L;
	
	private int authorReview_id;
	private ReviewBean review;
	private String authorReplyContent = "";
	private String reSdTime="";
	private MasterBean master;
	
	
	public MasterBean getMaster() {
		return master;
	}
	public void setMaster(MasterBean master) {
		this.master = master;
	}
	public String getReSdTime() {
		return reSdTime;
	}
	public void setReSdTime(String reSdTime) {
		this.reSdTime = reSdTime;
	}
	public int getAuthorReview_id() {
		return authorReview_id;
	}
	public void setAuthorReview_id(int authorReviewId) {
		authorReview_id = authorReviewId;
	}
	public ReviewBean getReview() {
		return review;
	}
	public void setReview(ReviewBean review) {
		this.review = review;
	}
	public String getAuthorReplyContent() {
		return authorReplyContent;
	}
	public void setAuthorReplyContent(String authorReplyContent) {
		this.authorReplyContent = authorReplyContent;
	}
	
	
	

	
	
}
