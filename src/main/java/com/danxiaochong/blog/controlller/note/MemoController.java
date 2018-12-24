package com.danxiaochong.blog.controlller.note;

import com.danxiaochong.blog.common.DataTable;
import com.danxiaochong.blog.pojo.note.Memo;
import com.danxiaochong.blog.service.note.MemoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/memo")
public class MemoController {
    private static final Logger LOG = LoggerFactory.getLogger(MemoController.class);

    static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    private MemoService memoService;

    /**
     * 查询未完成备忘
     * @return DataTable<T>
     */
    @RequestMapping(value = "list", method = { RequestMethod.POST })
    @ResponseBody
    public DataTable<Memo> MemoList(@RequestParam String memo_start_time,String memo_end_time,String input_time, String matter) throws ParseException {
        LOG.info("查询指定时间段任务");
        Map<String, String> params = new HashMap<>();
        params.put("memo_start_time", "2018-08-08");
        params.put("memo_end_time", "2018-12-15");
        params.put("input_time", input_time);
        params.put("matter", "工作");
        DataTable<Memo> ret = new DataTable<>();
        List<Memo> list = new ArrayList<>();
        list = memoService.getMemoList(params);
        ret.setData(list);
        return ret;
    }
    @ResponseBody
    @RequestMapping(value = "file", method = { RequestMethod.POST })
    public Map<String, Object> fileExcelInput(HttpServletRequest request, MultipartFile file) throws IOException {

        return null;
    }

    }
