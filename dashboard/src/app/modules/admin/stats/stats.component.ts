import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { CompanyService } from 'app/core/company/companyService';
import { TrainingService } from 'app/core/training/trainingService';
import { UserService } from 'app/core/user/user.service';
import { ApexChart, ApexNonAxisChartSeries, ApexPlotOptions,ApexOptions,ApexResponsive } from 'ng-apexcharts';

export type ChartOptions = {
  series: ApexNonAxisChartSeries;
  chart: ApexChart;
  labels: any;
  plotOptions: ApexPlotOptions;
  responsive: ApexResponsive[];

};
@Component({
    selector     : 'stats',
    templateUrl  : './stats.component.html',
    encapsulation: ViewEncapsulation.None
})
export class StatsComponentAdmin implements OnInit 
{
    public userChart: Partial<ChartOptions>;
    public companyChart: Partial<ChartOptions>;

    /**
     * Constructor
     */
    usersRoles:any
    totalUsers:number
    activeUsers:number
    usersRolePieChart:any
    constructor(private readonly _userService:UserService, private readonly _trainingService:TrainingService, private readonly _companyService:CompanyService )
    {
    }
    statistics: any;
    totalTrainings: number = 0;
    totalCompanies: number = 0;
    totalOffres: number = 0;
    mostLikedTrainings: any[];
    mostLikedCompanies: any[];
    mostPopularIndustry: any[];
    numberOfCompaniesPerIndustry: any[];
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

          this.companyChart = {
            series : [],
            labels :[],
            chart: {
              width: 380,
              type: "pie"
            },
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
            this.usersRolePieChart = Object.keys(data.usersByRole).map(key => ({ name:key, value: data.usersByRole[key] }));
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


        this._companyService.getStats().subscribe((data:any) => {
          this.statistics = data|| {};
          this.totalCompanies = this.statistics.totalCompanies|| 0;
          this.totalOffres = this.statistics.totalOffres|| 0;


          this.mostLikedCompanies = (this.statistics.mostLikedCompanies || []).map(item => ({ name: item.name, value: item.rating }));
          this.mostPopularIndustry = (this.statistics.mostPopularIndustry || []).map(item => ({ name: item[0], value: item[1] }));
          this.numberOfCompaniesPerIndustry = (this.statistics.numberOfCompaniesPerIndustry || []).map(item => ({ name: item[0], value: item[1] }));



    


        });


    }
}
