import {createEffect, createStore} from 'effector';
import {IError} from "../model";

export const $errorStore = createStore<IError | null>(null);

export const throwErrorFx = createEffect<IError, IError, Error>( {
    handler: (e) => e
});

$errorStore.on(throwErrorFx.doneData, (_, result) => result)
