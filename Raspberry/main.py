import os
import requests
import RPi.GPIO as GPIO

import const


class LEDState:
    OFF = 0
    RED = 1
    GREEN = 2


class BoxState:
    CLOSED = 0
    OPENED = 1


class ServerCommunicator:
    def __init__(self, server_address: str, rasp_name: str, rasp_pass: str):
        self.server_address = server_address
        self.session = requests.Session()
        self.session.auth = (rasp_name, rasp_pass)

        url = self.server_address  # TODO may by another address
        response = self.session.get(url, verify=False)
        assert response.status_code == 200, (
            f'Server addr, rasp_name or rasp_pass is incorrect. The GET request to "{url}" with '
            f'user "{rasp_name}" and pass "{rasp_pass}" returned {response.status_code} status code'
        )

    def _call_server(self, url):
        response = self.session.get(url)
        assert response.status_code == 200, \
            f'INFO: The response of the query "{url}" is incorrect, status_code is {response.status_code}'
        return response

    def check_deliverer(self, deliverer_name: str) -> bool:
        url = os.path.join(self.server_address, 'other/magic/path/', deliverer_name)  # TODO
        response = self._call_server(url)
        return response.json()['result']

    def check_customer(self, customer_name: str) -> bool:
        url = os.path.join(self.server_address, 'third/magic/path/', customer_name)  # TODO
        response = self._call_server(url)
        return response.json()['result']


def init_hardware():
    GPIO.setmode(GPIO.BCM)
    GPIO.setwarnings(False)
    GPIO.setup(const.HW_LEG_RED, GPIO.OUT, initial=GPIO.LOW)
    GPIO.setup(const.HW_LEG_GREEN, GPIO.OUT, initial=GPIO.LOW)


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


def check_box_state() -> BoxState:
    result = GPIO.input(const.HW_LEG_SENSOR)  # TODO what dose 'input()' return? Float?
    if result > 0.5:
        return BoxState.OPENED
    else:
        return BoxState.CLOSED


def update_box_status() -> None:
    """send to the backEnd 'The goods were taken' and so on"""
    pass


def main_loop():
    pass


if __name__ == '__main__':
    main_loop()
