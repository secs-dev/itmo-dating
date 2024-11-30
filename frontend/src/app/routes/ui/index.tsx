import {Route} from "atomic-router-react";
import {routes} from "../api";
import {Registration, Home, Main} from "@/pages";

export const Routes = () => (
    <>
        <Route route={routes.home} view={Home}/>
        <Route route={routes.registration} view={Registration}/>
        <Route route={routes.main} view={Main}/>
    </>
);