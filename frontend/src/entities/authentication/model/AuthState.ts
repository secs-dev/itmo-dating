import {JwtToken} from "@/shared/api";

export interface AuthState {
    token: JwtToken | null;
    isAuthenticated: boolean;
    tgId: string | null;
    loading: boolean;
    error: string | null;
}

export const initialAuthState: AuthState = {
    token: null,
    isAuthenticated: false,
    tgId: null,
    loading: false,
    error: null,
};
