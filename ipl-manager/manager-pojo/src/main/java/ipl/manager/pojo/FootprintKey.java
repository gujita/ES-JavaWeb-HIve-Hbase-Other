package ipl.manager.pojo;

import java.util.Date;

public class FootprintKey {
    private Long userId;

    private String searchContent;

    private Date searchTime;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getSearchContent() {
        return searchContent;
    }

    public void setSearchContent(String searchContent) {
        this.searchContent = searchContent == null ? null : searchContent.trim();
    }

    public Date getSearchTime(){return searchTime;}

    public void setSearchTime(){this.searchTime = searchTime;}
}