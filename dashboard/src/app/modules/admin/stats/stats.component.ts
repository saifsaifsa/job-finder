import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { UserService } from 'app/core/user/user.service';
import { ApexChart, ApexNonAxisChartSeries, ApexPlotOptions,ApexOptions } from 'ng-apexcharts';

export type ChartOptions = {
  series: ApexNonAxisChartSeries;
  chart: ApexChart;
  labels: any;
  plotOptions: ApexPlotOptions;
};
@Component({
    selector     : 'stats',
    templateUrl  : './stats.component.html',
    encapsulation: ViewEncapsulation.None
})
export class StatsComponentAdmin implements OnInit 
{
    public userChart: Partial<ChartOptions>;
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
        
        this.userChart = {
            series: [],
            chart: {
              type: 'pie',
            },
            labels: [],
            plotOptions: {
              pie: {
                expandOnClick: true
              }
            }
          };
        this._userService.getStats().subscribe((data:any)=>{
            this.usersRoles = data.usersByRole
            this.totalUsers=data.totalUsers
            this.activeUsers=data.activeUsers
            this.userChart.series = Object.values(data.usersByRole);
            this.userChart.labels = Object.keys(data.usersByRole);
        })
    }
}
