package com.danxiaochong.blog.service.impl.blog;

import com.danxiaochong.blog.mapper.blog.ArticleMapper;
import com.danxiaochong.blog.pojo.blog.Article;
import com.danxiaochong.blog.service.blog.ArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ArticleServiceImpl implements ArticleService {

    private static Logger logger = LoggerFactory.getLogger(ArticleServiceImpl.class);

    @Autowired
    ArticleMapper articleMapper;

    /**
     * 获取文章
     * */
    @Override
    public List<Article> getArticleList(Map<String, Object> params) {
        List<Article> articles = null;
        try {
            articles = articleMapper.getArticleList(params);
        } catch (Exception e) {
            logger.error("getArticleList error",e);
        }
        return articles;
    }

    /**
     * 编辑
     * */
    @Override
    public int editArticle(Map<String, Object> params) {
        int result = articleMapper.editArticle(params);
        return result;
    }
}
