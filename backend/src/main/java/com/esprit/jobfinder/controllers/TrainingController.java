package com.esprit.jobfinder.controllers;

import com.esprit.jobfinder.models.Training;
import com.esprit.jobfinder.models.enums.TrainingCategories;
import com.esprit.jobfinder.payload.request.CreateTrainingReq;
import com.esprit.jobfinder.payload.request.UpdateTrainingReq;
import com.esprit.jobfinder.services.ITrainingService;
import com.esprit.jobfinder.utiles.DateUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
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

    @PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public Training addTraining(@Valid @ModelAttribute CreateTrainingReq training){
        Training train = new Training();
        train.setTitle(training.getTitle());
        train.setDescription(training.getDescription());
        train.setPrice(Double.parseDouble(training.getPrice()));
        train.setRating(Double.parseDouble(training.getRating()));
        train.setLikes(Integer.parseInt(training.getLikes()));
        train.setDislikes(Integer.parseInt(training.getDislikes()));
        train.setTrainingCategories(training.getTrainingCategories());
        train.setDateDebut(DateUtils.parseDate(training.getDateDebut()));
        train.setDateFin(DateUtils.parseDate(training.getDateFin()));
        return trainingService.addTraining(train, training.getImage());
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
    @PutMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public Training updateTraining(@Valid @ModelAttribute UpdateTrainingReq training){
        Training train = new Training();
        train.setId(Long.parseLong(training.getId()));
        train.setTitle(training.getTitle());
        train.setDescription(training.getDescription());
        train.setPrice(Double.parseDouble(training.getPrice()));
        train.setRating(Double.parseDouble(training.getRating()));
        train.setLikes(Integer.parseInt(training.getLikes()));
        train.setDislikes(Integer.parseInt(training.getDislikes()));
        train.setTrainingCategories(training.getTrainingCategories());
        train.setDateDebut(DateUtils.parseDate(training.getDateDebut()));
        train.setDateFin(DateUtils.parseDate(training.getDateFin()));
        return trainingService.updateTraining(train,training.getImage());
    }
    @GetMapping("/findTrainingByCategories/{trainingCategories}")
    public Set<Training> findTrainingByCategories(@PathVariable TrainingCategories trainingCategories) {
        return trainingService.findTrainingByCategories(trainingCategories);
    }
    @GetMapping()
    public ResponseEntity<Page<Training>> getAllTrainings(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
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
