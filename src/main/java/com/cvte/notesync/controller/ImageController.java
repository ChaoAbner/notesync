package com.cvte.notesync.controller;


import com.alibaba.fastjson.JSONObject;
import com.cvte.notesync.common.response.Result;
import com.cvte.notesync.service.ImageService;
import com.cvte.notesync.utils.CommonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("image")
@Api(tags = "图片接口")
public class ImageController {

    @Autowired
    ImageService imageService;

    @PostMapping("/note/{noteId}")
    @ApiOperation("上传图片")
    public Result insertImage(@PathVariable int noteId, @RequestParam("file") MultipartFile file) throws IOException {
        String link = imageService.insertImage(file.getBytes(), CommonUtil.uuid(), noteId);
        JSONObject jo = new JSONObject();
        jo.put("url", link);
        return Result.success(jo);
    }

    @GetMapping("/list/note/{noteId}")
    @ApiOperation("获取某个笔记的图片列表")
    public Result getImagesByNoteId(@PathVariable int noteId) {
        List<String> result = imageService.getImagesByNoteId(noteId);
        return Result.success(result);
    }
}
