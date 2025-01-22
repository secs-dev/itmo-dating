import axios, { AxiosResponse } from 'axios'
import { backendUrl } from '@/shared/api'
import { RegistrationData } from '@/entities'
import { $authStore } from '@/features/authentication/api/authFx.ts'
import { IError } from '@/shared/model'
import { PersonPatchV2 } from '@/entities/person/model/Person.ts'
import { PersonStatusMessage } from '@/entities/person/model/Status.ts'
import {
  InterestLevelMessage,
  InterestMessage,
} from '@/entities/person/model/Interest.ts'
import { PictureMessage } from '@/entities/person/model/Picture.ts'

export function patchPerson(
  registartionData: RegistrationData,
  status: PersonStatusMessage,
): Promise<AxiosResponse<void | IError>> {
  console.log('patch fun', registartionData)

  const url = `${backendUrl}/api/people/${$authStore.getState().userId}`
  const config = {
    method: 'PATCH',
    headers: {
      'Content-Type': 'application/json',
      Authorization: `Bearer ${$authStore.getState().token}`,
    },
  }
  const birthday = registartionData.birthday || new Date()

  let pictures: Array<PictureMessage> | null
  if (registartionData?.pictures) {
    pictures =
      registartionData?.pictures?.map((p) => {
        return { id: p.id }
      }) || null
  } else {
    pictures = null
  }
  console.log(
    'int level',
    InterestLevelMessage['_1' as keyof typeof InterestLevelMessage],
  )
  let interests: Array<InterestMessage> | null
  if (registartionData?.interests) {
    interests =
      registartionData.interests?.map((i) => {
        const level: InterestLevelMessage =
          InterestLevelMessage[
            `_${i.level.toString()}` as keyof typeof InterestLevelMessage
          ]
        return { topicId: i.topicId, level: level }
      }) || null
  } else {
    interests = null
  }
  const patchData: PersonPatchV2 = {
    status: PersonStatusMessage[status],
    firstName: registartionData.firstName || null,
    lastName: registartionData.lastName || null,
    interests: interests,
    height: registartionData.height || null,
    birthday: birthday.toISOString().split('T')[0] || null,
    facultyId: registartionData.facultyId || null,
    locationId: registartionData?.locationId || null,
    pictures: pictures,
  }

  console.log('patch sent', patchData)
  return axios.patch(url, JSON.stringify(patchData), config)
}
