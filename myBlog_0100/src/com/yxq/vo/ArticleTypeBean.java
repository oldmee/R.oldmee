package com.yxq.vo;

import java.io.Serializable;
import java.util.Set;

public class ArticleTypeBean implements Serializable {
	
	private static final long serialVersionUID = 7601039278609364615L;
	
	private int articleType_id;
	private String typeName = "";
	private String typeInfo = "";
	private Set<ArticleBean> articles;
	private MasterBean masterBean;

	public MasterBean getMasterBean() {
		return masterBean;
	}

	public void setMasterBean(MasterBean masterBean) {
		this.masterBean = masterBean;
	}

	public int getArticleType_id() {
		return articleType_id;
	}

	public void setArticleType_id(int articleType_id) {
		this.articleType_id = articleType_id;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getTypeInfo() {
		return typeInfo;
	}

	public void setTypeInfo(String typeInfo) {
		this.typeInfo = typeInfo;
	}

	public Set<ArticleBean> getArticles() {
		return articles;
	}

	public void setArticles(Set<ArticleBean> articles) {
		this.articles = articles;
	}

}
