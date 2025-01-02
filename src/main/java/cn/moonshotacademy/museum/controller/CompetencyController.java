package cn.moonshotacademy.museum.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.moonshotacademy.museum.dto.ResponseDto;
import cn.moonshotacademy.museum.entity.CompetencyEntity;
import cn.moonshotacademy.museum.service.CompetencyService;

@RestController
public class CompetencyController {
    @Autowired
    private CompetencyService competencyService;

    @GetMapping("/competencies")
    public ResponseDto<List<CompetencyEntity>> competenciesList() {
        List<CompetencyEntity> data = competencyService.getCompetencies();

        return new ResponseDto<List<CompetencyEntity>>(data);
    }
    
}
