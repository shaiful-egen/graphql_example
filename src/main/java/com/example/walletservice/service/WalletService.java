package com.example.walletservice.service;

import com.example.walletservice.dto.AddWalletRequestDTO;
import com.example.walletservice.entities.Currency;
import com.example.walletservice.entities.Wallet;
import com.example.walletservice.entities.WalletTransaction;
import com.example.walletservice.enums.TransactionType;
import com.example.walletservice.repositories.CurrencyRepository;
import com.example.walletservice.repositories.WalletRepository;
import com.example.walletservice.repositories.WalletTransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Service
@Transactional
public class WalletService {

  private CurrencyRepository currencyRepository;
  private WalletRepository walletRepository;
  private WalletTransactionRepository walletTransactionRepository;

  public WalletService(
    CurrencyRepository currencyRepository,
    WalletRepository walletRepository,
    WalletTransactionRepository walletTransactionRepository) {
    this.currencyRepository = currencyRepository;
    this.walletRepository = walletRepository;
    this.walletTransactionRepository = walletTransactionRepository;
  }

  public Wallet save(AddWalletRequestDTO walletRequestDTO) {
    Currency currency = currencyRepository.findById(walletRequestDTO.currencyCode()).orElseThrow(
      ()-> new RuntimeException(String.format("Currency %s not found",walletRequestDTO.currencyCode())));
    Wallet wallet = Wallet.builder()
      .balance(walletRequestDTO.balance())
      .id(UUID.randomUUID().toString())
      .createdAt(System.currentTimeMillis())
      .userId("user1")
      .currency(currency)
      .build();
    return walletRepository.save(wallet);
  }

  public void loadData() throws IOException {
    URI uri = new ClassPathResource("currencies.data.csv").getURI();
    Path path = Paths.get(uri);
    List<String> lines = Files.readAllLines(path);
    for (int i = 1; i < lines.size(); i++) {
      String[] line = lines.get(i).split(";");
      Currency currency = Currency.builder()
        .code(line[0])
        .name(line[1])
        .salePrice(Double.parseDouble(line[2]))
        .purchasePrice(Double.parseDouble(line[3]))
        .build();
      this.currencyRepository.save(currency);
    }
    Stream.of("USD", "EUR", "CAD").forEach(currencyCode -> {
      Currency currency = currencyRepository.findById(currencyCode)
        .orElseThrow(() -> new RuntimeException(String.format("Currency %s not found", currencyCode)));
      Wallet wallet = new Wallet();
      wallet.setBalance(10000.0);
      wallet.setCurrency(currency);
      wallet.setCreatedAt(System.currentTimeMillis());
      wallet.setUserId("user1");
      wallet.setId(UUID.randomUUID().toString());
      walletRepository.save(wallet);
    });
    walletRepository.findAll().forEach(wallet -> {
      for (int i = 0; i < 10; i++) {
        WalletTransaction debitWalletTransaction = WalletTransaction.builder()
          .amount(Math.random() * 1000)
          .wallet(wallet)
          .type(TransactionType.DEBIT)
          .timeStamp(System.currentTimeMillis())
          .build();
        walletTransactionRepository.save(debitWalletTransaction);
        wallet.setBalance(wallet.getBalance() - debitWalletTransaction.getAmount());
        WalletTransaction creditWalletTransaction = WalletTransaction.builder()
          .amount(Math.random() * 1000)
          .wallet(wallet)
          .type(TransactionType.CREDIT)
          .timeStamp(System.currentTimeMillis())
          .build();
        walletTransactionRepository.save(creditWalletTransaction);
        wallet.setBalance(wallet.getBalance() + creditWalletTransaction.getAmount());
        walletRepository.save(wallet);
      }
    });
  }
}
