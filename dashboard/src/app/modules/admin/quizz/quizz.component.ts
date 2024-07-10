import {Component, ViewChild, ViewEncapsulation} from '@angular/core';
import {MatDialog} from "@angular/material/dialog";
import {MatSort} from "@angular/material/sort";
import {MatPaginator} from "@angular/material/paginator";
import {MatTableDataSource} from "@angular/material/table";
import {SkillDialogComponent} from '../skills/modals/skill-dialog.component';
import {QuizzesService} from "./quizz.service";
import {ActivatedRoute} from "@angular/router";

@Component({
    selector     : 'quizz',
    templateUrl  : './quizz.component.html',
    encapsulation: ViewEncapsulation.None
})
export class QuizzComponent
{
    displayedColumns: string[] = ['Name', 'Category', 'Actions'];
    dataSource: any;
    constructor(private quizzesService: QuizzesService, public dialog: MatDialog,
                private route: ActivatedRoute, ) {
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
        this.quizzesService.deleteQuizzes(id).subscribe(() => {
            this.refreshData();
        }, (error) => {
            console.error(error);
        });
    }
    private refreshData(): void {
        this.route.params.subscribe((params) => {
            const skillId = params['id'];
            this.quizzesService.getQuizzes(skillId).subscribe((data: any) => {
                this.dataSource = new MatTableDataSource(data);
                this.dataSource.sort = this.sort;
                this.dataSource.paginator = this.paginator;
            });
        });
    }
}
