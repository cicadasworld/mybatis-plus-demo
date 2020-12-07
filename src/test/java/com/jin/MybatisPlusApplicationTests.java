package com.jin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jin.entity.User;
import com.jin.mapper.UserMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class MybatisPlusApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    void testSelectList() {
        List<User> users = userMapper.selectList(null);
        users.forEach(System.out::println);
    }

    @Test
    void testInsert() {
        User user = new User();
        user.setName("Jim");
        user.setAge(3);
        user.setEmail("test@jin.com");
        userMapper.insert(user);
    }

    @Test
    void testUpdate() {
        User user = new User();
        user.setId(5L);
        user.setName("July");
        user.setAge(19);
        userMapper.updateById(user);
    }

    @Test
    void testOptimisticLocker() {
        User user = userMapper.selectById(1L);
        user.setName("Tim");
        user.setEmail("tim@jin.com");
        userMapper.updateById(user);
    }

    @Test
    void testOptimisticLocker2() {
        // 模拟线程1
        User user = userMapper.selectById(1L);
        user.setName("Tim");
        user.setEmail("tim@jin.com");

        // 模拟线程2
        User user2 = userMapper.selectById(1L);
        user2.setName("Lily");
        user2.setEmail("liy@jin.com");
        userMapper.updateById(user2);

        userMapper.updateById(user);
    }

    @Test
    void testSelectById() {
        User user = userMapper.selectById(1L);
        System.out.println(user);
    }

    // 批量查询
    @Test
    void testSelectByBatchId() {
        List<User> users = userMapper.selectBatchIds(Arrays.asList(1, 2, 3));
        users.forEach(System.out::println);
    }

    // 条件查询
    @Test
    void testSelectByMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "Tom");
        map.put("age", 28);
        List<User> users = userMapper.selectByMap(map);
        users.forEach(System.out::println);
    }

    // 分页查询
    @Test
    void testPagination() {
        Page<User> page = new Page<>(1, 3);
        Page<User> selectPage = userMapper.selectPage(page, null);
        selectPage.getRecords().forEach(System.out::println);
        System.out.println(selectPage.getTotal());
    }

    // 删除
    @Test
    void testDeleteById() {
        userMapper.deleteById(5L);
    }

    @Test
    void testDeleteBatchId() {
        userMapper.deleteBatchIds(Arrays.asList(1L, 2L, 3L));
    }

    @Test
    void testDeleteByMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "Tom");
        userMapper.deleteByMap(map);
    }
}
