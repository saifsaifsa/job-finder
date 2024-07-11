import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { UserService } from 'app/core/user/user.service';

@Component({
    selector     : 'stats',
    templateUrl  : './stats.component.html',
    encapsulation: ViewEncapsulation.None
})
export class StatsComponentAdmin implements OnInit 
{
    /**
     * Constructor
     */
    usersRoles:any
    totalUsers:number
    activeUsers:number
    constructor(private readonly _userService:UserService)
    {
    }
    ngOnInit(): void {
        this._userService.getStats().subscribe((data:any)=>{
            this.usersRoles = data.usersByRole
            this.totalUsers=data.totalUsers
            this.activeUsers=data.activeUsers
        })
    }

}
