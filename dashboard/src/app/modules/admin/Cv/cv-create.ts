import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormArray } from '@angular/forms';
import { CvService } from '../Cv/CvService'; 

@Component({
  selector: 'app-cv-create',
  templateUrl: './cv-create.component.html',
  styleUrls: ['./cv-create.component.scss']
})
export class CvCreateComponent implements OnInit {
  cvForm: FormGroup;

  constructor(private fb: FormBuilder, private cvService: CvService) { }

  ngOnInit(): void {
    this.cvForm = this.fb.group({
      name: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      skills: this.fb.array([this.createSkill()])
    });
  }

  get skills(): FormArray {
    return this.cvForm.get('skills') as FormArray;
  }

  createSkill(): FormGroup {
    return this.fb.group({
      skill: ['', Validators.required]
    });
  }

  addSkill(): void {
    this.skills.push(this.createSkill());
  }

  removeSkill(index: number): void {
    this.skills.removeAt(index);
  }

  onSubmit(): void {
    if (this.cvForm.valid) {
      const formValue = this.cvForm.value;
      const cvData = {
        name: formValue.name,
        email: formValue.email,
        skills: formValue.skills.map(skill => skill.skill)
      };

      this.cvService.createCv(cvData).subscribe(
        response => {
          console.log('CV created successfully', response);
        },
        error => {
          console.error('Error creating CV:', error);
        }
      );
    } else {
      console.log('Form is invalid');
    }
  }
}
