package com.esprit.jobfinder.controllers;

import com.esprit.jobfinder.models.Training;
import com.esprit.jobfinder.models.User;
import com.esprit.jobfinder.models.enums.TrainingCategories;
import com.esprit.jobfinder.services.ITrainingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/training")
@RequiredArgsConstructor
public class TrainingController {
    @Qualifier("ITrainingServiceImpl")
    private final ITrainingService trainingService;

    @PostMapping()
    public Training addTraining(@RequestBody Training training){
        return trainingService.addTraining(training);
    }

    @GetMapping("{id}")
    public Training getTraining(@PathVariable long id){
        return trainingService.getTraining(id);
    }
    @GetMapping("/likes/{direction}")
    public List<Training> getAllOrderByLikes(@PathVariable String direction){
        return trainingService.getAllOrderByLikes(direction);
    }
    @GetMapping("/price/{direction}")
    public List<Training> getAllOrderByPrice(@PathVariable String direction){
        return trainingService.getAllOrderByPrice(direction);
    }
    @DeleteMapping("{id}")
    public void deleteTraining(@PathVariable long id){
        trainingService.DeleteTraining(id);
    }
    @PutMapping()
    public Training updateTraining(@RequestBody Training training){
        return trainingService.updateTraining(training);
    }
    @GetMapping("/findTrainingByCategories/{trainingCategories}")
    public Set<Training> findTrainingByCategories(@PathVariable TrainingCategories trainingCategories) {
        return trainingService.findTrainingByCategories(trainingCategories);
    }
    @GetMapping()
    public ResponseEntity<Page<Training>> getAllTrainings(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "1") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortOrder) {

    Page<Training> trainings = trainingService.getAllTrainings(page, size, sortBy, sortOrder);
        return ResponseEntity.ok(trainings);
    }
    @PostMapping("/{postId}/like")
    public ResponseEntity<Training> likeTraining(@PathVariable Long postId) {
        Training training = trainingService.likeTraining(postId);
        return ResponseEntity.ok(training);
    }
    @PostMapping("/{postId}/dislike")
    public ResponseEntity<Training> dislikePost(@PathVariable Long postId) {
        Training training = trainingService.dislikeTraining(postId);
        return ResponseEntity.ok(training);
    }
}
