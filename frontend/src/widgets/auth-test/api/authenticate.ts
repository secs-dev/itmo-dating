import { retrieveLaunchParams } from '@telegram-apps/sdk'
import { authFx, prepareInitDataRaw } from '@/features'

export function authenticate() {
  const { initDataRaw } = retrieveLaunchParams()
  const { tgInitDataRaw, hash } = prepareInitDataRaw(initDataRaw)

  authFx({ string: tgInitDataRaw, hash: hash })
    .then(() => {
      console.log('auth success')
    })
    .catch((reason) => {
      console.log('auth error')
      console.log(reason)
    })
}
