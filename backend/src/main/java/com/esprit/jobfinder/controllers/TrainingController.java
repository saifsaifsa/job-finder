package com.esprit.jobfinder.controllers;

import com.esprit.jobfinder.models.Training;
import com.esprit.jobfinder.models.enums.TrainingCategories;
import com.esprit.jobfinder.services.ITrainingService;
import com.esprit.jobfinder.services.ITrainingServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("training")
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
    @GetMapping()
    public List<Training> getAllTraining(){
        return trainingService.getAll();
    }
    @DeleteMapping("{id}")
    public void deleteTraining(@PathVariable long id){
        trainingService.DeleteTraining(id);
    }
    @PutMapping("update")
    public Training updateTraining(@RequestBody Training training){
        return trainingService.updateTraining(training);
    }
    @GetMapping("/findTrainingByCategories/{trainingCategories}")
    public Set<Training> findTrainingByCategories(@PathVariable TrainingCategories trainingCategories) {
        return trainingService.findTrainingByCategories(trainingCategories);
    }

}
