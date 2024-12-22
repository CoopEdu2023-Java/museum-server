package cn.moonshotacademy.museum.controller;

import cn.moonshotacademy.museum.entity.ArtifactEntity;
import cn.moonshotacademy.museum.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/artifact")
public class ArtifactController {

    @Autowired
    private ArtifactService artifactService;

    @PostMapping("/{artifactId}/update")
    public ResponseEntity<String> updateArtifact(
            @PathVariable Integer artifactId,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String intro,
            @RequestParam(required = false) String competency,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String type) {

        ArtifactEntity updatedArtifact = artifactService.updateArtifact(artifactId, title, intro, competency, category, type);

        if (updatedArtifact != null) {
            return ResponseEntity.ok("Artifact updated successfully");
        } else {
            return ResponseEntity.ok("Fuck");
        }
    }
}