package org.zerock.mreview.controller;


import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.mreview.dto.PageRequestDTO;

@Controller
public class UploadTestController {

    @GetMapping("/uploadEx")
    public void uploadEx(MultipartFile[] uploadFiles){
    }



    @GetMapping({"/"})
    public String home(PageRequestDTO pageRequestDTO, Model model){
        return "redirect:/movie/list";
    }

}
