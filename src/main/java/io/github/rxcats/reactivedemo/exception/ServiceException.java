package io.github.rxcats.reactivedemo.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

import io.github.rxcats.reactivedemo.message.ResultCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class ServiceException extends RuntimeException {
    private static final long serialVersionUID = 2293905698144904113L;

    private ResultCode resultCode;

    private String details;

    public ServiceException(ResultCode resultCode) {
        this(resultCode, null);
    }

    public ServiceException(ResultCode resultCode, String details) {
        super(resultCode.getMessage());

        this.resultCode = resultCode;
        this.details = details;
    }

}
