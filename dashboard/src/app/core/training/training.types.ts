import { TrainingCategories } from "./training.enums";

export interface Training{
    id: Number;
    title:  String;
    description:  String;
    trainingCategories: TrainingCategories;
    rating: number;
    price: Number;
    likes: number;
    dislikes: number;
    dateDebut: Date;
    dateFin: Date;
    image?: String;
}