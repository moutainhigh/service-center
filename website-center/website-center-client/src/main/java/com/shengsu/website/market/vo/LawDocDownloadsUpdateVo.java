package com.shengsu.website.market.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @description:
 * @author: lipiao
 * @create: 2020-07-14 16:35
 **/
@Data
public class LawDocDownloadsUpdateVo implements Serializable {
    @NotBlank
    private String docId;
}
