package com.cvte.notesync.controller;


import com.alibaba.fastjson.JSONObject;
import com.cvte.notesync.common.response.Result;
import com.cvte.notesync.entity.FileDo;
import com.cvte.notesync.entity.dto.FileDto;
import com.cvte.notesync.service.FileService;
import com.cvte.notesync.service.ImageService;
import com.cvte.notesync.utils.CommonUtil;
import com.cvte.notesync.utils.ImageUtil;
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

    @Autowired
    FileService fileService;

    @PostMapping("/qiniu/note/{noteId}")
    @ApiOperation("上传图片到七牛云")
    public Result insertImageToCloud(@PathVariable int noteId,
                                     @RequestParam("file") MultipartFile file) throws IOException {
        // 图片格式校验
        ImageUtil.checkImageFormat(file.getInputStream());
        String link = imageService.insertImage(file.getBytes(), CommonUtil.getUuid(), noteId);
        JSONObject jo = new JSONObject();
        jo.put("url", link);
        return Result.success(jo);
    }

    @PostMapping("/local/note/{noteId}")
    @ApiOperation("上传图片到本地")
    public Result insertImageToLocal(@PathVariable int noteId,
                                     @RequestParam("file") MultipartFile file) throws IOException {
        // 图片格式校验
        ImageUtil.checkImageFormat(file.getInputStream());
        String link = fileService.saveMultipartFile(file, CommonUtil.getShortUuid(), noteId);
        JSONObject jo = new JSONObject();
        jo.put("url", link);
        return Result.success(jo);
    }

    @PostMapping("/shard/note/{noteId}")
    @ApiOperation("上传文件分片到本地")
    public Result insertImageToLocalByShard(@PathVariable int noteId,
                                            @RequestBody FileDto fileDto) throws IOException {
//        ImageUtil.checkImageFormat(fileDto);
        int result = fileService.saveFile(fileDto, noteId);
        JSONObject jo = new JSONObject();
        jo.put("result", result);
        return Result.success(jo);
    }

    @GetMapping("/shard/check")
    @ApiOperation("检查某个文件是否有分片")
    public Result insertImageToLocalByShard(String key) {
        FileDo fileDo = fileService.selectFileByKey(key);
        return Result.success(fileDo);
    }

    @GetMapping("/list/note/{noteId}")
    @ApiOperation("获取某个笔记的图片列表")
    public Result getImagesByNoteId(@PathVariable int noteId) {
        List<String> result = imageService.getImagesByNoteId(noteId);
        return Result.success(result);
    }
}
