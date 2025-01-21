import { PersonDraftV2, PersonV2 } from '@/entities/person/model/Person.ts'
import { backendUrl } from '@/shared/api'
import axios, { AxiosResponse } from 'axios'
import { $authStore } from '@/features/authentication/api/authFx.ts'
import { IError } from '@/shared/model'

export function getUser(
  userId: number,
): Promise<AxiosResponse<PersonDraftV2 | PersonV2 | IError>> {
  const url = `${backendUrl}/api/people/${userId}`

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
