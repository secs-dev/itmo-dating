import { retrieveLaunchParams } from '@telegram-apps/sdk'
import { authFx, prepareInitDataRaw } from '@/features'
import { AuthState } from '@/entities'

export function authenticate(): Promise<AuthState> {
  const { initDataRaw } = retrieveLaunchParams()
  const { tgInitDataRaw, hash } = prepareInitDataRaw(initDataRaw)

  return authFx({ string: tgInitDataRaw, hash: hash })
}
