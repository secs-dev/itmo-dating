import {createEffect, createStore} from 'effector';
import { persist } from 'effector-storage/session'
import {AuthRequest, AuthResponse} from '../model'
import axios from "axios";
import {AuthState, initialAuthState} from "@/entities";
import {backendAuthikUrl} from "@/shared/api";
// import {initDataToSearchParams, validate} from "@telegram-apps/init-data-node";

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


export function prepareInitDataRaw(value: string | undefined): any {
    let hash: string | undefined;

    // All search params pairs presented as `k=v`.
    const pairs: string[] = [];

    // Iterate over all key-value pairs of parsed parameters and find required
    // parameters.
    new URLSearchParams(value).forEach((value, key) => {
        if (key === 'hash') {
            hash = value;
            return;
        }

        pairs.push(`${key}=${value}`);
    });


    // According to docs, we sort all the pairs in alphabetical order.
    pairs.sort();

    // const sign = signData(pairs.join('\n'), token, options);
    //
    // if (initDataRaw == undefined)
    //     throw new Error("init data is undefined")
    // // Parse query data
    // const parsedData =
    //
    // // Get Telegram hash
    // const hash = parsedData.get("hash")
    //
    // // Remove 'hash' value & Sort alphabetically
    // const data_keys = Array.from(parsedData.keys()).filter(v => v !== 'hash').sort()
    //
    // // Create line format key=<value>
    // const items = data_keys.map(key => `${key}=${parsedData.get(key)}`)
    //
    // // Create check string with a line feed
    // // character ('\n', 0x0A) used as separator
    // // result: 'auth_date=<auth_date>\nquery_id=<query_id>\nuser=<user>'
    // const data_check_string = items.join('\n')
    const data_check_string = pairs.join('\n')
    return {data_check_string, hash}
}

// function splitInitDataRaw(initDataRaw: string): Map<string, any> {
//     var vars = initDataRaw.split('&');
//     return new Map(vars.map(obj => obj.split('=')).map(pair => [pair[0], pair[1]]));
//
// }
//
// const bot_token = '0123456789:XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX';
//
// // https://core.telegram.org/bots/webapps#validating-data-received-via-the-web-app
// function isValidHash(initDataRaw: string) {
//     // Parse query data
//     const parsedData = window.Telegram.Utils.urlParseQueryString()
//
//     // Get Telegram hash
//     const hash = parsedData.hash
//
//     // Remove 'hash' value & Sort alphabetically
//     const data_keys = Object.keys(parsedData).filter(v => v !== 'hash').sort()
//
//     // Create line format key=<value>
//     const items = data_keys.map(key => key + '=' + parsedData[key])
//
//     // Create check string with a line feed
//     // character ('\n', 0x0A) used as separator
//     // result: 'auth_date=<auth_date>\nquery_id=<query_id>\nuser=<user>'
//     const data_check_string = items.join('\n')
//
//     function HMAC_SHA256(value, key) {
//         const crypto = require('crypto');
//         return crypto.createHmac('sha256', key).update(value).digest()
//     }
//
//     function hex(bytes) {
//         return bytes.toString('hex');
//     }
//
//     // Generate secret key
//     const secret_key = HMAC_SHA256(bot_token, 'WebAppData')
//
//     // Generate hash to validate
//     const hashGenerate = hex(HMAC_SHA256(data_check_string, secret_key))
//
//     // Return bool value is valid
//     return Boolean(hashGenerate === hash)
// }
