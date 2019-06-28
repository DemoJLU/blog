package com.danxiaochong.blog.controlller;

import com.danxiaochong.blog.controlller.note.MemoController;
import com.danxiaochong.blog.service.UserService;
import com.danxiaochong.blog.service.note.MemoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;

/**
 * 定时任务
 * */
@Component
public class Timing {

    private static final Logger LOG = LoggerFactory.getLogger(MemoController.class);

    static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private MemoService memoService;
    @Autowired
    private UserService userService;

//    @Scheduled(cron = " */30 * * * * *")
//    public void scheduledEmail() throws ParseException {
//        Date dateNow = new Date();
//        Map<String,Object> params = new HashMap<>();
//        params.put("done",1);
//        List<Memo> list = new ArrayList<>();
//        list = memoService.getMemoList(params);
//        for (Memo memo : list) {
//            String deadlineStr = memo.getMemo_end_time();
//            Date deadline = df.parse(deadlineStr);
//            Integer remind = memo.getRemind();
//            if (remind != 0){
//                if (remind == 1){
//
//                }
//            }
//        }
//        SendEmails sendEmails = new SendEmails();
//        String emailAddress = null;
//  //      String emailAddress = userService.getUserById().getEmailAddress();
//        try {
//            sendEmails.SendEmail(emailAddress);
//        } catch (Exception e) {
//            LOG.error("scheduledHello error: ", e);
//        }
//        System.out.println("hello...");
//
//    }


    /**
     * 定时任务——新增备忘
     * @return Integer
     */
//    @Scheduled(cron = "0 0 8 * * *" )
//    public Integer addMemo(@RequestParam Map<String, Object> params) throws ParseException {
//        LOG.info("定时任务——新增备忘");
//        Date date = new Date();
//        String dateStr = df.format(date);
//        Date input_time = df.parse(dateStr);
//        String deadlineStr = (String) params.get("memoDeadline");
//        Date memoDeadline = df.parse(deadlineStr);
//        params.put("memoDeadline",memoDeadline);
//        params.put("inputTime", input_time);
//        int result = memoService.addMemo(params);
//        return result;
//    }

}
