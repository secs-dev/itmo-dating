import {Route} from "atomic-router-react";
import {routes} from "../api";
import {Registration, Home} from "@/pages";

export const Routes = () => (
    <>
        <Route route={routes.home} view={Home}/>
        <Route route={routes.registration} view={Registration}/>
    </>
);