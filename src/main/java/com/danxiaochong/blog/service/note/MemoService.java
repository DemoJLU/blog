package com.danxiaochong.blog.service.note;


import com.danxiaochong.blog.pojo.note.Memo;

import java.util.List;
import java.util.Map;

public interface MemoService {
    List<Memo> getMemoList(Map<String, String> params);
}
