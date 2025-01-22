import axios from 'axios'
import React from 'react'
import { backendUrl } from '@/shared/api'
import { $authStore } from '@/features/authentication/api/authFx.ts'
import { SearchPerson } from '@/entities/person/model/Person.ts'
import { GetPeopleQueryParams } from '@/features/get-people/model/getPeopleQueryParams.ts'

export function getPeople(
  getPeopleQueryParams: GetPeopleQueryParams,
  setFoundPeople: React.Dispatch<React.SetStateAction<Array<SearchPerson>>>,
) {
  const url = `${backendUrl}/api/people`
  const config = {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      Authorization: `Bearer ${$authStore.getState().token}`,
    },
    params: {
      offset: getPeopleQueryParams.offset,
      limit: getPeopleQueryParams.limit,
      first_name: getPeopleQueryParams.first_name,
      last_name: getPeopleQueryParams.last_name,
      pictures_count_min: getPeopleQueryParams.pictures_count_min,
      pictures_count_max: getPeopleQueryParams.pictures_count_max,
      topic_id: getPeopleQueryParams.topic_id,
      height_min: getPeopleQueryParams.height_min,
      height_max: getPeopleQueryParams.height_max,
      birthday_min: getPeopleQueryParams.birthday_min,
      birthday_max: getPeopleQueryParams.birthday_max,
      zodiac: getPeopleQueryParams.zodiac,
      facultyId: getPeopleQueryParams.facultyId,
      latitude: getPeopleQueryParams.latitude,
      longitude: getPeopleQueryParams.longitude,
      radius: getPeopleQueryParams.radius,
      updated_min: getPeopleQueryParams.updated_min,
      updated_max: getPeopleQueryParams.updated_max,
      sort_by: getPeopleQueryParams.sort_by,
    },
  }
  axios
    .get(url, config)
    .then((response) => {
      console.log(response.data)
      setFoundPeople(response.data)
    })
    .catch((error) => {
      console.error('Error getting people: ', error)
      setFoundPeople([])
    })
}
