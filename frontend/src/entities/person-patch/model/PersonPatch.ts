export interface PersonPatch {
    status: string | null,
    firstName: string | null,
    lastName: string | null,
    interests: Topic[] | null,
    height: number | null,
    birthday: string | null,
    facultyId: number | null,
    locationId: number | null
}

export interface Topic {
    topicId: number,
    level: number
}
