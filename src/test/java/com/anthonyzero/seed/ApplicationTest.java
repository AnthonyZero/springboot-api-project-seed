package com.anthonyzero.seed;

import com.anthonyzero.seed.common.core.SysConstant;
import com.anthonyzero.seed.modules.user.entity.SmUser;
import com.anthonyzero.seed.modules.user.mapper.SmUserMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTest {

	@Autowired
	private SmUserMapper smUserMapper;
	
	@Test
	public void createUser() {
		SmUser smUser = new SmUser();
		smUser.setUserName("username");
		smUser.setUserCode("admin");
		String salt = RandomStringUtils.randomAlphanumeric(20);
		// 初始明文密码 123456 sha256加密
		smUser.setPassword(new Sha256Hash("123456", salt).toHex());
		smUser.setSalt(salt);
		smUser.setDepartId(0L);
		smUser.setState(SysConstant.STATE_VALID);
		smUser.setCreateTime(LocalDateTime.now());
		smUserMapper.insert(smUser);
	}
	
	@Test
	public void initPassword() {
		String usercode = "test";
		SmUser smUser = smUserMapper.selectOne(new LambdaQueryWrapper<SmUser>().eq(SmUser::getUserCode, usercode));
		if (smUser == null) {
			System.out.println(usercode + "用户不存在");
			return;
		}
		String salt = RandomStringUtils.randomAlphanumeric(20);
		smUser.setPassword(new Sha256Hash("123456", salt).toHex());
		smUser.setSalt(salt);
		smUserMapper.updateById(smUser);
	}
}
