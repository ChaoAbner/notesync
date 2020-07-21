package com.cvte.notesync.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cvte.notesync.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper extends BaseMapper<User> {

    @Select("select * from n_user where username = #{username}")
    User selectByUsername(@Param("username") String username);
}
