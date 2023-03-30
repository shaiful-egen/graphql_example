package com.example.walletservice.entities;

import com.example.walletservice.enums.TransactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WalletTransaction {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private Long timeStamp;
  private Double amount;

  @ManyToOne
  private Wallet wallet;

  @Enumerated(EnumType.STRING)
  private TransactionType type;

}
