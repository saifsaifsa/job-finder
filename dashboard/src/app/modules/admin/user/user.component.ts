import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { fuseAnimations } from '@fuse/animations';
import { FuseAlertType } from '@fuse/components/alert';
import { UserService } from 'app/core/user/user.service';
import { ConfirmDialogComponent } from 'app/shared/dialog/confirm-dialog.component';

@Component({
    selector: 'app-user',
    templateUrl: './user.component.html',
    encapsulation: ViewEncapsulation.None,
    animations: fuseAnimations,
})
export class UserComponent implements OnInit {
    usersDataSource: MatTableDataSource<any> = new MatTableDataSource();
    recentTransactionsTableColumns: string[] = [
        'name',
        'email',
        'phone',
        'role',
        'status',
        'actions',
    ];
    roles = {
        0: 'Client',
        1: 'Teacher',
        2: 'Admin',
    };
    alert: { type: FuseAlertType; message: string } = {
        type: 'success',
        message: '',
    };
    showAlert: boolean = false;
    filterValues: { [key: string]: string } = {
        email: '',
        name: '',
        role: '',
        phone: '',
    };
    sortField: string = '';
    sortOrder: 'asc' | 'desc' = 'asc';
    pageSize: number = 10;
    currentPage: number = 1;
    totalItems:number=0
    authentifiedUser:any
    ngOnInit(): void {
        this.getUsers();
        this.userService.getLoggedInUser().subscribe((user)=>{
            this.authentifiedUser = user
        })
    }
    /**
     * Constructor
     */
    constructor(
        private userService: UserService,
        private dialog: MatDialog,
        private router: Router
    ) {}

    trackByFn(index: number, item: any): any {
        return item.id || index;
    }
    getUsers() {
        this.userService
            .getUsers(
                this.currentPage,
                this.pageSize,
                this.filterValues,
                this.sortField,
                this.sortOrder
            )
            .subscribe(
                (data: any) => {
                    this.usersDataSource.data = data;
                    this.totalItems = data.total
                },
                (err) => {
                    console.log('errors: ', err);
                }
            );
    }
    applyFilters() {
        this.currentPage = 1;
        this.getUsers();
    }
    applySort(sort: { active: string; direction: 'asc' | 'desc' }) {
        this.sortField = sort.active;
        this.sortOrder = sort.direction;
        this.getUsers();
    }

    onPageChange(event: any) {
        this.currentPage = event.pageIndex + 1;
        this.pageSize = event.pageSize;
        this.getUsers();
    }
    goToAddUser() {
        this.router.navigateByUrl('/add');
    }
    openConfirmationDialog(userId: string): void {
        //FuseConfirmationDialogComponent
        const dialogRef = this.dialog.open(ConfirmDialogComponent, {
            width: '400px',
            data: { userId },
        });

        dialogRef.afterClosed().subscribe((result) => {
            if (result === true) {
                this.showAlert = false;
                if (this.authentifiedUser._id == userId) {
                    this.alert = {
                        type: 'error',
                        message: "You can't delete your owen account",
                    };
                    this.showAlert = true;
                    return;
                }
                this.userService.deleteUser(userId).subscribe((res) => {
                    this.usersDataSource.data =
                        this.usersDataSource.data.filter((u) => {
                            return u._id != userId;
                        });
                });
            }
        });
    }
    toggleConfirmation(userId: string, value: any) {
        this.showAlert = false;
        if (this.authentifiedUser._id == userId) {
            value.source.checked = true;
            this.alert = {
                type: 'error',
                message: "You can't disable your owen account",
            };
            this.showAlert = true;
            return;
        }
        this.userService.toggleConfirmation(userId, value.checked).subscribe(
            (res) => {},
            (error) => {
                // TODO Handle the error
            }
        );
    }
}
