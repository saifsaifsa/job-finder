import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { TrainingService } from 'app/core/training/trainingService';
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
    constructor(private readonly _userService:UserService, private readonly _trainingService:TrainingService )
    {
    }
    statistics: any;
    totalTrainings: number = 0;
    mostLikedTrainings: any[];
    mostDislikedTrainings: any[];
    mostPopularCategories: any[];
    averagePricePerCategory: any[];
    upcomingTrainings: any[];
    recentlyFinishedTrainings: any[];
    durationOfTrainings: any[];
    numberOfTrainingsPerCategory: any[];
    averageDurationPerCategory: any[];
    monthlyEngagementTrends: any[];
    ratingTrendsOverTime: any[];
    colorScheme = {
      domain: ['#5AA454', '#A10A28', '#C7B42C', '#AAAAAA']
    };

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
        });
        this._trainingService.getStats().subscribe(data => {
          this.statistics = data|| {};
          this.totalTrainings = this.statistics.totalTrainings|| 0;
          this.mostLikedTrainings = (this.statistics.mostLikedTrainings || []).map(item => ({ name: item.title, value: item.likes }));
          this.mostDislikedTrainings = (this.statistics.mostDislikedTrainings || []).map(item => ({ name: item.title, value: item.dislikes }));
          this.mostPopularCategories = (this.statistics.mostPopularCategories || []).map(item => ({ name: item[0], value: item[1] }));
          this.averagePricePerCategory = (this.statistics.averagePricePerCategory || []).map(item => ({ name: item[0], value: item[1] }));
          this.averageDurationPerCategory = (this.statistics.averageDurationPerCategory || []).map(item => ({ name: item[0], value: item[1] }));
          this.numberOfTrainingsPerCategory = (this.statistics.numberOfTrainingsPerCategory || []).map(item => ({ name: item[0], value: item[1] }));
          this.durationOfTrainings = (this.statistics.durationOfTrainings || []).map(item => ({ name: item[0], value: item[1] }));
          this.monthlyEngagementTrends = (this.statistics.monthlyEngagementTrends || []).map((item, index) => ({
            name: `Month ${index + 1}`,
            series: [
              { name: 'Likes', value: item[1] },
              { name: 'Dislikes', value: item[2] }
            ]
          }));
          this.ratingTrendsOverTime = (this.statistics.ratingTrendsOverTime || []).map(item => ({ name: item[0], value: item[1] }));
        });
    }
}
