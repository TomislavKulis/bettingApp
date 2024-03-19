package com.example.bettingApplication.mapper;

import com.example.bettingApplication.entity.BetSlip;
import com.example.bettingApplication.model.BetSlipDTO;
import org.mapstruct.Mapper;

import java.util.Optional;

@Mapper(componentModel = "spring")
public interface BetSlipMapper {

    BetSlip betSlipDtoToBetSlip(BetSlipDTO betSlipDTO);

    BetSlipDTO betSlipToBetSlipDto(BetSlip betSlip);
}
