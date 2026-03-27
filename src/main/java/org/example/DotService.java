package org.example;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.example.validation.AreaCheck;
import org.example.validation.ArgumentValidator;
import org.example.validation.ValueValidator;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

@Named
@ApplicationScoped
public class DotService implements Serializable {

    @Inject
    private DotRepository dotRepository;


    @Inject
    private DotMapper dotMapper;


    public List<ViewDto> getAllDots() {
        List<Dot> entities = dotRepository.findAll();
        return dotMapper.toViewDtoList(entities);
    }


    public void clearAllDots() {
        dotRepository.deleteAll();
    }


    public ViewDto processAndSaveDot(CreateDto createDto) {


        Dot dot = dotMapper.toEntity(createDto);


        ArgumentValidator.validate(dot);
        ValueValidator.validate(dot);


        long startNs = System.nanoTime();


        dot.setStatus(AreaCheck.isHit(dot));


        String serverNowUtcIso = Instant.now().toString();
        dot.setTime(serverNowUtcIso);


        long durationMicros = (System.nanoTime() - startNs) / 1_000L;
        dot.setScriptTime(durationMicros);


        dotRepository.save(dot);


        return dotMapper.toViewDto(dot);
    }
}
