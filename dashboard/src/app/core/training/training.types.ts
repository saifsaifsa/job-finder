import { TrainingCategories } from "./training.enums";

export interface Training{
    id: Number;
    title:  String;
    description:  String;
    trainingCategories: TrainingCategories;
    rating: Number;
    price: Number;
    likes: Number;
    dislikes: Number;
    dateDebut: Date;
    dateFin: Date;
    image?: String;
}