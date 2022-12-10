import const
from datetime import timedelta, datetime
import time

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


def handle_opened(order_id, time_opened, controller, communicator) -> bool:
    while datetime.now() - time_opened <= timedelta(seconds=10):  # fewer than 10s have passed since opening the box
        if controller.check_box_state() == BoxState.CLOSED:
            communicator.set_order_delivered(order_id=order_id)
            return True

        time.sleep(0.25)

    return False


def main_loop():
    controller = HardwareController()
    communicator = ServerCommunicator(
        server_address=const.SERVER_ADDRESS,
        rasp_name=const.RASP_NAME,
        rasp_pass=const.RASP_PASS)
    print('Main loop is running')
    while True:
        RFID_result, order_id = check_RFID_name(
            controller=controller, communicator=communicator, p_type=ActorType.ANY)
        if RFID_result:
            time_opened = datetime.now()
            controller.switch_led(LEDState.GREEN)

            if not handle_opened(order_id=order_id, time_opened=time_opened,
                                 controller=controller, communicator=communicator):
                controller.blink_led(LEDState.RED, 5)

        else:
            controller.switch_led(LEDState.RED)
            time.sleep(5)

        controller.switch_led(LEDState.OFF)


if __name__ == '__main__':
    main_loop()
