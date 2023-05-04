import SidebarWithHeader from "./shared/SideBar.jsx";
import {getCustomers} from "../services/client.js";
import {useEffect} from "react";

const App = () => {

    useEffect(() => {
        getCustomers().then((res) => {
            console.log(res)
        }).catch(err => {
                console.log(err);
            }
        )
    }, [])


    return (
        <SidebarWithHeader>
            {/*<Button colorScheme='blue'>Click me*/}

            {/*</Button>*/}

        </SidebarWithHeader>
    )
}

export default App;