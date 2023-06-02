package com.example.demo;

import java.io.IOException;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TempController {

    @GetMapping("/test")
    public String temp(Model model) throws IOException {

        System.out.println(System.getProperty("user.dir"));

        // 아래 코드는 프로젝트가 파일을 가져오는 경로를 확인한다. (주석처리)
        // 개발환경에서는 gradle에 sourceSets에 webapp폴더를 추가하여야 작동한다.
        // 실제환경(jar, war)에서는 sourceSets에 webapp폴더를 추가하지 않아도 작동한다.
        // ClassPathResource classPathResource = new ClassPathResource("img/cat.jpg");
        // model.addAttribute("img", classPathResource.getURL().toString());

        return "list";
    }

}
