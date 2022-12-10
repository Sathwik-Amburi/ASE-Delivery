from enum import Enum
import RPi.GPIO as GPIO
from mfrc522 import SimpleMFRC522
import const
import time


class LEDState(Enum):
    OFF = 0
    RED = 1
    GREEN = 2


class LightState:
    DARK = 0
    LIGHT = 1


class BoxState(Enum):
    CLOSED = 0
    OPENED = 1


class HardwareController:
    def __init__(self):
        GPIO.setmode(GPIO.BCM)
        GPIO.setwarnings(False)
        GPIO.setup(const.HW_LEG_RED, GPIO.OUT, initial=GPIO.LOW)
        GPIO.setup(const.HW_LEG_GREEN, GPIO.OUT, initial=GPIO.LOW)
        GPIO.setup(const.HW_LEG_SENSOR, GPIO.IN)
        self.reader = SimpleMFRC522()

    def read_RFID_name(self):
        _, text = self.reader.read()
        return text

    def write_RFID_name(self, name):
        self.reader.write(name)

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

    def blink_led(self, state: LEDState, duration: int) -> None:
        for _ in range(duration):
            self.switch_led(state)
            time.sleep(0.5)
            self.switch_led(LEDState.OFF)
            time.sleep(0.5)

    @staticmethod
    def check_box_state() -> BoxState:
        result = GPIO.input(const.HW_LEG_SENSOR)  # TODO what dose 'input()' return? Float?
        if result > 0.5:
            return BoxState.OPENED
        else:
            return BoxState.CLOSED
