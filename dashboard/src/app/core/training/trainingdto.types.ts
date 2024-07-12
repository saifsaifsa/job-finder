import { TrainingCategories } from "./training.enums";

export interface TrainingDTO{
    id: Number;
    title:  String;
    description:  String;
    trainingCategories: TrainingCategories;
    price: Number;
    dateDebut: Date;
    dateFin: Date;
    image?: String;
}