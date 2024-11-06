package com.app.main.altibase.vo;

public class AltibaseVo{
    
    private int idx;
    private String title;


    // Getter, Setter
    public int getIdx() {
        return this.idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    //ToString
    @Override
    public String toString() {
        return "{" +
            " idx='" + getIdx() + "'" +
            ", title='" + getTitle() + "'" +
            "}";
    }

}
