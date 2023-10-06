package com.toyproject.instagram.service;

import com.toyproject.instagram.dto.UploadFeedReqDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FeedService {

    @Value("${file.path}") // yml에서 IoC에서 DI될 때 가져올 수 있도록
    private String filePath;

    public void upload(UploadFeedReqDto uploadFeedReqDto) {
        uploadFeedReqDto.getFiles().forEach(file -> {
            String originName = file.getOriginalFilename();
            String extensionName = originName.substring(originName.lastIndexOf("."));
            String newName = UUID.randomUUID().toString().replaceAll("-", "").concat(extensionName);
            // 새이름 만듦 : 서버에서 저장 할 때 이미지 이름이 곂치면 데이터가 덮어 써져버리기 때문에

            Path uploadPath = Paths.get(filePath + "/feed/" + newName);

            File f = new File(filePath + "/feed");
            // 요청한 client에게 File의 경로가 없을 경우에 경로를 만들기 위해
            if(!f.exists()) {
                f.mkdirs();
            }

            try {
                Files.write(uploadPath, file.getBytes()); // ** 경로에, byte데이터로된 받은 파일
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

}








