package com.cvte.notesync.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cvte.notesync.entity.Note;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface NoteMapper extends BaseMapper<Note> {

}
