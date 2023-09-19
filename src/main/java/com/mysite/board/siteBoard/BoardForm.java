package com.mysite.board.siteBoard;


import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class BoardForm {
    private String subject;
    private String content;
}
