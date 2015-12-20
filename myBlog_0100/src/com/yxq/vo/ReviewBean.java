package com.yxq.vo;

import java.io.Serializable;
import java.util.Set;

public class ReviewBean implements Serializable {
	
	private static final long serialVersionUID = -8878665579345529076L;
	
	private int review_id;
	private ArticleBean article;
	private String reAuthor = "";
	private String reContent = "";
	private String reSdTime = "";
	private Set<AuthorReviewBean> authorReviews;


	public Set<AuthorReviewBean> getAuthorReviews() {
		return authorReviews;
	}

	public void setAuthorReviews(Set<AuthorReviewBean> authorReviews) {
		this.authorReviews = authorReviews;
	}

	public int getReview_id() {
		return review_id;
	}

	public void setReview_id(int review_id) {
		this.review_id = review_id;
	}

	public ArticleBean getArticle() {
		return article;
	}

	public void setArticle(ArticleBean article) {
		this.article = article;
	}

	public String getReAuthor() {
		return reAuthor;
	}

	public void setReAuthor(String reAuthor) {
		this.reAuthor = reAuthor;
	}

	public String getReContent() {
		return reContent;
	}

	public void setReContent(String reContent) {
		this.reContent = reContent;
	}

	public String getReSdTime() {
		return reSdTime;
	}

	public void setReSdTime(String reSdTime) {
		this.reSdTime = reSdTime;
	}

}
