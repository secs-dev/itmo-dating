import {createEffect, createStore} from 'effector';
import { persist } from 'effector-storage/session'
import {AuthRequest, AuthResponse} from '../model'
import axios from "axios";
import {AuthState, initialAuthState} from "@/entities";
import {backendAuthikUrl} from "@/shared/api";

export const $authStore = createStore<AuthState>(initialAuthState);

persist({store: $authStore, key: "authStore"})

export const authFx = createEffect<AuthRequest, AuthState, Error>({
    handler: async (authReq) => {
        try {
            const response =
                await axios.put<AuthResponse>(`${backendAuthikUrl}/api/auth/telegram/web-app`,
                    JSON.stringify(authReq),
                    {
                        method: 'PUT',
                        headers: {
                            'Content-Type': 'application/json',
                        },
                    });
            return fillAuthState(response.data);
        } catch (error) {
            console.log(error)
            throw new Error('Auth failed');
        }
    },
});

export const logoutFx = createEffect<void, AuthState, Error>({
    handler: async () => {
        console.log("deauth success")
        return initialAuthState
    },
});

const fillAuthState = (response: AuthResponse): AuthState => {
    return {
        token: response.access,
        loading: false,
        tgId: "-1",
        isAuthenticated: true,
        error: null
    };
}

export const $isAuthenticated = $authStore.map(state => state.isAuthenticated)

$authStore.on(authFx.doneData, (_, result) => result);
$authStore.on(authFx.failData, (_, error) => ({ ...initialAuthState, loading: false, error: error.message }));

$authStore.on(logoutFx.doneData, (_, result) => result);


export function prepareInitDataRaw(initDataRaw: string | undefined): any {
    if (initDataRaw == undefined)
        throw new Error("initDataRaw is undefined")
    let hash: string | undefined;

    const pairs: string[] = [];

    new URLSearchParams(initDataRaw).forEach((value, key) => {
        if (key === 'hash') {
            hash = value;
            return;
        }

        pairs.push(`${key}=${value}`);
    });

    pairs.sort();

    const tgInitDataRaw = pairs.join('\n')
    return {tgInitDataRaw, hash}
}
