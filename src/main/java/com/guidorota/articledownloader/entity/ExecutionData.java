package com.guidorota.articledownloader.entity;

import java.util.Date;

public final class ExecutionData {

    private final Date startDate;
    private final Date finishDate;
    private final String command;

    public ExecutionData(Date startDate, Date finishDate, String command) {
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.command = command;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public String getCommand() {
        return command;
    }

}
