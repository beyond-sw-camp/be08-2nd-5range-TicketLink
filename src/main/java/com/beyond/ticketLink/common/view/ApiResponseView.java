package com.beyond.ticketLink.common.view;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.ToString;

/**
 * To unify the format of views returned by the controller
 * wrap the view returned by the controller
 * @param <T> any type of view
 */
@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponseView<T> {
    private final T data;

    public ApiResponseView(T data) {
        this.data = data;
    }
}
