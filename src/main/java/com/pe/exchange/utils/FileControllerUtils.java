package com.pe.exchange.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.pe.exchange.common.Result;
import com.pe.exchange.common.Results;

@RestController
@RequestMapping("file")
public class FileControllerUtils {
	
	 /**
     * 单文件上传
     *
     * @param file
     * @param request
     * @return
     */
    @PostMapping("/upload")
    @ResponseBody
    public Result upload(@RequestParam("file") MultipartFile file,@RequestParam("type")String type, HttpServletRequest request) {
        if (!file.isEmpty()) {
            String saveFileName = type+"_"+file.getOriginalFilename();
            File saveFile = new File(request.getSession().getServletContext().getRealPath("/upload/") + saveFileName);
            if (!saveFile.getParentFile().exists()) {
                saveFile.getParentFile().mkdirs();
            }
            try {
                BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(saveFile));
                out.write(file.getBytes());
                out.flush();
                out.close();
                return Results.success(saveFile.getName());
            } catch (FileNotFoundException e) {
               // e.printStackTrace();
                return Results.fail("上传失败," + e.getMessage());
            } catch (IOException e) {
                //e.printStackTrace();
                return  Results.fail("上传失败," + e.getMessage());
            }
        } else {
        	return Results.fail("上传失败，因为文件为空.");
        }
    }
 
    /**
     * 多文件上传
     *
     * @param request
     * @return
     */
    @PostMapping("/uploadFiles")
    @ResponseBody
    public String uploadFiles(HttpServletRequest request) throws IOException {
        File savePath = new File(request.getSession().getServletContext().getRealPath("/upload/"));
        if (!savePath.exists()) {
            savePath.mkdirs();
        }
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
        MultipartFile file = null;
        BufferedOutputStream stream = null;
        for (int i = 0; i < files.size(); ++i) {
            file = files.get(i);
            if (!file.isEmpty()) {
                try {
                    byte[] bytes = file.getBytes();
                    File saveFile = new File(savePath, file.getOriginalFilename());
                    stream = new BufferedOutputStream(new FileOutputStream(saveFile));
                    stream.write(bytes);
                    stream.close();
                } catch (Exception e) {
                    if (stream != null) {
                        stream.close();
                        stream = null;
                    }
                    return "第 " + i + " 个文件上传有错误" + e.getMessage();
                }
            } else {
                return "第 " + i + " 个文件为空";
            }
        }
        return "所有文件上传成功";
    }

}
