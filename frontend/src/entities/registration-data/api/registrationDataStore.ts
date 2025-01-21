import { createEffect, createStore } from 'effector'
import { RegistrationData } from '@/entities'
import { persist } from 'effector-storage/session'

export const $registrationDataStore = createStore<RegistrationData>({
  firstName: null,
  lastName: null,
  height: null,
  facultyId: null,
  birthday: null,
  pictures: null,
  interests: null,
  locationId: null,
})

persist({ store: $registrationDataStore, key: 'registrationDataStore' })

export const registrationDataFx = createEffect<
  RegistrationData,
  RegistrationData,
  Error
>({
  handler: async (regData) => {
    return regData
  },
})

$registrationDataStore.on(registrationDataFx.doneData, (_, result) => result)
$registrationDataStore.on(registrationDataFx.failData, (rd, _error) => rd)
