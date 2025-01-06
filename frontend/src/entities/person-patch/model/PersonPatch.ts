export interface PersonPatch {
  status: string
  firstName: string
  lastName: string
  interests: Topic[]
  height: number
  birthday: string
  facultyId: number
  locationId: number
}

export interface Topic {
  topicId: number
  level: number
}
