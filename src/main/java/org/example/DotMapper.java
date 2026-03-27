package org.example;

import org.mapstruct.Mapper;
import java.util.List;


@Mapper(componentModel = "cdi")
public interface DotMapper {


    Dot toEntity(CreateDto dto);


    ViewDto toViewDto(Dot dot);


    List<ViewDto> toViewDtoList(List<Dot> dots);
}
