package org.zzd.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author :zzd
 * @apiNote :刷新token
 * @date : 2023-03-24 9:40
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "token刷新对象")
public class TokenVo {
    @ApiModelProperty(value = "过期时间",required = true)
    private Long expireTime;

    @ApiModelProperty(value = "token",required = true)
    private String token;

}
