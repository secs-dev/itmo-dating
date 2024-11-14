import {Link} from "atomic-router-react";
import {Text} from "@telegram-apps/telegram-ui"
import {AuthTest} from "@/pages/auth-test/ui";
export const Home = () => {
   return (
       <>
           <Link to={"/itmo-dating-mini-app/registration"}><Text weight="1">TEXT</Text></Link>
           <br/>
           <AuthTest/>
       </>
    )
}
