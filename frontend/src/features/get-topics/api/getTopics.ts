import axios from "axios";
import {Topic} from "@/entities/registration-data/model/topic.ts";
import React from "react";

export function getTopics(setTopics:  React.Dispatch<React.SetStateAction<Array<Topic>>>) {
    const url = 'http://localhost:3000/getTopics';

    axios.get(url)
        .then((response) => {
            console.log(response.data);
            //FIXME after integration with backend
            // setTopics(response.data)
        })
        .catch((error) => {
            console.error("Error getting topics: ", error);
            //TODO remove after integration with backend
            setTopics([
                {id: 1, name: "programming", color: undefined, icon: undefined},
                {id: 2, name: "eating", color: undefined, icon: undefined},
                {id: 3, name: "sleeping", color: undefined, icon: undefined}
            ])
        })
}