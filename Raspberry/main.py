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

def closed_correctly(time_opened, controller) -> bool:
    while datetime.now() - time_opened <= timedelta(seconds=10):  # fewer than 10s have passed since opening the box
        if controller.check_box_state() == BoxState.CLOSED:
            return True

        time.sleep(0.25)

    return False


def main_loop():
    controller = HardwareController()
    communicator = ServerCommunicator(
        server_address=const.SERVER_ADDRESS,
        box_number=const.BOX_NUMBER)

    print('Main loop is running')

    delivered = False

    while True:
        if delivered:
            RFID_result, order_id = check_RFID_name(
                controller=controller, communicator=communicator, p_type=ActorType.CLIENT)

            if RFID_result:
                print("customer has a delivery")

                delivered = False
                status_str = const.STATUS_DELIVERED
        else:
            RFID_result, order_id = check_RFID_name(
                controller=controller, communicator=communicator, p_type=ActorType.DELIVERER)

            if RFID_result:
                print("deliverer delivery package")

                delivered = True
                status_str = const.STATUS_ONITSWAY

        if not RFID_result:
            controller.switch_led(LEDState.RED)
            time.sleep(5)
            controller.switch_led(LEDState.OFF)
            continue

        time_opened = datetime.now()
        controller.switch_led(LEDState.GREEN)

        if closed_correctly(time_opened=time_opened, controller=controller):
            communicator.change_orders_status(order_ids=order_id, status_str=status_str)

        else: # box was not closed correctly
             controller.blink_led(LEDState.RED, 5)
             controller.switch_led(LEDState.OFF)

        controller.switch_led(LEDState.OFF)


if __name__ == '__main__':
    main_loop()
