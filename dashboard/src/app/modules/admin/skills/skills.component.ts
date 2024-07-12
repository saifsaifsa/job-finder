import {Component, OnInit, ViewChild, ViewEncapsulation} from '@angular/core';

import {MatPaginator} from '@angular/material/paginator';
import {MatTableDataSource} from '@angular/material/table';
import {MatDialog} from '@angular/material/dialog';

import {MatSort} from '@angular/material/sort';
import {SkillsService} from './skillsService';
import {SkillDialogComponent} from "./modals/skill-dialog.component";
import { TrainingCategories } from 'app/core/training/training.enums';

@Component({
  selector: 'skills',
  templateUrl: './skills.component.html',
  encapsulation: ViewEncapsulation.None
})
export class SkillsComponent implements OnInit {
  displayedColumns: string[] = ['Name', 'Category', 'Actions'];
  dataSource: any;
  constructor(private skillsService: SkillsService, public dialog: MatDialog) {
  }

    // eslint-disable-next-line @typescript-eslint/member-ordering
  @ViewChild(MatSort, { static: true }) sort: MatSort;
    // eslint-disable-next-line @typescript-eslint/member-ordering
  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;

    // eslint-disable-next-line @typescript-eslint/explicit-function-return-type
  ngOnInit() {
    this.refreshData();
  }
    // eslint-disable-next-line @typescript-eslint/explicit-function-return-type
  applyFilter(event: Event) {
      this.dataSource.filter = (event.target as HTMLInputElement).value.trim().toLowerCase();
  }

  public onDelete(id: any): void {
    this.skillsService.deleteSkills(id).subscribe(() => {
      this.refreshData();
    }, (error) => {
      console.error(error);
    });
  }
  private refreshData(): void {
    this.skillsService.getSkills().subscribe((data: any) => {
      this.dataSource = new MatTableDataSource(data);
        this.dataSource.sort = this.sort;
      this.dataSource.paginator = this.paginator;
    });
  }
    openDialog(action: string, element?: any): void {
        const dialogRef = this.dialog.open(SkillDialogComponent, {
            width: '250px',
            data: action === 'update' ? element : null,
        });

        dialogRef.afterClosed().subscribe(result => {
            if (result) {
                if (action === 'add') {
                    this.addSkill(result);
                } else if (action === 'update') {
                    this.updateSkill(element, result);
                }
            }
        });
    }

    addSkill(skill: any): void {
        this.skillsService.addSkills(skill).subscribe((data)=>{
            this.refreshData();
        });
    }

    updateSkill(oldSkill: any, newSkill: any): void {
        this.skillsService.updateSkills(oldSkill.id,newSkill).subscribe((data)=>{
            this.refreshData();
        });
    }
}
