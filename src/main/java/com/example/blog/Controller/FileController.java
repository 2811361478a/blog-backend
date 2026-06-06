package com.example.blog.Controller;
import org.springframework.web.multipart.MultipartFile;
import com.example.blog.Result;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/File")
public class FileController {
    @Value("${upload.path:/tmp/uploads/}")
    private String uploadPath;

    @Value("${server.base-url:http://localhost:8080}")
    private String baseUrl;

    @PostMapping("/upload")
    public Result<String> upload(@RequestParam("file") MultipartFile file,@RequestParam(value = "oldAvatar",required = false) String oldAvatar)throws IOException{
        File dir = new File(uploadPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String originalName=file.getOriginalFilename();
        String ext=originalName.substring(originalName.lastIndexOf("."));
        String newName= UUID.randomUUID().toString()+ext;
        file.transferTo(new File(uploadPath+newName));
        if (oldAvatar!=null&&!oldAvatar.isEmpty()){
            String oldFileName=oldAvatar.substring(oldAvatar.lastIndexOf("/")+1);
            File oldFile=new File(uploadPath+oldFileName);
            if (oldFile.exists()){
                try{
                    java.nio.file.Files.delete(oldFile.toPath());
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
        return Result.success(baseUrl+"/uploads/"+newName);
    }
}
