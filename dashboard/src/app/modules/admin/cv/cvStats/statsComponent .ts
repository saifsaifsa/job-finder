import { Component, OnInit } from '@angular/core';
import { StatsService } from '../cvStats/statsService'; 
@Component({
  selector: 'app-stats',
  templateUrl: './stats.component.html'
})
export class StatsComponent implements OnInit {
  statistics: any;
  error: any; // Define error property

  constructor(private statsService: StatsService) { }

  ngOnInit(): void {
    this.loadStatistics();
  }

  loadStatistics(): void {
    this.statsService.getCvStatistics().subscribe(
      (data: any) => {
        this.statistics = data;
      },
      (error: any) => {
        console.error('Error fetching statistics:', error);
        this.error = error; // Assign error to the error property for display in the template
      }
    );
  }
}
