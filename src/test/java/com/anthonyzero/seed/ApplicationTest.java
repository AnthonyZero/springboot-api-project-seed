package com.anthonyzero.seed;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSON;
import com.anthonyzero.seed.modules.user.domain.SmUser;
import com.anthonyzero.seed.modules.user.mapper.SmUserMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTest {

	@Autowired
	private SmUserMapper smUserMapper;
	
	@Test
	public void contextLoads() {
		SmUser smUser = smUserMapper.selectByPrimaryKey(384234735095808L);
		System.out.println(JSON.toJSONString(smUser));
	}

}
