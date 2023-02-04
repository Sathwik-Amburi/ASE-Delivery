import const
from datetime import timedelta, datetime
import time
import json

from serverCommunicator import ServerCommunicator, ActorType
from utils import HardwareController, BoxState, LEDState


def check_RFID_name(controller: HardwareController,
                    communicator: ServerCommunicator,
                    p_type: ActorType) -> (bool, str):
    name = controller.read_RFID_name().strip()
    print(f'INFO: read name=|{name}|')
    result, order_id = communicator.check_person_name(actor_id=name, a_type=p_type)
    print(f'INFO: is the name authorised: {result}')
    return result, order_id


def handle_opened(order_id, time_opened, status_str, controller, communicator) -> bool:
    while datetime.now() - time_opened <= timedelta(seconds=10):  # fewer than 10s have passed since opening the box
        if controller.check_box_state() == BoxState.CLOSED:
            communicator.change_order_status(order_id=order_id, status_str=status_str)
            return True

        time.sleep(0.25)

    return False


def main_loop():
    controller = HardwareController()
    communicator = ServerCommunicator(
        server_address=const.SERVER_ADDRESS,
        box_number=const.BOX_NUMBER)

    print('Main loop is running')
    while True:
        RFID_result, order_id = check_RFID_name(
            controller=controller, communicator=communicator, p_type=ActorType.DELIVERER)

        if RFID_result:
            time_opened = datetime.now()
            controller.switch_led(LEDState.GREEN)

            if not handle_opened(order_id=order_id, time_opened=time_opened, status_str=const.STATUS_ONITSWAY,
                                 controller=controller, communicator=communicator):
                controller.blink_led(LEDState.RED, 5)

        else:
            controller.switch_led(LEDState.RED)
            time.sleep(5)

        controller.switch_led(LEDState.OFF)

        RFID_result, order_id = check_RFID_name(
            controller=controller, communicator=communicator, p_type=ActorType.CLIENT)

        if RFID_result:
            time_opened = datetime.now()
            controller.switch_led(LEDState.GREEN)

            if not handle_opened(order_id=order_id, time_opened=time_opened, status_str=const.STATUS_DELIVERED,
                                 controller=controller, communicator=communicator):
                controller.blink_led(LEDState.RED, 5)

        else:
            controller.switch_led(LEDState.RED)
            time.sleep(5)

        controller.switch_led(LEDState.OFF)


if __name__ == '__main__':
    main_loop()
