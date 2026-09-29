package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a student service request queued in FIFO order.
 * Member 2 Responsibility: Stack & Queue Implementation.
 */
public class ServiceRequest {
    private final String requestId;
    private final String studentId;
    private final String requestDetails;
    private final String timestamp;

    public ServiceRequest(String requestId, String studentId, String requestDetails) {
        this.requestId = requestId;
        this.studentId = studentId;
        this.requestDetails = requestDetails;
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public String getRequestId() {
        return requestId;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getRequestDetails() {
        return requestDetails;
    }

    public String getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return String.format("[ReqID: %-8s | StudentID: %-10s | Details: %-25s | Time: %s]",
                requestId, studentId, requestDetails, timestamp);
    }
}
