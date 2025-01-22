import { Interest } from './interest.ts'
import { Picture } from './picture.ts'

export type RegistrationData = {
  firstName: string | null
  lastName: string | null
  height: number | null
  facultyId: number | null
  birthday: Date | null
  pictures: Picture[] | null
  interests: Interest[] | null
  locationId: number | null
}
