package com.yxq.vo;

import java.io.Serializable;
import java.util.Set;

public class ArticleBean implements Serializable {
	
	private static final long serialVersionUID = 3013938398215747034L;
	
	private int article_id;
	private ArticleTypeBean articleType;
	private String title = "";
	private String content = "";
	private String sdTime = "";
	private String createFrom = "";
	private String info = "";
	private Set<ReviewBean> reviews;
	private MasterBean masterBean;
	private int count = 0;

	public int getArticle_id() {
		return article_id;
	}

	public void setArticle_id(int article_id) {
		this.article_id = article_id;
	}

	public ArticleTypeBean getArticleType() {
		return articleType;
	}

	public void setArticleType(ArticleTypeBean articleType) {
		this.articleType = articleType;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSdTime() {
		return sdTime;
	}

	public void setSdTime(String sdTime) {
		this.sdTime = sdTime;
	}

	public String getCreateFrom() {
		return createFrom;
	}

	public void setCreateFrom(String createFrom) {
		this.createFrom = createFrom;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public Set<ReviewBean> getReviews() {
		return reviews;
	}

	public void setReviews(Set<ReviewBean> reviews) {
		this.reviews = reviews;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public MasterBean getMasterBean() {
		return masterBean;
	}

	public void setMasterBean(MasterBean masterBean) {
		this.masterBean = masterBean;
	}

}
