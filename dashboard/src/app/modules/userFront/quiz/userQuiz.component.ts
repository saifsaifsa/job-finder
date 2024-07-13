import { Component, ViewEncapsulation, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSort } from '@angular/material/sort';
import { QuizzesService } from 'app/modules/admin/quizz/quizz.service';
import { UserService } from 'app/core/user/user.service';

@Component({
  selector: 'user-quiz',
  templateUrl: './userQuiz.component.html',
  encapsulation: ViewEncapsulation.None
})
export class UserQuizzComponent implements OnInit {
  displayedColumns: string[] = ["Name","Actions"];
  dataSource: any;
  skills=[]
  constructor(private quizService: QuizzesService,private userService: UserService) {
  }

  @ViewChild(MatSort, { static: true }) sort: MatSort;
  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;

  ngOnInit() {
    this.refreshData();
  }
  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value.trim().toLowerCase();
    this.dataSource.filter = filterValue;
  }
  trackByFn(index: number, item: any): any
  {
      return item.id || index;
  }
  private refreshData(): void {
    // get user skills
    // get quizzes By userSkills
    this.userService.getLoggedInUser().subscribe((user)=>{
        const data = []
        console.log("user.skills: ",user.skills);
        
        this.skills = user.skills
        user.skills.map((skill:any)=>{
            this.quizService.getQuizzes(skill.id).subscribe((res: any) => {
                res.forEach((r)=>{
                  return {...r,skillName:skill.name}
                })
                console.log("res:",res);
                
                data.push(...res)
            });
        })        
        this.dataSource= data
        console.log(this.dataSource);
        
        // this.dataSource = new MatTableDataSource(data);
        //     this.dataSource.sort = this.sort;
        //     this.dataSource.paginator = this.paginator;
    },(err)=>{
        console.log(err);
        
    })
  }
}



