import {retrieveLaunchParams} from "@telegram-apps/sdk";
import {authFx, prepareInitDataRaw} from "@/features/authentication/api";

export function authenticate() {
    const { initDataRaw} = retrieveLaunchParams();
    const {tgInitDataRaw, hash} = prepareInitDataRaw(initDataRaw)

    authFx({string: tgInitDataRaw, hash: hash})
        .then(_ => {
            console.log("auth success")
    }).catch(
        (reason) => {
            console.log("auth error")
            console.log(reason)
        }
    )
}
