import axios from "axios";

export const getCustomers = async () => {
    try {
        return await axios.get("http://45.137.66.185:8088/api/v1/customers")
    } catch (e) {
        throw e;
    }
}