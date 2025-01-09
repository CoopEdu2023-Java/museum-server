package cn.moonshotacademy.museum.controller;

import cn.moonshotacademy.museum.dto.CompetencyDto;
import cn.moonshotacademy.museum.dto.ResponseDto;
import cn.moonshotacademy.museum.entity.CompetencyEntity;
import cn.moonshotacademy.museum.service.CompetencyService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CompetencyController {
    @Autowired private CompetencyService competencyService;

    @GetMapping("/competencies")
    public ResponseDto<List<CompetencyDto>> competenciesList() {
        List<CompetencyDto> data = competencyService.getCompetencies();

        return new ResponseDto<List<CompetencyDto>>(data);
    }

    @GetMapping("/competencies/{id}")
    public ResponseDto<CompetencyEntity> competencyById(@PathVariable Integer id) {
        CompetencyEntity data = competencyService.getCompetencyById(id);

        return new ResponseDto<CompetencyEntity>(data);
    }
}
