package com.danxiaochong.blog.pojo.note;

import java.util.Date;

public class Memo {
    private int id;
    private int memo_id;
    private Date memo_start_time;
    private  Date memo_end_time;
    private int priority;
    private String matter;
    private String memo_content;
    private String input_persion;
    private Date nput_time;
    private int status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMemo_id() {
        return memo_id;
    }

    public void setMemo_id(int memo_id) {
        this.memo_id = memo_id;
    }

    public Date getMemo_start_time() {
        return memo_start_time;
    }

    public void setMemo_start_time(Date memo_start_time) {
        this.memo_start_time = memo_start_time;
    }

    public Date getMemo_end_time() {
        return memo_end_time;
    }

    public void setMemo_end_time(Date memo_end_time) {
        this.memo_end_time = memo_end_time;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getMatter() {
        return matter;
    }

    public void setMatter(String matter) {
        this.matter = matter;
    }

    public String getMemo_content() {
        return memo_content;
    }

    public void setMemo_content(String memo_content) {
        this.memo_content = memo_content;
    }

    public String getInput_persion() {
        return input_persion;
    }

    public void setInput_persion(String input_persion) {
        this.input_persion = input_persion;
    }

    public Date getNput_time() {
        return nput_time;
    }

    public void setNput_time(Date nput_time) {
        this.nput_time = nput_time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
