import { AbstractControl, ValidatorFn } from "@angular/forms";
import { emailRegex, passwordRegex } from "../constants/regex";

export const emailRegexValidator = (): ValidatorFn => {

    return (control: AbstractControl): { [key: string]: any } | null => {
        const email: string = control.value;

        if (email && !emailRegex.test(email)) {
            return { invalidEmailFormat: true };
        }

        return null;
    };
}

export const passwordRegexValidator = (): ValidatorFn =>{

    return (control: AbstractControl): { [key: string]: any } | null => {
        const password: string = control.value;

        if (password && !passwordRegex.test(password)) {
            return { invalidPasswordFormat: true };
        }

        return null;
    };
}