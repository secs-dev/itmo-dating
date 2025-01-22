export interface GetPeopleQueryParams {
  offset: number
  limit: number
  first_name: string | null
  last_name: string | null
  pictures_count_min: number | null
  pictures_count_max: number | null
  topic_id: Array<number> | null
  height_min: number | null
  height_max: number | null
  birthday_min: string | null
  birthday_max: string | null
  zodiac: string | null
  facultyId: number | null
  latitude: number | null
  longitude: number | null
  radius: number | null
  updated_min: string | null
  updated_max: string | null
  sort_by: Array<string> | null
}
