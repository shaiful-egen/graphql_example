package com.example.walletservice.controller;

import com.example.walletservice.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping
public class TestController {



  @GetMapping("/test1")
  public ResponseEntity<String> test1() throws IOException {

    return new ResponseEntity<>("test success", HttpStatus.OK);

  }
}

