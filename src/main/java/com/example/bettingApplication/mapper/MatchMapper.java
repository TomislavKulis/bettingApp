package com.example.bettingApplication.mapper;

import com.example.bettingApplication.entity.Match;
import com.example.bettingApplication.model.MatchDTO;
import org.mapstruct.Mapper;
@Mapper(componentModel = "spring")
public interface MatchMapper {

    Match matchDtoToMatch(MatchDTO matchDTO);

    MatchDTO matchToMatchDto(Match match);
}
