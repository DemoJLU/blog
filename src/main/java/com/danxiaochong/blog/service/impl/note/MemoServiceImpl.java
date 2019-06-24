package com.danxiaochong.blog.service.impl.note;

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

    /**
     * 新增备忘
     *
     * @param params*/
    @Override
    public Integer addMemo(Map<String, Object> params){
        int result = memoMapper.addMemo(params);
        return result;
    }
    /**
     * 修改备忘
     *
     * @param params*/
    @Override
    public Integer modifyMemo(Map<String, Object> params) {
        Integer result = null;
        try {
            result = memoMapper.modifyMemo(params);
        } catch (Exception e) {
            logger.error("modifyMemo error: ", e);
        }
        return result;
    }

    /**
     * 删除备忘
     *
     * @param memoId*/
    @Override
    public Integer deleteMemo(Integer memoId) {
        Integer result = 0;
        try{
            result = memoMapper.deleteMemo(memoId);
        }catch (Exception e) {
            logger.error("deleteMemo error: ", e);
        }
        return result;
    }

    /**
     * 完成备忘
     * */
    @Override
    public Integer doneMemo(Integer memoId) {
        Integer result = 0;
        try{
            result = memoMapper.doneMemo(memoId);
        }catch (Exception e) {
            logger.error("doneMemo error: ", e);
        }
        return result;
    }

    /**
     * 查看备忘
     * */
    @Override
    public List<Memo> getMemoList(Map<String, Object> params) {
        List<Memo> list = null;
        try {
            list = memoMapper.getMemoList(params);
        } catch (Exception e) {
            logger.error("getMemoList error: ", e);
        }
        return list;
    }
}
