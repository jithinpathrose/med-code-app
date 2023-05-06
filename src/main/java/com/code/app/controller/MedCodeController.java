package com.code.app.controller;


import com.code.app.dto.MedCodeDetails;
import com.code.app.service.MedCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/code")
public class MedCodeController {

    @Autowired
    private MedCodeService medCodeService;

    @PostMapping("/upload")
    public void uploadcsv(@RequestBody List<MedCodeDetails> medCodeDetails){
        if(!this.medCodeService.saveAll(medCodeDetails)){
            throw new RuntimeException("Upload failed.");
        }
    }

    @GetMapping("/fetchAll")
    public List<MedCodeDetails> fetchAll(){
        return this.medCodeService.fetchAll();
    }

    @GetMapping("/fetchById")
    public MedCodeDetails fetch(@RequestParam String code){
        return this.medCodeService.fetchById(code);
    }


    @DeleteMapping("/deleteAll")
    public void deleteAll(){
        this.medCodeService.deleteAll();
    }
}
