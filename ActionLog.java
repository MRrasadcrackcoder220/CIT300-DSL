package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a log entry for recent user actions, maintained in LIFO order using a Stack.
 * Member 2 Responsibility: Stack & Queue Implementation.
 */
public class ActionLog {
    private final String actionType; // e.g., "ADD_STUDENT", "UPDATE_STUDENT", "DELETE_STUDENT", "PROCESS_REQUEST"
    private final String description;
    private final StudentRecord backupRecord; // Store deleted record backup if needed for history
    private final String timestamp;

    public ActionLog(String actionType, String description) {
        this(actionType, description, null);
    }

    public ActionLog(String actionType, String description, StudentRecord backupRecord) {
        this.actionType = actionType;
        this.description = description;
        this.backupRecord = backupRecord;
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public String getActionType() {
        return actionType;
    }

    public String getDescription() {
        return description;
    }

    public StudentRecord getBackupRecord() {
        return backupRecord;
    }

    public String getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return String.format("[%s] %-16s - %s", timestamp, actionType, description);
    }
}
