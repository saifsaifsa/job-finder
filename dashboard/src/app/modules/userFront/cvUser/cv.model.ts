import { Skill } from './skill.model';

export interface Cv {
  id: number;
  userId: number;
  content: string;
  views: number;
  downloads: number;
  skills: Skill[];
}
