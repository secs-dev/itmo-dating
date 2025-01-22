import axios from 'axios'
import React from 'react'
import { backendUrl } from '@/shared/api'
import { $authStore } from '@/features/authentication/api/authFx.ts'

export function getMatches(
  setMatchesId: React.Dispatch<React.SetStateAction<Array<number>>>,
) {
  const url = `${backendUrl}/api/people/${$authStore.getState().userId}/matches`
  const config = {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      Authorization: `Bearer ${$authStore.getState().token}`,
    },
  }
  axios
    .get(url, config)
    .then((response) => {
      console.log(response.data)
      setMatchesId(response.data)
    })
    .catch((error) => {
      console.error('Error getting matches: ', error)
      setMatchesId([])
    })
}
