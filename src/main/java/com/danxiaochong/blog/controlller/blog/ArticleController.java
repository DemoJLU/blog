package com.danxiaochong.blog.controlller.blog;

import com.danxiaochong.blog.pojo.blog.Article;
import com.danxiaochong.blog.service.blog.ArticleService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/blog")
public class ArticleController {
    private static final Logger LOG = LoggerFactory.getLogger(ArticleController.class);

    static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    ArticleService articleService;

    /**
     * 文章列表
     * @return List<Article>
     */
    @ApiOperation("文章列表")
    @RequestMapping(value = "articleList", method = { RequestMethod.POST })
    @ResponseBody
    public List<Article> articleList(){
        LOG.info("查询指定备忘");
        Map<String,Object> params = new HashMap<>();
        List<Article> articles = articleService.getArticleList(params);
        return articles;
    }

    /**
     * 文章列表
     * @return List<Article>
     */
    @ApiOperation("文章列表")
    @RequestMapping(value = "articleDetail", method = { RequestMethod.POST})
    @ResponseBody
    public List<Article> articleDetail(@RequestParam Map<String, Object> params){
        LOG.info("查询指定备忘");
        List<Article> articles = articleService.getArticleList(params);
        return articles;
    }
}
