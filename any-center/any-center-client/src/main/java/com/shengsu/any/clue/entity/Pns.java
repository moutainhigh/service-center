package com.shengsu.any.clue.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * pns
 * @author 
 */
@Data
public class Pns implements Serializable {
    private String clueId;

    private String bindId;

    private Date bindTime;

    private String tela;

    private String telb;

    private String telx;

    private Integer expiration;

    private static final long serialVersionUID = 1L;
}