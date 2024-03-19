package com.example.bettingApplication.mapper;

import com.example.bettingApplication.entity.Wallet;
import com.example.bettingApplication.model.WalletDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WalletMapper {

    Wallet walletDtoToWallet(WalletDTO walletDTODTO);

    WalletDTO walletToWalletDto(Wallet wallet);
}
