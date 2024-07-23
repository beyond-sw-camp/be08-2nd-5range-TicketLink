package com.beyond.ticketLink.board.persistence.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Date;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BoardUpdateDto {
    private String boardNo;
    private String content;
    private String title;
    private float rating;
    private Date uptDate;
}