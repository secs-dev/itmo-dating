import { Interest } from './interest.ts'
import { Picture } from './picture.ts'

export type RegistrationData = {
    tgId: string | null;
    name: string | null;
    surname: string | null;
    height: number | null;
    facultyId: number | null;
    birthday: Date | null;
    pictures: Picture[] | null;
    interests: Interest[] | null;
    locationId: number | null;
};
