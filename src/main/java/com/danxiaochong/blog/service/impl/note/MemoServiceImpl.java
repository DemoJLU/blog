package com.danxiaochong.blog.service.impl.note;

import com.danxiaochong.blog.mapper.UserMapper;
import com.danxiaochong.blog.mapper.note.MemoMapper;
import com.danxiaochong.blog.pojo.note.Memo;
import com.danxiaochong.blog.service.impl.UserServiceImpl;
import com.danxiaochong.blog.service.note.MemoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MemoServiceImpl implements MemoService {
    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private MemoMapper memoMapper;

    @Override
    public List<Memo> getMemoList(Map<String, String> params) {
        List<Memo> list = null;
        try {
            list = memoMapper.getMemoList(params);
        } catch (Exception e) {
            logger.error("getDayShiftPlanList error: ", e);
        }
        return list;
    }
}
