import { type User } from '@telegram-apps/sdk'
import { TgUser } from '../model/TgUser.ts'

export function getTgUser(user: User | undefined): TgUser | null {
  if (user == undefined) return null
  return {
    id: user.id.toString(),
    username: user.username || null,
    firstName: user.firstName,
    lastName: user.lastName || null,
    languageCode: user.languageCode || null,
  }
}
