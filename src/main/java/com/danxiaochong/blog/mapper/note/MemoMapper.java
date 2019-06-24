package com.danxiaochong.blog.mapper.note;

import com.danxiaochong.blog.pojo.note.Memo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface MemoMapper {
    /**
     * 查看备忘
     * */
    List<Memo> getMemoList(Map<String, Object> params);
    /**
     * 新增备忘
     * */
    Integer addMemo(Map<String, Object> params);
    /**
     * 修改备忘
     * */
    Integer modifyMemo(Map<String, Object> params);
    /**
     * 删除备忘
     * */
    Integer deleteMemo(Integer memoId);
    /**
     * 完成备忘
     * */
    Integer doneMemo(Integer memoId);
}
