import {
    Button, CloseButton,
    Drawer,
    DrawerBody,
    DrawerCloseButton,
    DrawerContent, DrawerFooter,
    DrawerHeader,
    DrawerOverlay, useDisclosure
} from "@chakra-ui/react";
import CreateCustomerForm from "./CreateCustomerForm.jsx";

const AddIcon = () => "+";

const DrawerForm = () => {
    const {isOpen, onOpen, onClose} = useDisclosure()
    return (
        <div>
            <Button
                leftIcon={<AddIcon/>}
                onClick={onOpen}
                colorScheme={"teal"}

            >
                Create customer
                <Drawer isOpen={isOpen} onClose={onClose} size={"xl"}>
                    <DrawerOverlay/>
                    <DrawerContent>
                        <DrawerCloseButton/>
                        <DrawerHeader>Create new customer   </DrawerHeader>

                        <DrawerBody>
                            <CreateCustomerForm/>
                        </DrawerBody>

                        <DrawerFooter>
                            <Button  leftIcon={<CloseButton/>}
                                     onClick={onClose}
                                     colorScheme={"teal"}
                            >
                                Close
                            </Button>
                        </DrawerFooter>
                    </DrawerContent>
                </Drawer>

            </Button>
        </div>
    )
}
export default DrawerForm;

