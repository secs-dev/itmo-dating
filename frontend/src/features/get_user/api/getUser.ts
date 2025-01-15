import { PersonLegacy } from '@/entities/person/model/Person.ts'
import { backendPeopleUrl } from '@/shared/api'
import axios, { AxiosResponse } from 'axios'
import { $authStore } from '@/features/authentication/api/authFx.ts'

export function getUser(userId: number): Promise<AxiosResponse<PersonLegacy>> {
  const url = `${backendPeopleUrl}/api/people/${userId}`

  // FIXME jwt token
  const basicAuth = `Bearer ${$authStore.getState().token}`
  const config = {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      Authorization: basicAuth,
    },
  }

  return axios.get(url, config)
}
