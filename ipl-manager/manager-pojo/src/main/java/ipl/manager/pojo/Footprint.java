package ipl.manager.pojo;

import java.util.Date;

public class Footprint extends FootprintKey {
    private Date searchTime;

    public Date getSearchTime() {
        return searchTime;
    }

    public void setSearchTime(Date searchTime) {
        this.searchTime = searchTime;
    }
}