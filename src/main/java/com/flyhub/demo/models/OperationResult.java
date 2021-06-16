package com.flyhub.demo.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 *
 * @author Benjamin E Ndugga
 */
@JsonPropertyOrder({"timestamp", "operationResult", "operationDescription", "operationResultDetails"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OperationResult {


    private int operationResult;
    private String operationDescription;
    private Object operationResultDetails;

    public OperationResult() {

    }

    public OperationResult(int operationResult, String operationDescription) {

        this.operationResult = operationResult;
        this.operationDescription = operationDescription;
    }

    public OperationResult(int operationResult, String operationDescription, Object operationResultDetails) {

        this.operationResult = operationResult;
        this.operationDescription = operationDescription;
        this.operationResultDetails = operationResultDetails;
    }

    public String getOperationDescription() {
        return operationDescription;
    }

    public void setOperationDescription(String operationDescription) {
        this.operationDescription = operationDescription;
    }

    public int getOperationResult() {
        return operationResult;
    }

    public void setOperationResult(int operationResult) {
        this.operationResult = operationResult;
    }

    public Object getOperationResultDetails() {
        return operationResultDetails;
    }

    public void setOperationResultDetails(Object operationResultDetails) {
        this.operationResultDetails = operationResultDetails;
    }

    public Timestamp getTimestamp() {
        return Timestamp.valueOf(LocalDateTime.now(ZoneId.of("GMT+03:00")));
    }

    @Override
    public String toString() {
        return "OperationResult{" + "operationResult=" + operationResult + ", operationDescription=" + operationDescription + ", operationResultDetails=" + operationResultDetails + '}';
    }

}
