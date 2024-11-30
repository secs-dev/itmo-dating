import {useEffect} from "react";
import {router} from "./routes/api";
import {RouterProvider} from "atomic-router-react";

import {$errorStore} from "@/shared/api";
import {message} from "antd";
import {Routes} from "@/app/routes/ui";

import {init, mockTgEnv} from './telegram-sdk';
import {retrieveLaunchParams} from '@telegram-apps/sdk';


export const App = () => {
    mockTgEnv()
        .then(() => {
            init(retrieveLaunchParams().startParam === 'debug' || import.meta.env.DEV);
        })

    const [messageApi, contextHolder] = message.useMessage();
    useEffect(() => {
        $errorStore.watch(state => {
            if (state)
                messageApi.error(state?.message)
        })
    }, [])

    return (
        <RouterProvider router={router}>
            {contextHolder}
            <Routes/>
        </RouterProvider>
    );
};
