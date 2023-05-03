import UserProfile from "./UserProfile.jsx";

const name = "McKeller";

const users = [

    {
        name: "Slava", age: 36, gender: "male"
    },

    {
        name: "Maria", age: 27, gender: "female"
    },

    {
        name: "Joe", age: 54, gender: "male"
    }


]

const UserProfiles = ({users}) => (
    <div>
        {
            users.map((user, index) =>
                (
                    (<UserProfile
                        key={index}
                        name={user.name}
                        age={user.age}
                        gender={user.gender}
                        imageNumber={index}
                    />)

                )
            )
        }
    </div>
)

function App() {

    return (
        <UserProfiles users={users}/>
    )
}

export default App
