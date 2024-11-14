import {retrieveLaunchParams} from "@telegram-apps/sdk";
import {authFx, prepareInitDataRaw} from "@/features/authentication/api";

export function authenticate() {
    const { initDataRaw} = retrieveLaunchParams();
    const {data_check_string, hash} = prepareInitDataRaw(initDataRaw)
    console.log(initDataRaw)
    console.log(data_check_string)
    console.log(hash)
    authFx({string: data_check_string, hash: hash})
        .then(_ => {
            console.log("auth success")
    }).catch(
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        (reason) => {
            console.log("auth error")
            console.log(reason)
        }
    )
}
