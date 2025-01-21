import axios from 'axios'
import React from 'react'
import { backendUrl } from '@/shared/api'
import { $authStore } from '@/features/authentication/api/authFx.ts'
import { SearchPerson } from '@/entities/person/model/Person.ts'

export function getPeople(
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
      offset: 0,
      limit: 8,
      // first_name: "aaa",
      // last_name: "aaa",
      // pictures_count_min: 0,
      // pictures_count_max: 4,
      // topic_id: [],
      // height_min: 50,
      // height_max: 280,
      // birthday_min: "1990-01-01",
      // birthday_max: "2030-01-01",
      // zodiac: "aries",
      // faculty: [],
      // latitude: 0.0,
      // longitude: 0.0,
      // radius: 9999,
      // updated_min: "2024-04-14T13:32:42Z",
      // updated_max: "2024-04-14T13:32:42Z",
      // sort_by: []
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
