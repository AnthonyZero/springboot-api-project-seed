package com.anthonyzero.seed.modules.user.service;

import com.anthonyzero.seed.common.core.Result;
import com.anthonyzero.seed.modules.user.entity.SmUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author anthonyzero
 * @since 2019-11-14
 */
public interface SmUserService extends IService<SmUser> {

    /**
     * 登录
     * @param loginName
     * @param password
     * @return
     */
    Result login(String loginName, String password);
}
