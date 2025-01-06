import { createEffect, createStore } from 'effector'
import { persist } from 'effector-storage/session'
import { AuthRequest } from '../model/AuthRequest.ts'
import { AuthResponse } from '../model/AuthResponse.ts'
import axios from 'axios'
import { AuthState, initialAuthState } from '@/entities'
import { backendAuthikUrl } from '@/shared/api'

export const $authStore = createStore<AuthState>(initialAuthState)

persist({ store: $authStore, key: 'authStore' })

export const authFx = createEffect<AuthRequest, AuthState, Error>({
  handler: async (authReq) => {
    try {
      const response = await axios.put<AuthResponse>(
        `${backendAuthikUrl}/api/auth/telegram/web-app`,
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
