package com.example.blog.Controller;
import org.springframework.web.multipart.MultipartFile;
import com.example.blog.Result;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/File")
public class FileController {
    private String uploadPath="D:/uploads/";
    @PostMapping("/upload")
    public Result<String> upload(@RequestParam("file") MultipartFile file,@RequestParam(value = "oldAvatar",required = false) String oldAvatar)throws IOException{
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
        return Result.success("http://localhost:8080/uploads/"+newName);
    }
}
