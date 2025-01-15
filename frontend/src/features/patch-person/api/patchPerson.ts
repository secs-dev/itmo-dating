import axios, { AxiosResponse } from 'axios'
import { backendPeopleUrl } from '@/shared/api'
import { RegistrationData } from '@/entities'
import {
  $authStore,
  $userIdStore,
} from '@/features/authentication/api/authFx.ts'
import { PersonPatch } from '@/entities/person-patch/model/PersonPatch.ts'
import { IError } from '@/shared/model'

export function patchPerson(
  registartionData: RegistrationData,
  status: string,
): Promise<AxiosResponse<void | IError>> {
  const url = `${backendPeopleUrl}/api/people/${$userIdStore.getState()}`
  const config = {
    method: 'PATCH',
    headers: {
      'Content-Type': 'application/json',
      Authorization: `Bearer ${$authStore.getState().token}`,
    },
  }
  const birthday = registartionData.birthday || new Date()
  const patchData: PersonPatch = {
    status: status,
    firstName: registartionData.name,
    lastName: registartionData.surname,
    interests: registartionData.interests,
    height: registartionData.height,
    birthday: birthday.toISOString().split('T')[0],
    facultyId: registartionData.facultyId,
    locationId: registartionData.locationId,
  }

  return axios.patch(url, JSON.stringify(patchData), config)
}
