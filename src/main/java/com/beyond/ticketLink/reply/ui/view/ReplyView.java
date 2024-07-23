package com.beyond.ticketLink.reply.ui.view;

import com.beyond.ticketLink.reply.application.service.ReplyService.FindReplyResult;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ReplyView(
        Integer cnt,
        String content,
        Date insDate,
        Date uptDate
) {
    public ReplyView(FindReplyResult reply) {
        this(
                reply.getCnt(),
                reply.getContent(),
                reply.getInsDate(),
                reply.getUptDate()
        );
    }
}
