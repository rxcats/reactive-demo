package io.github.rxcats.reactivedemo.message;

import lombok.Data;

@Data
public class ResponseEntity<T> {

    private int code = ResultCode.ok.code;

    private T result;

    private String message = ResultCode.ok.message;

    private String details;

    private ResponseEntity() {
        super();
    }

    private ResponseEntity(T result) {
        super();
        this.result = result;
    }

    private ResponseEntity(ResultCode code, T result) {
        super();
        this.code = code.code;
        this.message = code.message;
        this.result = result;
    }

    public ResponseEntity code(ResultCode code) {
        this.code = code.code;
        this.message = code.message;
        return this;
    }

    public ResponseEntity code(int code) {
        this.code = code;
        return this;
    }

    public ResponseEntity result(T result) {
        this.result = result;
        return this;
    }

    public ResponseEntity message(String message) {
        this.message = message;
        return this;
    }

    public ResponseEntity details(String details) {
        this.details = details;
        return this;
    }

    public static <T> ResponseEntity<T> of() {
        return new ResponseEntity<>();
    }

    public static <T> ResponseEntity<T> of(T result) {
        return new ResponseEntity<>(result);
    }

    public static <T> ResponseEntity<T> of(ResultCode code, T result) {
        return new ResponseEntity<>(code, result);
    }

}
