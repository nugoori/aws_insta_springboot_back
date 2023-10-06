package com.toyproject.instagram.service;

import com.toyproject.instagram.dto.UploadFeedReqDto;
import com.toyproject.instagram.entity.Feed;
import com.toyproject.instagram.entity.FeedImg;
import com.toyproject.instagram.repository.FeedMapper;
import com.toyproject.instagram.security.PrincipalUser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class FeedService {

    @Value("${file.path}") // yml에서 IoC에서 DI될 때 가져올 수 있도록
    private String filePath;

    private final FeedMapper feedMapper;

    @Transactional( rollbackFor = Exception.class )
    public void upload(UploadFeedReqDto uploadFeedReqDto) {

        // 부품 작성
        String content = uploadFeedReqDto.getContent();
        List<FeedImg> feedImgList = new ArrayList<>();
        PrincipalUser principalUser =
                (PrincipalUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = principalUser.getUsername();
        // stateless ??

        Feed feed = Feed.builder()
                .content(content)
                .username(username)
                .build();

        feedMapper.saveFeed(feed);

        uploadFeedReqDto.getFiles().forEach(file -> {
            String originName = file.getOriginalFilename();
            String extensionName = originName.substring(originName.lastIndexOf("."));
            String saveName = UUID.randomUUID().toString().replaceAll("-", "").concat(extensionName);
            // 새이름 만듦 : 서버에서 저장 할 때 이미지 이름이 곂치면 데이터가 덮어 써져버리기 때문에

            Path uploadPath = Paths.get(filePath + "/feed/" + saveName);

            File f = new File(filePath + "/feed");
            // 요청한 client에게 File의 경로가 없을 경우에 경로를 만들기 위해
            if (!f.exists()) {
                f.mkdirs();
            }

            try {
                Files.write(uploadPath, file.getBytes()); // ** 경로에, byte데이터로된 받은 파일
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            feedImgList.add(FeedImg.builder()
                    .feedId(feed.getFeedId()) // feed객체를 builder로 생성 하고 가져오기
                    .originFileName(originName)
                    .saveFileName(saveName)
                    .build());
        });

        feedMapper.saveFeedImgList(feedImgList); // 여기까지 문제점 feed생성후 img파일을 넣는 과정에 문제가 생길경우 -> rollback
    }
}








