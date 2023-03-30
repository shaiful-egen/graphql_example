package com.example.walletservice.web;

import com.example.walletservice.dto.AddWalletRequestDTO;
import com.example.walletservice.entities.Wallet;
import com.example.walletservice.repositories.WalletRepository;
import com.example.walletservice.service.WalletService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.Currency;
import java.util.List;
import java.util.UUID;

@Controller
public class WalletGraphQLController {

  private WalletRepository walletRepository;
  private WalletService walletService;

  public WalletGraphQLController(WalletRepository walletRepository, WalletService walletService) {
    this.walletRepository = walletRepository;
    this.walletService = walletService;
  }

  @QueryMapping
  public List<Wallet> userWallets() {
    return walletRepository.findAll();
  }

  @QueryMapping
  public Wallet walletById(@Argument String id) {
    return walletRepository.findById(id)
      .orElseThrow(() -> new RuntimeException(String.format("Wallet %s not found")));
  }

  @MutationMapping
  public Wallet addWallet(@Argument AddWalletRequestDTO wallet) {
    return walletService.save(wallet);
  }

}
