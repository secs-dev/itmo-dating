import { Link } from 'atomic-router-react'
import { Text } from '@telegram-apps/telegram-ui'
import { AuthTest } from '@/widgets'
export const Home = () => {
    return (
        <>
            <Link to={'/itmo-dating-mini-app/registration'}>
                <Text weight="1">TEXT</Text>
            </Link>
            <br />
            <AuthTest />
            <br />
            <Link to={'/itmo-dating-mini-app/main'}>
                <Text weight="1">Main</Text>
            </Link>
        </>
    )
}
