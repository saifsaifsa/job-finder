export interface User
{
    id: string;
    name: string;
    email: string;
    avatar?: string;
    status?: string;
    profilePicture?:string
    role?:string
    skills?:[]
}
