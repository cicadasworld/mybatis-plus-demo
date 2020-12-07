package com.jin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jin.entity.User;
import com.jin.mapper.UserMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

@SpringBootTest // 条件构造器
public class WrapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    void testSelectList() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.isNotNull("name")
                .isNotNull("email")
                .ge("age", 12);
        List<User> users = userMapper.selectList(wrapper);
        users.forEach(System.out::println);
    }

    @Test
    void testSelectOne() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("name", "Tom");
        User user = userMapper.selectOne(wrapper);
        System.out.println(user);
    }

    @Test
    void testSelectCount() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.between("age", 20, 30);
        Integer count = userMapper.selectCount(wrapper);
        System.out.println(count);
    }

    // 模糊查询
    @Test
    void testSelectMaps() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.notLike("name", "o")
                .likeRight("email", "t"); // t%
        List<Map<String, Object>> maps = userMapper.selectMaps(wrapper);
        maps.forEach(System.out::println);
    }

    // 嵌套查询
    @Test
    void testObjets() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.inSql("id", "select id from user where id < 3");
        List<Object> objects = userMapper.selectObjs(wrapper);
        objects.forEach(System.out::println);
    }

    // 排序
    @Test
    void testOrderBy() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        List<User> users = userMapper.selectList(wrapper);
        users.forEach(System.out::println);
    }
}
