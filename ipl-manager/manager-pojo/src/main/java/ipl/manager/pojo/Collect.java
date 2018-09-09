package ipl.manager.pojo;

import java.util.Date;

public class Collect extends CollectKey {
    private Date collTime;

    private String description;

    public Date getCollTime() {
        return collTime;
    }

    public void setCollTime(Date collTime) {
        this.collTime = collTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }
}