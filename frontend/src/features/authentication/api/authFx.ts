import { createEffect, createStore } from 'effector'
import { persist } from 'effector-storage/session'
import { AuthRequest } from '../model/AuthRequest.ts'
import { AuthResponse } from '../model/AuthResponse.ts'
import axios from 'axios'
import { AuthState, initialAuthState } from '@/entities'
import { backendUrl } from '@/shared/api'

export const $authStore = createStore<AuthState>(initialAuthState)
export const $userIdStore = createStore<number | null>(null)

export const $userRegisteredStore = createStore<boolean>(false)

persist({ store: $authStore, key: 'authStore' })
persist({ store: $userIdStore, key: 'userIdStore' })
persist({ store: $userRegisteredStore, key: 'userRegisteredStore' })

export const authFx = createEffect<AuthRequest, AuthState, Error>({
  handler: async (authReq) => {
    try {
      const response = await axios.put<AuthResponse>(
        `${backendUrl}/api/auth/telegram/web-app`,
        JSON.stringify(authReq),
        {
          method: 'PUT',
          headers: {
            'Content-Type': 'application/json',
          },
        },
      )
      return fillAuthState(response.data)
    } catch (error) {
      console.error(error)
      throw new Error('Auth failed')
    }
  },
})

const fillAuthState = (response: AuthResponse): AuthState => {
  return {
    token: response.access,
    loading: false,
    tgId: '-1',
    isAuthenticated: true,
    error: null,
  }
}

export const $isAuthenticated = $authStore.map((state) => state.isAuthenticated)

$authStore.on(authFx.doneData, (_, result) => result)
$authStore.on(authFx.failData, (_, error) => ({
  ...initialAuthState,
  loading: false,
  error: error.message,
}))

export const userIdFx = createEffect<number | null, number | null, Error>({
  handler: async (x) => {
    if (x == null) throw Error('x is null')
    return x
  },
})

export const userRegisteredFx = createEffect<boolean, boolean, Error>({
  handler: async (x) => {
    if (!x) throw Error('user not registered')
    return x
  },
})

$userIdStore.on(userIdFx.doneData, (_, result) => result)
$userIdStore.on(userIdFx.failData, (_, __) => {
  return null
})
$userRegisteredStore.on(userRegisteredFx.doneData, (_, result) => result)
$userRegisteredStore.on(userRegisteredFx.failData, (_, __) => {
  return false
})
