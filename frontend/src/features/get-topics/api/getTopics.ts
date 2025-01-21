import axios from 'axios'
import { Topic } from '@/entities/registration-data/model/topic.ts'
import React from 'react'
import { backendUrl } from '@/shared/api'
import { $authStore } from '@/features/authentication/api/authFx.ts'

export function getTopics(
  setTopics: React.Dispatch<React.SetStateAction<Array<Topic>>>,
) {
  const url = `${backendUrl}/api/topics`
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
      //FIXME after integration with backend
      setTopics(response.data)
    })
    .catch((error) => {
      console.error('Error getting topics: ', error)
      //TODO remove after integration with backend
      setTopics([
        { id: 1, name: 'programming', color: '#BCA9E1' },
        { id: 2, name: 'eating', color: '#BCA9E1' },
        { id: 3, name: 'sleeping', color: '#BCA9E1' },
      ])
    })
}
