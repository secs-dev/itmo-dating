export interface PersonDraft {
    firstName: string,
    lastName: string,
    pictures: Picture[],
    interests: Interest[],
    height: number,
    birthday: Date,
    faculty: string,
    location: Location
}

interface Picture {
    id: number,
    small: string,
    medium: string | null,
    large: string | null
}

interface Interest {
    topicId: number,
    level: number
}

interface Location {
    name: string,
    coordinates: Coordinates

}

interface Coordinates {
    latitude: number,
    longitude: number
}