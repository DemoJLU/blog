package com.danxiaochong.blog.controlller.blog;

import com.danxiaochong.blog.pojo.blog.Article;
import com.danxiaochong.blog.pojo.system.User;
import com.danxiaochong.blog.service.blog.ArticleService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@Controller
@RestController
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
    @ApiOperation(value="文章列表", notes="返回所有文章列表")
    @RequestMapping(value = "articleList", method = { RequestMethod.POST })
    @ResponseBody
    @CrossOrigin(origins = "*")
    public List<Article> articleList(){
        LOG.info("查询文章列表");
        Map<String,Object> params = new HashMap<>();
        List<Article> articles = articleService.getArticleList(params);
        return articles;
    }

    /**
     * 文章列表
     * @return List<Article>
     */
    @ApiOperation("文章具体内容")
    @RequestMapping(value = "articleDetail", method = { RequestMethod.POST})
    @ResponseBody
    @CrossOrigin(origins = "*")
    public List<Article> articleDetail(@RequestParam Map<String, Object> params){
        LOG.info("查询文章具体内容");
        List<Article> articles = articleService.getArticleList(params);
        return articles;
    }

    /**
    * @description: 编辑文章 接收富文本编辑器的值
    * @param: map
    * @return: int
    * @author: 杨帆
    * @date: 2019-08-05 21:54
    */
    @ApiOperation("编辑文章")
    @RequestMapping(value = "editor", method = { RequestMethod.POST})
    @ResponseBody
    public int editor(@RequestParam Map<String, Object> params, HttpSession httpSession){
        LOG.info("编辑文章");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        User user = (User) httpSession.getAttribute("AUTH_USER");
        String content = (String) params.get("content");
        String article_summary = null;
        if (content.length()>500){
            article_summary = content.substring(0,500);
        }else {
            article_summary = content.substring(0,content.length()-1);
        }
        Date date = new Date();
        String article_create_time = df.format(date);
        params.put("article_create_time",article_create_time);
        if (user!=null){
            params.put("user",user.getUser_id());
        }else {
            params.put("user","yf");
        }
        params.put("article_summary",article_summary);
        int result  = articleService.editArticle(params);
        return result;
    }
}
