package com.danxiaochong.blog.pojo.note;

public class Memo {
    private int id;
    private String memo_end_time;
    private int priority;
    private String matter;
    private String memo_content;
    private String input_persion;
    private String input_time;
    private int status;
    private int remind;

    public int getRemind() {
        return remind;
    }

    public void setRemind(int remind) {
        this.remind = remind;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMemo_end_time() {
        return memo_end_time;
    }

    public void setMemo_end_time(String memo_end_time) {
        this.memo_end_time = memo_end_time;
    }

    public String getInput_time() {
        return input_time;
    }

    public void setInput_time(String input_time) {
        this.input_time = input_time;
    }
}
