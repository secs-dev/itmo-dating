import axios from 'axios'
import React from 'react'
import { backendUrl } from '@/shared/api'
import { Faculty } from '@/entities/registration-data/model/faculty.ts'

export function getFaculties(
  setFaculties: React.Dispatch<React.SetStateAction<Array<Faculty>>>,
) {
  const url = `${backendUrl}/api/faculties`

  axios
    .get(url)
    .then((response) => {
      console.log(response.data)
      //FIXME after integration with backend
      setFaculties(response.data)
    })
    .catch((error) => {
      console.error('Error getting faculties: ', error)
      //TODO remove after integration with backend
      setFaculties([{ id: 1, longName: 'piict' }])
    })
}
