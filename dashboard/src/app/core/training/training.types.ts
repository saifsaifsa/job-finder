export interface Training{
    id: Number;
    title:  String;
    description:  String;
    trainingCategories: String;
    rating: Number;
    price: Number;
    likes: Number;
    dislikes: Number;
    dateDebut: Date;
    dateFin: Date;
    image?: String;
}