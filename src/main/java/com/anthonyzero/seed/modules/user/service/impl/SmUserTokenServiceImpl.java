package com.anthonyzero.seed.modules.user.service.impl;

import com.anthonyzero.seed.common.core.Result;
import com.anthonyzero.seed.common.core.SysConstant;
import com.anthonyzero.seed.common.utils.TokenGenerator;
import com.anthonyzero.seed.modules.user.entity.SmUserToken;
import com.anthonyzero.seed.modules.user.mapper.SmUserTokenMapper;
import com.anthonyzero.seed.modules.user.service.SmUserTokenService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 用户TOKEN 服务实现类
 * </p>
 *
 * @author anthonyzero
 * @since 2019-11-14
 */
@Service
public class SmUserTokenServiceImpl extends ServiceImpl<SmUserTokenMapper, SmUserToken> implements SmUserTokenService {

    @Override
    @Transactional
    public Result createToken(Long userId) {
        // 生成一个token
        String token = TokenGenerator.generateValue();

        // 当前时间
        LocalDateTime now = LocalDateTime.now();
        // 过期时间
        LocalDateTime expireTime = now.plusSeconds(SysConstant.EXPIRE);

        // 判断是否生成过token
        SmUserToken tokenEntity = baseMapper.selectById(userId);
        if (tokenEntity == null) {
            tokenEntity = new SmUserToken();
            tokenEntity.setUserId(userId);
            tokenEntity.setToken(token);
            tokenEntity.setUpdateTime(now);
            tokenEntity.setExpireTime(expireTime);

            // 保存token
            baseMapper.insert(tokenEntity);
        } else {
            tokenEntity.setToken(token);
            tokenEntity.setUpdateTime(now);
            tokenEntity.setExpireTime(expireTime);

            // 更新token
            baseMapper.updateById(tokenEntity);
        }
        Map<String,Object> map = new HashMap<>();
        map.put("token", token);
        map.put("expire", SysConstant.EXPIRE);
        return Result.success(map);
    }
}
