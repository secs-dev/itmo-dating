import { JwtToken } from '@/shared/api'

export interface AuthState {
  token: JwtToken | null
  isAuthenticated: boolean
  userId: string | null
  loading: boolean
  error: string | null
}

export const initialAuthState: AuthState = {
  token: null,
  isAuthenticated: false,
  userId: null,
  loading: false,
  error: null,
}
