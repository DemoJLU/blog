package com.danxiaochong.blog.service.blog;

import com.danxiaochong.blog.pojo.blog.Article;

import java.util.List;
import java.util.Map;

public interface ArticleService {
    /**
     * 获取文章
     * */
    List<Article> getArticleList(Map<String,Object> params);
    /**
     * 编辑
     * */
    int editArticle(Map<String,Object> params);
}
