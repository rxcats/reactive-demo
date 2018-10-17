package io.github.rxcats.reactivedemo.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultCode {

    ok(0, "Success"),
    error(900001, "Service Error"),

    ;

    final int code;
    final String message;

}
