package com.pe.exchange.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	
	Logger log = LoggerFactory.getLogger(FileControllerUtils.class);
	
	 /**
     * 单文件上传
     *
     * @param file
     * @param request
     * @return
     */
    @PostMapping("/upload")
    @ResponseBody
    public Result upload(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        if (!file.isEmpty()) {
        	log.info("开始文件上传！");
        	file.getName();
            String saveFileName = VeriCodeUtils.getOrderIdByUUId()+"_"+System.currentTimeMillis()+file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."), file.getOriginalFilename().length());
            File saveFile_ = new File(request.getSession().getServletContext().getRealPath("/upload/") + saveFileName);
            File saveFile = new File("/opt/app_end/upload/" + saveFileName);
            List<File> listFiles = new ArrayList<File>();
            listFiles.add(saveFile_);
            listFiles.add(saveFile);
            
            try {
	            for (File f : listFiles) {
	            	 if (!f.getParentFile().exists()) {
	                     f.getParentFile().mkdirs();
	                     log.info("创建文件保存目录:"+f.getParentFile().getPath());
	                 }
	            	 BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(f));
	                 out.write(file.getBytes());
	                 out.flush();
	                 out.close();
	                 log.info("文件上传成功！");
				}
               
                return Results.success(saveFile_.getName());
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
