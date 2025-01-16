import axios from 'axios'
import { backendUrl } from '@/shared/api'
import { $authStore } from '@/features/authentication/api/authFx.ts'

export function setAttitude(personId: number, attitude: string) {
  const url = `${backendUrl}/api/people/${personId}/attitudes/incoming/${attitude}`

  const basicAuth = `Bearer ${$authStore.getState().token}`
  const config = {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      Authorization: basicAuth,
    },
  }
  axios
    .post(url, null, config)
    .then((response) => {
      console.log(response.data)
    })
    .catch((error) => {
      console.error('Error setting attitude: ', error)
    })
}
