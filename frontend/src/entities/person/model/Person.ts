import { Picture, PictureMessage } from './Picture.ts'
import { Interest, InterestMessage, InterestV2 } from './Interest.ts'
import { Location } from './Location.ts'
import { PersonStatusMessage } from '@/entities/person/model/Status.ts'
import { ZodiacSignMessage } from '@/entities/person/model/ZodiacSign.ts'

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
  firstName: string
  lastName: string
  pictures: PictureMessage[]
  interests: InterestV2[]
  height: number
  birthday: string
  facultyId: number
  locationId: number
}

export interface PersonDraftV2 {
  status: PersonStatusMessage
  userId: number
  firstName: string | null
  lastName: string | null
  interests: Array<InterestMessage> | null
  height: number | null
  birthday: string | null
  facultyId: number | null
  locationId: number | null
  pictures: Array<PictureMessage> | null
}

export interface PersonV2 {
  firstName: string
  lastName: string
  interests: Array<InterestMessage>
  height: number
  birthday: string
  pictures: Array<PictureMessage>
  zodiac: ZodiacSignMessage
  status: PersonStatusMessage
  userId: number
  facultyId: number | null
  locationId: number | null
}

export interface PersonPatchV2 {
  status: PersonStatusMessage
  firstName: string | null
  lastName: string | null
  interests: Array<InterestMessage> | null
  height: number | null
  birthday: string | null
  facultyId: number | null
  locationId: number | null
  pictures: Array<PictureMessage> | null
}
