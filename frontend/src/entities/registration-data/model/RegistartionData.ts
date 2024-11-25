import {Interest} from "./interest.ts";
import {Picture} from "./picture.ts";

export type RegistrationData = {
    tgId: string;
    name: string;
    surname: string;
    height: number;
    faculty: string;
    birthday: Date;
    pictures: Picture[],
    interests: Interest[]
};
