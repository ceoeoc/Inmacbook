package com.example.admin.practice;

import java.util.List;

public class EventItem {
    private String EventId;
    private String StDate;
    private String EndDate;
    private String Hour;
    private String Progress;
    private List<String> MemberCid;

    public String getEventId() {
        return EventId;
    }

    public String getEndDate() {
        return EndDate;
    }

    public String getHour() {
        return Hour;
    }

    public String getStDate() {
        return StDate;
    }

    public List<String> getMemberCid() {
        return MemberCid;
    }

    public String getProgress() {
        return Progress;
    }

    public void setEventId(String eventId) {
        EventId = eventId;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }

    public void setHour(String hour) {
        this.Hour = hour;
    }

    public void setMemberCid(List<String> memberCid) {
        MemberCid = memberCid;
    }

    public void setStDate(String stDate) {
        StDate = stDate;
    }

    public void setProgress(String progress) {
        Progress = progress;
    }
}
