import axios from 'axios'
import { backendUrl } from '@/shared/api'

export function setAttitude(personId: number, attitude: string) {
  const url = `${backendUrl}/api/people/${personId}/attitudes/incoming/${attitude}`

  // FIXME jwt token
  const basicAuth = 'test basic auth'
  const config = {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      Authorization: +basicAuth,
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
