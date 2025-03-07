import axios from 'axios'
import React from 'react'
import { backendUrl } from '@/shared/api'
import { Faculty } from '@/entities/registration-data/model/faculty.ts'
import { $authStore } from '@/features/authentication/api/authFx.ts'

export function getFaculties(
  setFaculties: React.Dispatch<React.SetStateAction<Array<Faculty>>>,
) {
  const url = `${backendUrl}/api/faculties`
  const basicAuth = `Bearer ${$authStore.getState().token}`
  const config = {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      Authorization: basicAuth,
    },
  }

  axios
    .get(url, config)
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
