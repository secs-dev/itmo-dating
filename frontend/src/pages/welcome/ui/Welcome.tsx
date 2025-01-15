import { useEffectOnce } from '@/shared/api'
import { authenticate } from '@/widgets/auth-test/api/authenticate.ts'
import { redirect } from 'atomic-router'
import { $authStore, $isAuthenticated, userIdFx, userRegisteredFx } from '@/features/authentication/api/authFx.ts'
import { routes } from '@/app/routes/api'
import { useEffect } from 'react'
import { getUser } from '@/features/get_user/api/getUser.ts'

export const Welcome = () => {
    useEffectOnce(() => {
        authenticate()
    })
    redirect({clock: userRegisteredFx.doneData, route: routes.main})
    redirect({clock: userRegisteredFx.failData, route: routes.registration})
    useEffect(() => {
        const userId: string = JSON.parse(atob(($authStore.getState().token || '').split(".")[1])).user_id;
        userIdFx(Number(userId))
        getUser(Number(userId)).then((x) => {
                if (x.data.status == 'ready')
                    userRegisteredFx(true)
                else
                    userRegisteredFx(false)
            }
        ).catch(() => {
            userRegisteredFx(false)
        })
    }, [$isAuthenticated])

    return (
        <div>
           Wait please... :)
        </div>
    )
}