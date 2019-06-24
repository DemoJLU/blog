package com.danxiaochong.blog.controlller.note;

import com.danxiaochong.blog.common.DataTable;
import com.danxiaochong.blog.pojo.note.Memo;
import com.danxiaochong.blog.service.note.MemoService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/memo")
public class MemoController {
    private static final Logger LOG = LoggerFactory.getLogger(MemoController.class);

    static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private MemoService memoService;

    /**
     * 新增备忘
     * @return Integer
     */
    @ApiOperation("新增备忘")
    @RequestMapping(value = "addMemo", method = { RequestMethod.POST })
    @ResponseBody
    public Integer addMemo(@RequestParam Map<String, Object> params) throws ParseException {
        LOG.info("新增备忘");
        Date date = new Date();
        String dateStr = df.format(date);
        Date input_time = df.parse(dateStr);
        String deadlineStr = (String) params.get("memoDeadline");
        Date memoDeadline = df.parse(deadlineStr);
        params.put("memoDeadline",memoDeadline);
        params.put("inputTime", input_time);
        int result = memoService.addMemo(params);
        return result;
    }
    /**
     * 修改备忘
     * @return Integer
     */
    @ApiOperation("修改备忘")
    @RequestMapping(value = "modifyMemo", method = { RequestMethod.POST })
    @ResponseBody
    public Integer modifyMemo(@RequestParam Map<String, Object> params) throws ParseException {
        LOG.info("修改备忘");
        String rankStr = (String) params.get("memoRankModify");
        int rank = Integer.valueOf(rankStr);
        params.put("memoRankModify",rank);
        int result = memoService.modifyMemo(params);
        return result;
    }

    /**
     * 删除备忘信息
     * @return Integer
     */
    @ApiOperation("删除备忘信息")
    @RequestMapping(value = "deleteMemo", method = { RequestMethod.GET })
    @ResponseBody
    public Integer deleteMemo(@RequestParam Integer memoId) throws ParseException {
        LOG.info("删除备忘信息");
        Integer result = memoService.deleteMemo(memoId);
        return result;
    }

    /**
     * 完成备忘
     * @return Integer
     */
    @ApiOperation("完成备忘")
    @RequestMapping(value = "doneMemo", method = { RequestMethod.GET })
    @ResponseBody
    public Integer doneMemo(@RequestParam Integer memoId) throws ParseException {
        LOG.info("完成备忘");
        Integer result = memoService.doneMemo(memoId);
        return result;
    }

    /**
     * 查询指定备忘
     * @return DataTable<T>
     */
    @ApiOperation("查询指定备忘")
    @RequestMapping(value = "list", method = { RequestMethod.POST })
    @ResponseBody
    public DataTable<Memo> MemoList(@RequestParam String deadline,String matter, String done,String persion,String priority)
            throws ParseException {
        LOG.info("查询指定备忘");
        Date date = null;
        if (deadline != ""){
            date = df.parse(deadline);
        }
        Map<String, Object> params = new HashMap<>();
        params.put("deadline", date);
        params.put("matter", matter);
        params.put("done", done);
        params.put("persion", persion);
        params.put("priority", priority);
        DataTable<Memo> ret = new DataTable<>();
        List<Memo> list = new ArrayList<>();
        list = memoService.getMemoList(params);
        ret.setData(list);
        return ret;
    }

}
