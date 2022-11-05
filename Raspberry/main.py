import const
from datetime import datetime
import time

from utils import HardwareController, ServerCommunicator, PersonType, BoxState, LEDState


def check_RFID_name(controller: HardwareController,
                    communicator: ServerCommunicator,
                    p_type: PersonType) -> bool:
    name = controller.read_RFID_name()
    print(f'name=|{name}|')  # TODO remove
    result = communicator.check_person_name(name=name, p_type=p_type)
    print(f'check_person_name=|{result}|')  # TODO remove
    return result


def handle_user(time_opened, controller, communicator) -> bool:
    while datetime.now() - time_opened < 10:  # 10s have passed since opening the box
        time.sleep(0.25)

        if controller.check_box_state() == BoxState.CLOSED:  # box is closed
            communicator.update_box_status()
            return True

    return False


def main_loop():
    controller = HardwareController()
    communicator = ServerCommunicator(
        server_address=const.SERVER_ADDRESS,
        rasp_name=const.RASP_NAME,
        rasp_pass=const.RASP_PASS)
    print('Main loop is running')
    while True:
        if check_RFID_name(controller=controller, communicator=communicator, p_type=PersonType.ANY):
            time_opened = datetime.now()
            controller.switch_led(LEDState.GREEN)

            if not handle_user(time_opened, controller, communicator):
                controller.blink_for(LEDState.RED, 5)

            controller.switch_led(LEDState.OFF)


if __name__ == '__main__':
    main_loop()
