package com.yxq.vo;

import java.io.Serializable;
import java.util.Set;

public class MasterBean implements Serializable {
	
	private static final long serialVersionUID = -7245616097749403109L;

	private int master_id;
	private String master_name;
	private String master_password;
	private String master_sex;
	private String master_oicq;
	private boolean master_state;
	private Set<ArticleBean> articles;
	private Set<ArticleTypeBean> articleTypes;
	private Set<AuthorReviewBean> authorReview;


	public Set<AuthorReviewBean> getAuthorReview() {
		return authorReview;
	}

	public void setAuthorReview(Set<AuthorReviewBean> authorReview) {
		this.authorReview = authorReview;
	}

	public Set<ArticleTypeBean> getArticleTypes() {
		return articleTypes;
	}

	public void setArticleTypes(Set<ArticleTypeBean> articleTypes) {
		this.articleTypes = articleTypes;
	}

	public Set<ArticleBean> getArticles() {
		return articles;
	}

	public void setArticles(Set<ArticleBean> articles) {
		this.articles = articles;
	}

	public int getMaster_id() {
		return master_id;
	}

	public void setMaster_id(int master_id) {
		this.master_id = master_id;
	}

	public String getMaster_name() {
		return master_name;
	}

	public void setMaster_name(String master_name) {
		this.master_name = master_name;
	}

	public String getMaster_password() {
		return master_password;
	}

	public void setMaster_password(String master_password) {
		this.master_password = master_password;
	}

	public String getMaster_sex() {
		return master_sex;
	}

	public void setMaster_sex(String master_sex) {
		this.master_sex = master_sex;
	}

	public String getMaster_oicq() {
		return master_oicq;
	}

	public void setMaster_oicq(String master_oicq) {
		this.master_oicq = master_oicq;
	}

	public boolean isMaster_state() {
		return master_state;
	}

	public void setMaster_state(boolean master_state) {
		this.master_state = master_state;
	}

}
