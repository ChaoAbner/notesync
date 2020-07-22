package com.cvte.notesync.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cvte.notesync.entity.Note;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteMapper extends BaseMapper<Note> {

    @Select("select * from n_note where user_id = #{userId} and status = 1" +
            " order by update_time desc limit #{start}, #{limit}")
    List<Note> selectNotesByUserId(@Param("userId") int userId,
                                   @Param("start") int start, @Param("limit") int limit);
}
