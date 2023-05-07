import axios from "axios";

export const getCustomers = async () => {
    // eslint-disable-next-line no-useless-catch
    try {
       // / return await axios.get(`${import.meta.env.VITE_API_BASE_URL}/api/v1/customers`)
        return await axios.get(`http://localhost:8080/api/v1/customers`)
    } catch (e) {
        throw e;
    }
}

export const saveCustomer = async (customer) => {
    // eslint-disable-next-line no-useless-catch
    try {
        // / return await axios.get(`${import.meta.env.VITE_API_BASE_URL}/api/v1/customers`)
        return await axios.post
        (`http://localhost:8080/api/v1/customers`),
            customer
    } catch (e) {
        throw e;
    }
}