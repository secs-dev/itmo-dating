import { getTgUser, TgUser } from '@/entities'
import { retrieveLaunchParams } from '@telegram-apps/sdk'

export function fetchTgUser(): TgUser | null {
  const { initData } = retrieveLaunchParams()
  return getTgUser(initData?.user)
}
