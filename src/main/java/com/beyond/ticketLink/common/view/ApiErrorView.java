package com.beyond.ticketLink.common.view;

import com.beyond.ticketLink.common.exception.CommonMessageType;
import com.beyond.ticketLink.common.exception.MessageType;
import com.beyond.ticketLink.common.exception.TicketLinkException;
import lombok.Getter;
import lombok.ToString;
import org.springframework.util.ObjectUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@ToString
public class ApiErrorView {
    private final List<Error> errors;

    public ApiErrorView(List<MessageType> messageTypes) {
        this.errors = messageTypes.stream().map(Error::errorWithMessageType).collect(Collectors.toList());
    }

    public ApiErrorView(TicketLinkException exception) {
        this.errors = Collections.singletonList(Error.errorWithException(exception));
    }

    public ApiErrorView(CommonMessageType messageType, String message) {
        this.errors = Collections.singletonList(Error.errorWithMessageTypeAndMessage(messageType, message));
    }

    @Getter
    @ToString
    public static class Error {
        private final String errorType;
        private final String errorMessage;

        public static Error errorWithMessageType(MessageType messageType) {
            return new Error(messageType.name(), messageType.getMessage());
        }

        public static Error errorWithMessageTypeAndMessage(MessageType messageType, String message) {
            return new Error(messageType.name(), message);
        }

        public static Error errorWithException(TicketLinkException ticketLinkException) {
            return new Error(ticketLinkException);
        }

        private Error(String errorType, String errorMessage) {
            this.errorType = errorType;
            this.errorMessage = errorMessage;
        }

        private Error(TicketLinkException ticketLinkException) {
            this.errorType = ObjectUtils.isEmpty(ticketLinkException.getType()) ? ticketLinkException.getStatus().getReasonPhrase() :
                    ticketLinkException.getType();
            this.errorMessage = ticketLinkException.getMessage();
        }
    }
}
