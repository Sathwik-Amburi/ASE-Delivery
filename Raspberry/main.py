import os
import requests
from enum import Enum
import RPi.GPIO as GPIO
from mfrc522 import SimpleMFRC522

import const


class LEDState(Enum):
    OFF = 0
    RED = 1
    GREEN = 2


class BoxState(Enum):
    CLOSED = 0
    OPENED = 1


class PersonType(Enum):
    DELIVERER = 0
    CUSTOMER = 1


class ServerCommunicator:
    def __init__(self, server_address: str, rasp_name: str, rasp_pass: str):
        self.server_address = server_address
        self.session = requests.Session()
        self.session.auth = (rasp_name, rasp_pass)

        url = self.server_address  # TODO may be another address
        response = self.session.get(url, verify=False)
        assert response.status_code == 200, (
            f'Server addr, rasp_name or rasp_pass is incorrect. The GET request to "{url}" with '
            f'user "{rasp_name}" and pass "{rasp_pass}" returned {response.status_code} status code'
        )

    def check_person_name(self, name: str, p_type: PersonType):
        if p_type == PersonType.DELIVERER:
            url = os.path.join(self.server_address, 'other/magic/path/', name)  # TODO
        else:
            url = os.path.join(self.server_address, 'third/magic/path/', name)  # TODO

        response = self.session.get(url)
        assert response.status_code == 200, \
            f'INFO: The response of the query "{url}" is incorrect, status_code is {response.status_code}'
        return response

    def update_box_status(self, message: str) -> None:
        """send to the backEnd 'The goods were taken' and so on"""
        pass


class HardwareController:
    def __init__(self):
        GPIO.setmode(GPIO.BCM)
        GPIO.setwarnings(False)
        GPIO.setup(const.HW_LEG_RED, GPIO.OUT, initial=GPIO.LOW)
        GPIO.setup(const.HW_LEG_GREEN, GPIO.OUT, initial=GPIO.LOW)
        self.reader = SimpleMFRC522()

    def read_RFID_name(self):
        _, text = self.reader.read()
        return text

    @staticmethod
    def switch_led(state: LEDState) -> None:
        if state == LEDState.GREEN:
            GPIO.output(const.HW_LEG_GREEN, GPIO.HIGH)
        elif state == LEDState.RED:
            GPIO.output(const.HW_LEG_RED, GPIO.HIGH)
        elif state == LEDState.OFF:
            GPIO.output(const.HW_LEG_GREEN, GPIO.LOW)
            GPIO.output(const.HW_LEG_RED, GPIO.LOW)
        else:
            raise RuntimeError(f"LEADState {state} is not implemented")

    @staticmethod
    def check_box_state() -> BoxState:
        result = GPIO.input(const.HW_LEG_SENSOR)  # TODO what dose 'input()' return? Float?
        if result > 0.5:
            return BoxState.OPENED
        else:
            return BoxState.CLOSED


def check_RFID_name(self, controller: HardwareController,
                    communicator: ServerCommunicator,
                    p_type: PersonType) -> bool:
    name = controller.read_RFID_name()
    result = communicator.check_person_name(name=name, p_type=p_type)
    return result


def main_loop():
    pass


if __name__ == '__main__':
    main_loop()
