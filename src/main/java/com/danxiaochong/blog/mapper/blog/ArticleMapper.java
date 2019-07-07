package com.danxiaochong.blog.mapper.blog;

import com.danxiaochong.blog.pojo.blog.Article;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ArticleMapper {
    /**
     * 获取文章
     * */
    List<Article> getArticleList(Map<String,Object> params);
}
