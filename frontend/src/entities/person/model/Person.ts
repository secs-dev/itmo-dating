import { Picture } from './Picture.ts'
import { Interest } from './Interest.ts'
import { Location } from './Location.ts'

export interface Person {
  id: number
  zodiac: string
  updateMoment: Date
  firstName: string
  lastName: string
  pictures: Picture[]
  interests: Interest[]
  height: number
  birthday: Date
  faculty: string
  location: Location
}

export interface PersonLegacy {
  status: string
  userId: number
  firstName: string
  zodiac: string
  lastName: string
  interests: Interest[]
  height: number
  birthday: Date
  facultyId: number
  locationId: number
}

export interface SearchPerson {
  userId: number
  zodiac: string
  updateMoment: Date
  firstName: string
  lastName: string
  pictures: Picture[]
  interests: Interest[]
  height: number
  birthday: Date
  faculty: string
  location: Location
}
