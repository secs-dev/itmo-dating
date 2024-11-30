import {mainButton, offMainButtonClick, onMainButtonClick} from "@telegram-apps/sdk";

export function setMainButtonOnClick(fn: VoidFunction) {
    if (mainButton.isMounted()) {
        return onMainButtonClick(fn)
    } else return () => {}
}

export function removeMainButtonOnClick(fn: VoidFunction) {
    if (mainButton.isMounted()) {
        offMainButtonClick(fn)
    }
}

export function setMainButtonOnAndOffClick(fn: VoidFunction) {
    const fun = () => {fn(); removeMainButtonOnClick(fun)}
    setMainButtonOnClick(fun)
}