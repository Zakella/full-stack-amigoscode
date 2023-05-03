import axios from "axios";

// axios.get("https://api.chucknorris.io/jokes/random")
// .then(result=> {
//     console.log(result.data.value)
//
// })
//     .catch( err => {
//         console.log(err)
//     }
// )

const fetchJoke = async (url) => {

    try {
        const result = await axios.get(url);
        console.log(result);

    } catch (error) {
        console.log(error)
    }


}

fetchJoke("https://api.chucknorris.io/jokes/random");