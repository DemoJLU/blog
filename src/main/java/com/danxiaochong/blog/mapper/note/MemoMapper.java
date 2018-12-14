package com.danxiaochong.blog.mapper.note;

import com.danxiaochong.blog.pojo.note.Memo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface MemoMapper {
    List<Memo> getMemoList(Map<String, String> params);
}
