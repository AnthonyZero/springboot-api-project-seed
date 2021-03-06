package com.anthonyzero.seed.modules.user.service;

import com.anthonyzero.seed.common.core.Result;
import com.anthonyzero.seed.modules.user.entity.SmUserToken;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户TOKEN 服务类
 * </p>
 *
 * @author anthonyzero
 * @since 2019-11-14
 */
public interface SmUserTokenService extends IService<SmUserToken> {

    /**
     * 创建token
     * @param userId
     * @return
     */
    Result createToken(Long userId);
}
