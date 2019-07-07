package com.danxiaochong.blog.pojo.blog;

public class Article {

    private int articleId;
    private String articleUserId;
    private String userName;
    private String articleTitle;
    private String articleContent;
    private int articleViewCount;
    private int articleCommentCount;
    private int articleLikeCount;
    private int articleIsComment;
    private int articleStatus;
    private int articleOrder;
    private String articleUpdateTime;
    private String articleCreateTime;
    private String articleSummary;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public String getArticleUserId() {
        return articleUserId;
    }

    public void setArticleUserId(String articleUserId) {
        this.articleUserId = articleUserId;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public String getArticleContent() {
        return articleContent;
    }

    public void setArticleContent(String articleContent) {
        this.articleContent = articleContent;
    }

    public int getArticleViewCount() {
        return articleViewCount;
    }

    public void setArticleViewCount(int articleViewCount) {
        this.articleViewCount = articleViewCount;
    }

    public int getArticleCommentCount() {
        return articleCommentCount;
    }

    public void setArticleCommentCount(int articleCommentCount) {
        this.articleCommentCount = articleCommentCount;
    }

    public int getArticleLikeCount() {
        return articleLikeCount;
    }

    public void setArticleLikeCount(int articleLikeCount) {
        this.articleLikeCount = articleLikeCount;
    }

    public int getArticleIsComment() {
        return articleIsComment;
    }

    public void setArticleIsComment(int articleIsComment) {
        this.articleIsComment = articleIsComment;
    }

    public int getArticleStatus() {
        return articleStatus;
    }

    public void setArticleStatus(int articleStatus) {
        this.articleStatus = articleStatus;
    }

    public int getArticleOrder() {
        return articleOrder;
    }

    public void setArticleOrder(int articleOrder) {
        this.articleOrder = articleOrder;
    }

    public String getArticleUpdateTime() {
        return articleUpdateTime;
    }

    public void setArticleUpdateTime(String articleUpdateTime) {
        this.articleUpdateTime = articleUpdateTime;
    }

    public String getArticleCreateTime() {
        return articleCreateTime;
    }

    public void setArticleCreateTime(String articleCreateTime) {
        this.articleCreateTime = articleCreateTime;
    }

    public String getArticleSummary() {
        return articleSummary;
    }

    public void setArticleSummary(String articleSummary) {
        this.articleSummary = articleSummary;
    }
}
