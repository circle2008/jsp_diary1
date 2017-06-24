package com.dlmu.circle.model;

import java.util.Date;

/**
 * Created by cf on 2017/2/20.
 */
public class Diary {
    private int diaryId;
    private String title;
    private String content;
    private int typeId=-1;
    private String typeName;
    private Date releasedate;
    private String releaseDateStr;
    private int diaryDateCount;

    public Diary() {
        super();
    }

    public Diary(String title, String content, int typeId) {
        super();
        this.title = title;
        this.content = content;
        this.typeId = typeId;
    }

    public int getDiaryId() {
        return diaryId;
    }

    public void setDiaryId(int diaryId) {
        this.diaryId = diaryId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public Date getReleasedate() {
        return releasedate;
    }

    public void setReleasedate(Date releasedate) {
        this.releasedate = releasedate;
    }

    public String getReleaseDateStr() {
        return releaseDateStr;
    }

    public void setReleaseDateStr(String releaseDateStr) {
        this.releaseDateStr = releaseDateStr;
    }

    public int getDiaryDateCount() {
        return diaryDateCount;
    }

    public void setDiaryDateCount(int diaryDateCount) {
        this.diaryDateCount = diaryDateCount;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
